package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;
import com.revature.model.TransactionLog;

public class TransactionLogDAO{

	static final Logger log = Logger.getLogger(TransactionLogDAO.class);
	
	public TransactionLogDAO() {
		
	}
	
	public int insertTable(Connection con,TransactionLog tlog) throws Exception{
		PreparedStatement pstmt = null;
		int rowcnt = -1;
		
		log.debug("Inserting into TRANSACTION_LOG data:" + tlog.toString());
		pstmt = con.prepareStatement("INSERT INTO TRANSACTION_LOG(USERNAME,ACCOUNT_ID,AMOUNT,TTYPE,TRANSACTION_DATE) VALUES(?,?,?,?,?)");
		pstmt.setString(1, tlog.getUsername());
		pstmt.setInt(2, tlog.getAccountID());
		pstmt.setDouble(3, tlog.getAmount());
		pstmt.setString(4, tlog.getTransactionType());
		pstmt.setTimestamp(5, tlog.getTransactionDate());
		
		rowcnt = pstmt.executeUpdate();
		pstmt.close();
		return rowcnt;
	}
}
