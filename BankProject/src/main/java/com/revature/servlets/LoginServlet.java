package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDAO;
import com.revature.model.AbstractUser;
import com.revature.model.Admin;
import com.revature.model.Customer;
import com.revature.service.AdminService;
import com.revature.service.StandardService;

//@WebServlet("")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	String statusString;
	static final Logger log = Logger.getLogger(LoginServlet.class);

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	// call super class constructor
	public LoginServlet() {
		super();
	}

	public void init() throws ServletException {
		System.out.println(this.getServletName() + " is instantiated!");
		super.init();
	}

	/*
	 * protected void service(HttpServletRequest req, HttpServletResponse res)throws
	 * ServletException,IOException { doPost(req,res); }
	 */

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug(this.getServletName() + " service method called!");

		String username = req.getParameter("uname");
		String password = req.getParameter("psw");

		log.debug("username :" + username + " password: " + password);
		
		UsersDAO userDAO = new UsersDAO();
		StandardService customerService = new StandardService();
		AdminService adminService = new AdminService();
		Customer cust = new Customer();
		List<AbstractUser>  users = new ArrayList<AbstractUser>();

		int validUser = userDAO.validateLogin(username, password);
		if (validUser == 0) {
			setStatusString("Valid user.");
			// get user role to decide which page to redirect the user to
			int role_id = userDAO.getUserRole(username);
			if (role_id == -1) {
				// redirect to error page
			} else if (role_id == 1 || role_id == 2) // customer having bank account
			{
				// gather user data and account data
				customerService.getcustomerProfileInfo(username, cust);
				customerService.getcustomerAccountDetails(cust);
				
				log.debug("Customer Details obtained:" + cust.toString());
				 req.getSession().setAttribute("customer", cust);
				// redirect to accounts page
				 if (role_id == 1) {
				req.getRequestDispatcher("/jsp/customerMain.jsp").forward(req, res);
				 }else
				 {
					 req.getRequestDispatcher("/jsp/premiumMain.jsp").forward(req, res);
				 }
				
			}else if(role_id==3) //admin
			{
				Admin admin = new Admin();
				customerService.getcustomerProfileInfo(username, admin);
				log.debug("Admin Details obtained:" + admin.toString());
				users = adminService.getAllUsers();	
				req.getSession().setAttribute("admin", admin);
				req.getSession().setAttribute("users", users);
				req.getRequestDispatcher("/jsp/adminMain.jsp").forward(req, res);
			}
		} else if (validUser == 1) {
			this.setStatusString("Invalid Username/password.");
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
		} else { // validUser = 2, some exception occured
			this.setStatusString("Some error occurred at server side.Please try again later.");
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
		}

		log.info(statusString);

	}

	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}

}
