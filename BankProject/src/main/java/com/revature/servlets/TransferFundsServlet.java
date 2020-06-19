package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.bcel.classfile.Constant;

import com.revature.dao.AccountsDAO;
import com.revature.model.AbstractAccount;
import com.revature.model.AccountStatus;
import com.revature.model.Customer;
import com.revature.model.Roles;
import com.revature.service.StandardService;
import com.revature.util.Constants;

public class TransferFundsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(TransferFundsServlet.class);
	String statusString;

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	// call super class constructor
	public void init() throws ServletException {
		System.out.println(this.getServletName() + " is instantiated!");
		super.init();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int status = 1;
		boolean validTransfer = false;
		int fromAccountNo = Integer.parseInt(req.getParameter("fromAcct"));
		int toAccountNo = Integer.parseInt(req.getParameter("toAcct"));
		double amount = Double.parseDouble(req.getParameter("amount"));

		Customer cust = (Customer) req.getSession().getAttribute("customer");
		log.debug("Customer object retrieved from session:" + cust.toString());
		
		StandardService customerService = new StandardService();

		status = customerService.transferFunds(fromAccountNo,toAccountNo,amount);

		
		if(status == 0) {// success, update the customer object in session with new balance
			customerService.getcustomerAccountDetails(cust);
			this.setStatusString(Constants.TRANSFER_SUCCESS);
			log.info(Constants.TRANSFER_SUCCESS);
			log.debug("Customer Details updated for new balance:" + cust.toString());
		}else if(status == 1) //insufficinet funds
		{
			this.setStatusString(Constants.NO_SUFFICIENT_FUNDS);
			log.info(Constants.NO_SUFFICIENT_FUNDS);
		}else if(status == 3) { // account no to transfer funds to is invalid
			setStatusString(Constants.INVALID_ACCOUNT_NO);
			log.info(Constants.INVALID_ACCOUNT_NO + " : " + toAccountNo);
		}else {
			this.setStatusString(Constants.TRANSFER_FAILED);
			log.info(Constants.TRANSFER_FAILED);
		}
		
		req.getSession().setAttribute("customer", cust);
		req.setAttribute("errorMessage", this.getStatusString());
		req.getRequestDispatcher("/jsp/transferFunds.jsp").forward(req, res);

	}

	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}
