package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.revature.model.AbstractAccount;
import com.revature.model.AccountStatus;
import com.revature.model.AccountType;
import com.revature.model.CheckingAccount;
import com.revature.model.SavingsAccount;
import com.revature.util.Constants;

public class AccountsDAO extends AbstractDAO {

	static final Logger log = Logger.getLogger(AccountsDAO.class);

	public void getALLAccounts() {
		ResultSet rs = null;
		Statement stmt = null;

		try {
			getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT ACCOUNT_ID,BALANCE,TYPE_ID,STATUS_ID FROM ACCOUNTS");
			while (rs.next()) {
				System.out.println("Inside while");
				System.out.println(rs.getInt("ACCOUNT_ID"));
				System.out.print(rs.getDouble("BALANCE"));
				System.out.print(rs.getInt("TYPE_ID"));
				System.out.print(rs.getInt("STATUS_ID"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int insertAccountsTable(Connection con, AbstractAccount acct) throws Exception {
		PreparedStatement pstmt = null;
		int rowcnt = -1;

		log.debug("Inserting into ACCOUNTS data:" + acct.toString());
		pstmt = con.prepareStatement("INSERT INTO ACCOUNTS(ACCOUNT_ID,BALANCE,TYPE_ID,STATUS_ID) VALUES(?,?,?,?)");
		pstmt.setInt(1, acct.getAccountId());
		pstmt.setDouble(2, acct.getBalance());
		pstmt.setInt(3, acct.getType());
		pstmt.setInt(4, acct.getStatus());

		rowcnt = pstmt.executeUpdate();
		pstmt.close();
		return rowcnt;
	}

	public static int generateAccountNo() {
		int n = 5;
		int m = (int) Math.pow(10, n - 1);
		return m + new Random().nextInt(9 * m);
	//	int x = (int)((Math.random()) * 100000);
	//	return x;
	}

	public List<AbstractAccount> getAllCustomerAccounts(Connection con,int userId) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AbstractAccount> accList = new ArrayList<AbstractAccount>();
		AbstractAccount acct = null;
		
		int rowcnt = 0;

		log.debug("Getting all accounts for user : " + userId);

		pstmt = con.prepareStatement(
				"  SELECT ACCOUNT_ID,BALANCE,TYPE_ID,STATUS_ID FROM ACCOUNTS WHERE ACCOUNT_ID IN (SELECT ACCOUNT_ID FROM USER_ACCOUNT WHERE USER_ID=?)");
		pstmt.setInt(1, userId);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			String accountType = AccountType.getAccountTypesMap().get(rs.getInt("TYPE_ID"));
			log.debug("Account Type:" + accountType);
			if(accountType.equals(Constants.CHECKING)){
				acct = new CheckingAccount();
			}
			else if(accountType.equals(Constants.SAVINGS)) {
				acct = new SavingsAccount();	
			}
				 
			acct.setStrType(accountType);
			acct.setAccountId(rs.getInt("ACCOUNT_ID"));
			acct.setBalance(rs.getDouble("BALANCE"));
			acct.setType(rs.getInt("TYPE_ID"));
			acct.setStatus(rs.getInt("STATUS_ID"));
			acct.setStrStatus(AccountStatus.getAccountStatusMap().get(rs.getInt("STATUS_ID")));
			accList.add(acct);
			rowcnt++;
		}
		log.debug("Total accounts for user id : " + userId + " is :" + rowcnt);
		pstmt.close();
		return accList;
	}

	public double getAccountBalance(Connection con,int accountId) throws Exception{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		double balance = 0;

		pstmt = con.prepareStatement(
				"SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_ID=?");
		pstmt.setInt(1, accountId);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			balance = rs.getDouble("BALANCE");
			log.debug("Balance in account " + accountId + " is " + balance);
		}
		
		return balance;
	}
	
	public int updateAccountBalance(Connection con,int accountId,double amount) throws Exception{
		PreparedStatement pstmt = null;
		int status = 0; //0:success, 3:account no doesnt exists

		pstmt = con.prepareStatement("UPDATE ACCOUNTS SET BALANCE=? WHERE ACCOUNT_ID=?");
		pstmt.setDouble(1, amount);
		pstmt.setInt(2, accountId);
		int noRows = pstmt.executeUpdate();
		if(noRows == 1) //1 row updated, success
		{
			status = 0;
			log.info("Balance updated for accountId:" + accountId + ". New balance is " + amount);
		}else if(noRows ==0){  //no rows updated, that means invalid account no
			status = 3;
			log.info("AccountId:" + accountId + " is invalid");
		}
		
		return status;
	}
	
	}
