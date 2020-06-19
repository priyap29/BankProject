package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class RolesDAO extends AbstractDAO {

	static final Logger log = Logger.getLogger(RolesDAO.class);

	public RolesDAO() {

	}

	public Map<Integer, String> getRoles() {
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		ResultSet rs = null;
		Statement stmt = null;

		try {
			getConnection();

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT ROLE_ID,ROLE FROM ROLES ORDER BY ROLE_ID");
			log.debug("quereid roles table, rs:"+ rs.getFetchSize());
			while (rs.next()) {
				roleMap.put(rs.getInt("ROLE_ID"), rs.getString("ROLE"));
				log.debug(rs.getInt("ROLE_ID") + ":" + rs.getString("ROLE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return roleMap;
	}
	
	public int updateRoleId(Connection con,int userId,int newRoleId) throws Exception{
		PreparedStatement pstmt = null;
		int status = 0; //0:success, 3:userId doesnt exists

		log.debug("Setting new role to " + newRoleId + " for userid " + userId);
		pstmt = con.prepareStatement("UPDATE USERS SET ROLE_ID=? WHERE USER_ID=?");
		pstmt.setDouble(1, newRoleId);
		pstmt.setInt(2, userId);
		int noRows = pstmt.executeUpdate();
		if(noRows == 1) //1 row updated, success
		{
			status = 0;
			log.info("RoleId updated");
		}else if(noRows ==0){  //no rows updated, that means invalid account no
			status = 3;
			log.info("UuserId not found to update role");
		}
		
		return status;
	}
	

}
