package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.model.AbstractAccount;
import com.revature.model.AccountStatus;
import com.revature.model.CheckingAccount;
import com.revature.model.Customer;
import com.revature.model.SavingsAccount;
import com.revature.service.PremiumService;
import com.revature.service.StandardService;
import com.revature.util.Constants;

//public class RegisterServlet extends HttpServlet {
public class OpenAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(OpenAccountServlet.class);
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

	// add accounts to premium user
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String type = req.getParameter("type");
		log.debug("Accounttype: " + type);
		double bal = Double.parseDouble(req.getParameter("bal"));

		Customer cust = (Customer) req.getSession().getAttribute("customer");
		log.debug("Customer object retrieved from session:" + cust.toString());

		PremiumService premiumUser = new PremiumService();
		StandardService customerService = new StandardService();
		AbstractAccount acct = null;

		if (type.equalsIgnoreCase("Checking")) {
			acct = new CheckingAccount(bal);
		} else if (type.equalsIgnoreCase("Savings")) {
			acct = new SavingsAccount(bal);
		}
		acct.setStatus(AccountStatus.getKey(Constants.OPEN));

		int status = premiumUser.openPremiumAccount(cust, acct);

		if (status == 0) {
			setStatusString(Constants.ACCOUNT_OPEN_SUCCESS);
			log.info(Constants.ACCOUNT_OPEN_SUCCESS + " : " + acct.getAccountId());

			// update with new account details
			customerService.getcustomerAccountDetails(cust);
		} else {
			setStatusString(Constants.ACCOUNT_OPEN_FAILED);
			log.info(Constants.ACCOUNT_OPEN_FAILED);
		}

		req.getSession().setAttribute("customer", cust);
		log.debug("New Customer object set to session is:" + cust.toString());
		req.setAttribute("errorMessage", this.getStatusString());
		req.getRequestDispatcher("/jsp/openAccount.jsp").forward(req, res);

	}

	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}
