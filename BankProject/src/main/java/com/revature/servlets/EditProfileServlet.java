package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDAO;
import com.revature.model.AbstractAccount;
import com.revature.model.Customer;
import com.revature.model.Roles;
import com.revature.util.Constants;

//public class RegisterServlet extends HttpServlet {
public class EditProfileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(EditProfileServlet.class);
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
		int userId =Integer.parseInt(req.getParameter("userId"));
		int roleId = Integer.parseInt(req.getParameter("roleId"));

		UsersDAO userDAO = new UsersDAO();
		
		//save the account list 
		List<AbstractAccount> acctList = ((Customer)req.getSession().getAttribute("customer")).getAccountList();
		
		Customer cust = new Customer();
		cust.setUsername(username);
		cust.setFirstName(fname);
		cust.setLastName(lname);
		cust.setPassword(password);
		cust.setEmail(email);
		cust.setRoleId(roleId);
		cust.setUserId(userId);
		cust.setRoleName(Roles.getRoleMap().get(roleId));
		
		log.debug("Updating customer profile to following:\n" + cust.toString());
		
		
		int profileUpdated = userDAO.updateUserProfile(cust);

		if (profileUpdated == 0) // ..success
		{
				setStatusString(Constants.PROFILE_UPDATE_SUCCESS);
				
		} else { //failed
				setStatusString(Constants.PROFILE_UPDATE_FAILED);
			}
		
		log.info(getStatusString() + " : "+cust.getUsername());
		
		//add the accountlist to cust object and store in session again
		cust.setAccountList(acctList);
		req.getSession().setAttribute("customer", cust);
		
		log.debug("Setting customer object in session to: " + cust.toString());
		req.setAttribute("errorMessage", this.getStatusString());
		req.getRequestDispatcher("/jsp/profile.jsp").forward(req, res);	
	}
	
	
	
	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}

