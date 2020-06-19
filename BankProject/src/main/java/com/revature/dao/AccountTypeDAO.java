package com.revature.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class AccountTypeDAO extends AbstractDAO {

	static final Logger log = Logger.getLogger(AccountTypeDAO.class);
	public AccountTypeDAO() {

	}

	/*
	 * Gets types of accounts from db and stores in a Map
	 */
	public Map<Integer, String> getAccountTypes() {
		Map<Integer, String> accountTypesMap = new HashMap<Integer, String>();
		ResultSet rs = null;
		Statement stmt = null;

		try {
			getConnection();

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT TYPE_ID,ATYPE FROM ACCOUNT_TYPE ORDER BY TYPE_ID");
			while (rs.next()) {
				
				accountTypesMap.put(rs.getInt("TYPE_ID"), rs.getString("ATYPE"));
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

		return accountTypesMap;
	}

}
