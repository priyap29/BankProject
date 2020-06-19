package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDAO;
import com.revature.model.AbstractAccount;
import com.revature.model.Customer;
import com.revature.model.Roles;
import com.revature.service.PremiumService;
import com.revature.util.Constants;

//public class RegisterServlet extends HttpServlet {
public class AddJointHolderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(AddJointHolderServlet.class);
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
		int accountNo = Integer.parseInt(req.getParameter("account"));
		
		Customer cust = new Customer();
		cust.setUsername(username);
		cust.setFirstName(fname);
		cust.setLastName(lname);
		cust.setPassword(password);
		cust.setEmail(email);
		cust.setRoleId(Roles.getKey(Constants.PREMIUM));
		
		Customer custSession = (Customer) req.getSession().getAttribute("customer");
		log.debug("Customer object retrieved from session:" + custSession.toString());
		
		UsersDAO user = new UsersDAO();
		PremiumService premiumUser = new PremiumService();
		
		/*
		 * openAccount=0: if username donest exists..so this is a new user..ok to open
		 * account openAccount=1: if username exists...not allowed to create multiple
		 * accounts openAccount=2: Some exception occurred
		 */
		int addJointHolder = user.checkUsernameExists(username);

		if (addJointHolder == 0) // ..ok to add joint holder, username is valid
		{
			log.debug("ok to add joint holder to account : " +accountNo + ", details of joint holder are:");
			log.debug(cust.toString());			
			
			int status = premiumUser.addJointHolder(cust,accountNo);

			if (status == 0) {
				setStatusString(Constants.JOINT_HOLDER_SUCCESS);
				log.info(Constants.JOINT_HOLDER_SUCCESS + " : "+accountNo);
				
			} else {
				setStatusString(Constants.JOINT_HOLDER_FAILED);
				log.info(Constants.JOINT_HOLDER_FAILED);
			}
			// System.out.println(statusString);*/
		}
		else {
			setStatusString(Constants.USER_ALREADY_EXISTS);
		}
		
		req.getSession().setAttribute("customer", custSession);
		req.setAttribute("errorMessage", this.getStatusString());
		req.getRequestDispatcher("/jsp/addJointHolder.jsp").forward(req, res);
		
	}
	
	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}

