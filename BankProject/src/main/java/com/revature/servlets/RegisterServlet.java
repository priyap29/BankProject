package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDAO;
import com.revature.model.AbstractAccount;
import com.revature.model.AccountStatus;
import com.revature.model.CheckingAccount;
import com.revature.model.Customer;
import com.revature.model.Roles;
import com.revature.model.SavingsAccount;
import com.revature.service.StandardService;
import com.revature.util.Constants;

//public class RegisterServlet extends HttpServlet {
public class RegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(RegisterServlet.class);
	String statusString;

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	// call super class constructor
	public void init() throws ServletException{		
		System.out.println(this.getServletName() + " is instantiated!");
		super.init();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
		String fname = req.getParameter("first_name");
		String lname = req.getParameter("last_name");
		String username = req.getParameter("uname");
		String password = req.getParameter("psw");
		String email = req.getParameter("email");
		String type = req.getParameter("type");
		log.debug("Accounttype: " + type);
		double bal = Double.parseDouble(req.getParameter("bal"));

		UsersDAO user = new UsersDAO();
		StandardService stdUser = new StandardService();
		AbstractAccount acct = null;
		
		Customer cust = new Customer();
		cust.setUsername(username);
		cust.setFirstName(fname);
		cust.setLastName(lname);
		cust.setPassword(password);
		cust.setEmail(email);
		
		
		if(type.equalsIgnoreCase("Checking")) {
			acct = new CheckingAccount(bal);
		}
		else if(type.equalsIgnoreCase("Savings")) {
			acct = new SavingsAccount(bal);
		}
		
		/*
		 * openAccount=0: if username donest exists..so this is a new user..ok to open
		 * account openAccount=1: if username exists...not allowed to create multiple
		 * accounts openAccount=2: Some exception occurred
		 */
		int openAccount = user.checkUsernameExists(username);

		if (openAccount == 0) // ..ok to open account
		{
			log.debug("Ok to open account");
			
			acct.setStatus(AccountStatus.getKey(Constants.OPEN));
			cust.setRoleId(Roles.getKey(Constants.STANDARD));
			
			log.debug(acct.toString());
			log.debug(cust.toString());			
			
			int status = stdUser.openStandardAccount(cust,acct);

			if (status == 0) {
				setStatusString(Constants.ACCOUNT_OPEN_SUCCESS);
				log.info(Constants.ACCOUNT_OPEN_SUCCESS + " : "+acct.getAccountId());
				
				req.setAttribute("errorMessage", this.getStatusString());
				req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
			} else {
				setStatusString(Constants.ACCOUNT_OPEN_FAILED);
				log.info(Constants.ACCOUNT_OPEN_FAILED);
				
				req.setAttribute("errorMessage", this.getStatusString());
				req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
			}
			// System.out.println(statusString);*/
		}
		else {
			setStatusString(Constants.USER_ALREADY_EXISTS);
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
		}
		
	}
	
	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}

