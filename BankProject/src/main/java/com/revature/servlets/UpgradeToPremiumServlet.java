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
public class UpgradeToPremiumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(UpgradeToPremiumServlet.class);
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
		
		Customer cust = (Customer) req.getSession().getAttribute("customer");
		List<AbstractAccount> acctList = cust.getAccountList();
		log.debug("Customer object retrieved from session:" + cust.toString());

		StandardService customerService = new StandardService();
	
		status = customerService.upgradePremium(cust);

		
		
		if (status == 0) { // success
			setStatusString(Constants.PREMIUM_UPGRADE_SUCCESS);
			log.info(Constants.PREMIUM_UPGRADE_SUCCESS + " : " + acctList.get(0).getAccountId());
			req.setAttribute("errorMessage", this.getStatusString());
			// update the customer object in session with new balance
			cust.setRoleId(Roles.getKey(Constants.PREMIUM));
			customerService.getcustomerAccountDetails(cust);
			req.getSession().setAttribute("customer", cust);
			log.debug("Customer Details updated for new balance:" + cust.toString());
			
			req.getRequestDispatcher("/jsp/premiumMain.jsp").forward(req, res);
		} else if (status == 1) // -ve balance, no sufficient funds
		{
			setStatusString(Constants.NO_FUNDS_FOR_PREMIUM_UPGRADE);
			log.info(Constants.NO_FUNDS_FOR_PREMIUM_UPGRADE + " : " + acctList.get(0).getAccountId());
			req.getSession().setAttribute("customer", cust);
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/upgradePremium.jsp").forward(req, res);
		} else {
			setStatusString(Constants.PREMIUM_UPGRADE_FAILURE);
			log.info(Constants.PREMIUM_UPGRADE_FAILURE + " : " + acctList.get(0).getAccountId());
			req.getSession().setAttribute("customer", cust);
			req.setAttribute("errorMessage", this.getStatusString());
			req.getRequestDispatcher("/jsp/upgradePremium.jsp").forward(req, res);
		}


	}

	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}
}
