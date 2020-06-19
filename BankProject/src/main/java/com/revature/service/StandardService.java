package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.AbstractDAO;
import com.revature.dao.AccountsDAO;
import com.revature.dao.RolesDAO;
import com.revature.dao.TransactionLogDAO;
import com.revature.dao.User_AccountDAO;
import com.revature.dao.UsersDAO;
import com.revature.model.AbstractAccount;
import com.revature.model.AbstractUser;
import com.revature.model.Customer;
import com.revature.model.Roles;
import com.revature.model.TransactionLog;
import com.revature.util.Constants;

public class StandardService extends AbstractDAO {

	static final Logger log = Logger.getLogger(StandardService.class);

	public void getcustomerProfileInfo(String username, AbstractUser cust) {
		UsersDAO user = new UsersDAO();

		try {
			getConnection();

			user.getUser(con, cust, username);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void getcustomerAccountDetails(Customer cust) {
		AccountsDAO acct = new AccountsDAO();
		try {
			getConnection();

			List<AbstractAccount> accList = acct.getAllCustomerAccounts(con, cust.getUserId());
			cust.setAccountList(accList);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int depositFunds(int accountId, double amount) {
		int status = 0; // 0 - update successful, 2 - exception or unsuccessful
		double OldBalance = 0;
		AccountsDAO acctDAO = new AccountsDAO();
		log.debug("Amount to deposit to accountId: " + accountId + " is: " + amount);
		try {
			getConnection();
			con.setAutoCommit(false);

			// get old balance from accounts
			OldBalance = acctDAO.getAccountBalance(con, accountId);

			// add the amount to old balance
			amount = OldBalance + amount;

			// update new balance
			acctDAO.updateAccountBalance(con, accountId, amount);
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	public int withdrawFunds(int accountId, double amount) {
		int status = 0; // 0 - update successful, 2-balance dropped below 100, 3- balance went -ve, 2 -
						// exception
						// unsuccessful
		double oldBalance = 0;
		AccountsDAO acctDAO = new AccountsDAO();
		log.debug("Amount to withdraw from accountId: " + accountId + " is: " + amount);
		try {
			getConnection();
			con.setAutoCommit(false);

			// get old balance from accounts
			oldBalance = acctDAO.getAccountBalance(con, accountId);

			// deduct the amount from old balance
			oldBalance = oldBalance - amount;

			if (oldBalance > 0) {
				// update new balance
				acctDAO.updateAccountBalance(con, accountId, oldBalance);
				status = 0;
				con.commit();
			} else {
				// balance is -ve
				status = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;

	}

	public int transferFunds(int fromAccountNo, int toAccountNo, double amount) {
		int status = 1; // 0 - update successful,1 - insufficient funds, 2 - exception , 3 - destination
						// account no invalid
		double oldBalance = 0;
		AccountsDAO acctDAO = new AccountsDAO();
		log.debug("Transfering " + amount + " from " + fromAccountNo + " to " + toAccountNo);
		try {
			getConnection();
			con.setAutoCommit(false);

			// withdraw from first account
			// get old balance from accounts
			oldBalance = acctDAO.getAccountBalance(con, fromAccountNo);
			// deduct the amount from old balance
			oldBalance = oldBalance - amount;
			if (oldBalance > 0) { // funds are sufficient to make transfer
				// update new balance in first account
				int returnStatus = acctDAO.updateAccountBalance(con, fromAccountNo, oldBalance);

				if (returnStatus == 0) // amount successfully withdrwan from first account
				{
					// deposit into second account
					// get old balance from second account
					oldBalance = acctDAO.getAccountBalance(con, toAccountNo);
					// add the amount to old balance
					oldBalance = oldBalance + amount;
					// update new balance
					returnStatus = acctDAO.updateAccountBalance(con, toAccountNo, oldBalance);
					if (returnStatus == 0) { // amount successfully deposited in second account
						con.commit();
						status = 0;
					} else if (returnStatus == 3) { // desitnation account no invalid
						con.rollback();
						status = 3;
					}

				}
			} else {
				status = 1; // for insufficient funds
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;

	}

	public int upgradePremium(Customer cust) {
		// TODO Auto-generated method stub
		int status = 0;
		List<AbstractAccount> acctList = cust.getAccountList();
		double oldBalance = 0;
		AccountsDAO acctDAO = new AccountsDAO();
		RolesDAO roleDAO = new RolesDAO();

		try {
			getConnection();
			con.setAutoCommit(false);
			// Step1: withdraw $100 from account as upgrade fee
			// get old balance from accounts
			oldBalance = acctDAO.getAccountBalance(con, acctList.get(0).getAccountId());

			// deduct the amount from old balance
			oldBalance = oldBalance - Constants.PREMIUM_FEE;

			if (oldBalance > 0) { //customer has sufficient balance to pay for fee
				// update new balance after debitting $100
				status = acctDAO.updateAccountBalance(con, acctList.get(0).getAccountId(), oldBalance);
				if(status == 0) {
				//Step2: change customer role to premium
				status = roleDAO.updateRoleId(con, cust.getUserId(), Roles.getKey(Constants.PREMIUM));
				if(status == 0) {
					con.commit();
				}
				else {
					status = 2; //failure
					con.rollback();
				}
				}
			} else {
				// balance is -ve
				status = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public int openStandardAccount(Customer cust, AbstractAccount acct) {

		int status = 1;
		int rowcnt = 0;
		AccountsDAO accountDAO = new AccountsDAO();
		User_AccountDAO uaDAO = new User_AccountDAO();
		UsersDAO userDAO = new UsersDAO();

		try {
			getConnection();

			con.setAutoCommit(false);

			rowcnt = userDAO.insertUsers(con, cust);
			log.debug("Inserted into Users for std user");
			if (rowcnt == 1) { // 1 row inserted in USERS table, success
				// now insert into Accounts table
				acct.setAccountId(AccountsDAO.generateAccountNo());
				rowcnt = accountDAO.insertAccountsTable(con, acct);
			}

			log.debug("Inserted into accounts for std user");
			if (rowcnt == 1) { // 1 row inserted in ACCOUNTS table, success
				// now insert into USER_Accounts association table
				int userID = userDAO.getUserId(con, cust.getUsername());
				rowcnt = uaDAO.insertUserAccount(con, userID, acct.getAccountId());
			}
			log.debug("Inserted into User_account");
			// insert into transaction table
			if (rowcnt == 1) {
				// enter user login time in transaction log
				TransactionLog tlog = new TransactionLog();
				tlog.setUsername(cust.getUsername());
				tlog.setAccountID(acct.getAccountId());
				tlog.setAmount(acct.getBalance());
				if (acct.getType() == 1)
					tlog.setTransactionType(Constants.TT_REGISTER_CHECKING);
				else
					tlog.setTransactionType(Constants.TT_REGISTER_SAVINGS);
				long millis = System.currentTimeMillis();
				java.sql.Timestamp date = new java.sql.Timestamp(millis);
				tlog.setTransactionDate(date);
				TransactionLogDAO tdao = new TransactionLogDAO();
				tdao.insertTable(con, tlog);
			}

			log.debug("Inserted into transaction_log");
			if (rowcnt == 1) {
				con.commit();
				status = 0;
			} else {
				status = 1;
				con.rollback();
				log.info("Open account trasaction rolled back.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			status = 2;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;
	}

}
