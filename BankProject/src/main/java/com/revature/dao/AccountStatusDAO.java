package com.revature.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class AccountStatusDAO extends AbstractDAO {

	static final Logger log = Logger.getLogger(RolesDAO.class);
	
	public AccountStatusDAO() {

	};

	/*
	 * Gets account status from db and stores in a Map
	 */
	public Map<Integer, String> getAccountStatus() {
		Map<Integer, String> accountStatusMap = new HashMap<Integer, String>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			getConnection();

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT STATUS_ID,STATUS FROM ACCOUNT_STATUS ORDER BY STATUS_ID");
			log.debug("quereid roles table, rs:"+ rs.getFetchSize());
			while (rs.next()) {
				accountStatusMap.put(rs.getInt("STATUS_ID"), rs.getString("STATUS"));
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

		return accountStatusMap;
	}

}
