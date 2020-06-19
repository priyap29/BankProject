package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDAO;
import com.revature.util.Constants;

public class ForgotPasswordServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	String statusString;
	static final Logger log = Logger.getLogger(ForgotPasswordServlet.class);
	
	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	
	public void init() throws ServletException{		
		System.out.println(this.getServletName() + " is instantiated!");
		super.init();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
		String userid = req.getParameter("uname");
		String password = req.getParameter("psw");
		UsersDAO userDAO = new UsersDAO();

		int status = userDAO.updatePassword(userid, password);
		if (status == 0) {
			setStatusString(Constants.PASSWORD_UPDATE_SUCCESS);
			log.info(Constants.PASSWORD_UPDATE_SUCCESS + " : " + userid);
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/forgotPassword.jsp").forward(req, res);			
		} else {
			setStatusString(Constants.PASSWORD_UPDATE_FAILED);
			log.info(Constants.PASSWORD_UPDATE_FAILED + " : " + userid);
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/forgotPassword.jsp").forward(req, res);
		}
		
	}
	
	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
	
}
