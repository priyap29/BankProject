package com.revature.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.dao.AbstractDAO;
import com.revature.dao.AccountsDAO;
import com.revature.dao.TransactionLogDAO;
import com.revature.dao.User_AccountDAO;
import com.revature.dao.UsersDAO;
import com.revature.model.AbstractAccount;
import com.revature.model.Customer;
import com.revature.model.TransactionLog;
import com.revature.util.Constants;

public class PremiumService extends AbstractDAO {

	static final Logger log = Logger.getLogger(PremiumService.class);

	public int openPremiumAccount(Customer cust, AbstractAccount acct) {

		int status = 1;
		int rowcnt = 0;
		AccountsDAO accountDAO = new AccountsDAO();
		User_AccountDAO uaDAO = new User_AccountDAO();

		try {
			getConnection();

			con.setAutoCommit(false);

			acct.setAccountId(AccountsDAO.generateAccountNo());
			rowcnt = accountDAO.insertAccountsTable(con, acct);

			if (rowcnt == 1) { // 1 row inserted in ACCOUNTS table, success
				log.debug("Inserted into accounts for premium user");
				// now insert into USER_Accounts association table
				rowcnt = uaDAO.insertUserAccount(con, cust.getUserId(), acct.getAccountId());
			}
			log.debug("Inserted into User_account for premium user");
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

	public int addJointHolder(Customer cust, int accountNo) {
		int status = 1;
		int rowcnt = 0;
		User_AccountDAO uaDAO = new User_AccountDAO();
		UsersDAO userDAO = new UsersDAO();

		try {
			getConnection();

			con.setAutoCommit(false);

			rowcnt = userDAO.insertUsers(con, cust);
			log.debug("Inserted joint account holder into user table.");
			if (rowcnt == 1) { // 1 row inserted in USERS table, success
				// now insert into USER_Accounts association table
				 int userID = userDAO.getUserId(con, cust.getUsername());
				rowcnt = uaDAO.insertUserAccount(con, userID, accountNo);

				if (rowcnt == 1) {
					log.debug("Inserted into User_account");
					con.commit();
					status = 0;
				} else {
					status = 1;
					con.rollback();
					log.info("Inserting into user_account table failed");
				}

			} else {
				status = 1;
				con.rollback();
				log.info("Inserting into users table for joint holder failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();;
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
