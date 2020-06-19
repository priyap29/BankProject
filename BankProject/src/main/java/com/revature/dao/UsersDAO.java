package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.AbstractAccount;
import com.revature.model.AbstractUser;
import com.revature.model.Customer;
import com.revature.model.Roles;
import com.revature.model.TransactionLog;
import com.revature.util.Constants;

public class UsersDAO extends AbstractDAO {

	static final Logger log = Logger.getLogger(UsersDAO.class);

	public int validateLogin(String username, String enteredPassword) {

		int validUser = 2; // 0 - valid user, 1 - invalid user, 2 - exception
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			getConnection();
			pstmt = con.prepareStatement("SELECT PASSWD FROM USERS WHERE UPPER(USERNAME)=?");
			pstmt.setString(1, username.toUpperCase());
			rs = pstmt.executeQuery();

			if (!rs.next()) { // user not found in DB, invalid user
				validUser = 1;
				log.info("Username: " + username + " not found in DB.Invalid Login.");
			} else { // user found, lets check if user entered correct password
				String correctPass = rs.getString("PASSWD");
				if (correctPass.equals(enteredPassword)) {
					validUser = 0; // passwords match, valid user

					// enter user login time in transaction log
					TransactionLog tlog = new TransactionLog();
					tlog.setUsername(username);
					long millis = System.currentTimeMillis();
					java.sql.Timestamp date = new java.sql.Timestamp(millis);
					tlog.setTransactionDate(date);
					tlog.setTransactionType(Constants.TT_LOGIN);
					TransactionLogDAO tdao = new TransactionLogDAO();
					tdao.insertTable(con, tlog);
				} else {
					validUser = 1; // password doesnt match,invalid user
					log.info("Pasword for username: " + username + " didnt match.Invalid Login.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			validUser = 2;
		} finally {
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return validUser;
	}

	public int updatePassword(String username, String password) {

		int status = 2; // 0 - update successful, 2 - exception or unsuccessful
		PreparedStatement pstmt = null;

		try {
			getConnection();

			pstmt = con.prepareStatement("UPDATE USERS SET PASSWD=? WHERE UPPER(USERNAME)=?");
			pstmt.setString(1, password);
			pstmt.setString(2, username.toUpperCase());
			int rowcnt = pstmt.executeUpdate();

			if (rowcnt == 1) { // 1 row updated, success
				status = 0;
				log.info("Password updated for username:" + username);
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	public int checkUsernameExists(String username) {
		int openAccount = 1;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			getConnection();
			pstmt = con.prepareStatement("SELECT 1 FROM USERS WHERE UPPER(USERNAME)=?");
			pstmt.setString(1, username.toUpperCase());
			rs = pstmt.executeQuery();
			if (!rs.next()) { // user not found in DB,this is a new user...open account for him
				openAccount = 0;
				log.info("Username:" + username + " doesnt already exists.");
			} else {
				log.info("Username:" + username + " already exists.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			openAccount = 2;
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return openAccount;

	}

	
	public int getUserId(Connection con, String username) throws Exception {
		PreparedStatement pstmt = null;
		int userID = -1;
		ResultSet rs = null;

		pstmt = con.prepareStatement("SELECT USER_ID FROM USERS WHERE UPPER(USERNAME)=?");
		pstmt.setString(1, username.toUpperCase());
		rs = pstmt.executeQuery();
		while (rs.next()) {
			userID = rs.getInt("USER_ID");
			log.debug("Userid: " + userID + " found in users table for username: " + username);
		}
		rs.close();
		pstmt.close();
		return userID;
	}

	public int insertUsers(Connection con, Customer cust) throws Exception {
		PreparedStatement pstmt = null;
		int rowcnt = -1;
	

		pstmt = con.prepareStatement(
				"INSERT INTO USERS(USERNAME,PASSWD,FIRSTNAME,LASTNAME,EMAIL,ROLE_ID) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, cust.getUsername());
		pstmt.setString(2, cust.getPassword());
		pstmt.setString(3, cust.getFirstName());
		pstmt.setString(4, cust.getLastName());
		pstmt.setString(5, cust.getEmail());
		pstmt.setInt(6, cust.getRoleId());

		rowcnt = pstmt.executeUpdate();
	
		if(rowcnt == 1) 
			log.debug("Inserted into users table data: " + cust.toString());
		pstmt.close();
		return rowcnt;
	}

	public int getUser(Connection con,AbstractUser cust,String username) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int rowcnt = 2; //0: success, 2:otherwise

		log.debug("getting user details for user : " + username);

		pstmt = con.prepareStatement(
				"SELECT USER_ID,PASSWD,FIRSTNAME,LASTNAME,EMAIL,ROLE_ID from USERS WHERE UPPER(USERNAME)=? ORDER BY USER_ID");
		pstmt.setString(1, username.toUpperCase());
		rs = pstmt.executeQuery();
		if(rs.next()) {
			cust.setUsername(username);
			cust.setUserId(rs.getInt("USER_ID"));
			cust.setPassword(rs.getString("PASSWD"));
			cust.setFirstName(rs.getString("FIRSTNAME"));
			cust.setLastName(rs.getString("LASTNAME"));
			cust.setEmail(rs.getString("EMAIL"));
			int roleID =rs.getInt("ROLE_ID");
			cust.setRoleId(roleID);
			cust.setRoleName(Roles.getRoleMap().get(roleID));
			rowcnt = 0;//success
		}
		//log.debug("Retrieved customer details" + cust.toString());
		pstmt.close();
		return rowcnt;
	}

	
	public int getUserRole(String username) {
		PreparedStatement pstmt = null;
		int userRole = -1;
		ResultSet rs = null;
		try {
			getConnection();

			pstmt = con.prepareStatement("SELECT ROLE_ID FROM USERS WHERE UPPER(USERNAME)=?");
			pstmt.setString(1, username.toUpperCase());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userRole = rs.getInt("ROLE_ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				pstmt.close();
				rs.close();
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
		log.debug(username +" has role_id as "+userRole);
		return userRole;
	}

	public int updateUserProfile(Customer cust) {
		int status = 2; // 0 - update successful, 2 - exception or unsuccessful
		PreparedStatement pstmt = null;

		try {
			getConnection();

			pstmt = con.prepareStatement("UPDATE USERS SET FIRSTNAME=?,LASTNAME=?,EMAIL=?,PASSWD=?,ROLE_ID=? WHERE UPPER(USERNAME)=?");
			pstmt.setString(1, cust.getFirstName());
			pstmt.setString(2, cust.getLastName());
			pstmt.setString(3, cust.getEmail());
			pstmt.setString(4, cust.getPassword());
			pstmt.setInt(5, cust.getRoleId());
			pstmt.setString(6, cust.getUsername().toUpperCase());
			
			int rowcnt = pstmt.executeUpdate();

			if (rowcnt == 1) { // 1 row updated, success
				status = 0;
				log.info("profile updated for username:" + cust.getUsername());
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;

	}

	public List<AbstractUser> getAllUsers(Connection con) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		AccountsDAO acctDAO = new AccountsDAO();
		List<AbstractUser>  users = new ArrayList<AbstractUser>();

		
		log.debug("getting all users details");

		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT USER_ID,USERNAME,FIRSTNAME,LASTNAME,EMAIL,ROLE_ID from USERS ORDER BY USER_ID");
		while(rs.next()) {
			Customer user = new Customer();
			List<AbstractAccount> accountList =  new ArrayList<AbstractAccount>();
			user.setUserId(rs.getInt("USER_ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setFirstName(rs.getString("FIRSTNAME"));
			user.setLastName(rs.getString("LASTNAME"));
			user.setEmail(rs.getString("EMAIL"));
			int roleID =rs.getInt("ROLE_ID");
			user.setRoleId(roleID);
			user.setRoleName(Roles.getRoleMap().get(roleID));
			//get all accounts related to this customer
			accountList = acctDAO.getAllCustomerAccounts(con,user.getUserId());
			user.setAccountList(accountList);
			users.add(user);
			
		}
		log.debug("Number of users retrieved :" +users.size());
		stmt.close();
		return users;
		
	}

}
