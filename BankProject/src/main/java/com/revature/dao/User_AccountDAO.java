package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

public class User_AccountDAO extends AbstractDAO{

	static final Logger log = Logger.getLogger(UsersDAO.class);
	
	public int insertUserAccount(Connection con,int userID,int accountID) throws Exception{
		PreparedStatement  pstmt = null;
		int rowcnt = -1;
		
		log.debug("inserting into USER_ACCOUNT table: " +userID + ", " + accountID);
		
		pstmt = con.prepareStatement("INSERT INTO USER_ACCOUNT(USER_ID,ACCOUNT_ID) VALUES(?,?)");
		pstmt.setInt(1, userID);
		pstmt.setInt(2, accountID);
		
		rowcnt = pstmt.executeUpdate();
		pstmt.close();
		return rowcnt;
		
	}
	
}
