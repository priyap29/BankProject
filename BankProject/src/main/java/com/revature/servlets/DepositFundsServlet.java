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

//public class RegisterServlet extends HttpServlet {
public class DepositFundsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(DepositFundsServlet.class);
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
		int accountId = Integer.parseInt(req.getParameter("accountID"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		String type = req.getParameter("type");

		
		Customer cust = (Customer) req.getSession().getAttribute("customer");
		log.debug("Customer object retrieved from session:" + cust.toString());

		StandardService customerService = new StandardService();

		if (type.equals("deposit")) {
			status = customerService.depositFunds(accountId, amount);
			if (status == 0) {
				setStatusString(Constants.DEPOSIT_SUCCESS);
				log.info(Constants.DEPOSIT_SUCCESS + " : " + accountId);
			} else {
				setStatusString(Constants.DEPOSIT_FAILED);
				log.info(Constants.DEPOSIT_FAILED + " : " + accountId);
			}

		} else if (type.equals("withdraw")) {
			status = customerService.withdrawFunds(accountId, amount);
			if (status == 0) { // success
				setStatusString(Constants.WITHDRAW_SUCCESS);
				log.info(Constants.WITHDRAW_SUCCESS + " : " + accountId);
			} else if (status == 1) // -ve balance
			{
				setStatusString(Constants.NO_SUFFICIENT_FUNDS);
				log.info(Constants.NO_SUFFICIENT_FUNDS + " : " + accountId);
			} else {
				setStatusString(Constants.WITHDRAW_FAILURE);
				log.info(Constants.WITHDRAW_FAILURE + " : " + accountId);
			}
		}

		if (status == 0) {// update the customer object in session with new balance
			customerService.getcustomerAccountDetails(cust);
			log.debug("Customer Details updated for new balance:" + cust.toString());
		}

		req.getSession().setAttribute("customer", cust);
		req.setAttribute("errorMessage", this.getStatusString());
		req.getRequestDispatcher("/jsp/depositFunds.jsp").forward(req, res);
		
	}

	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}
