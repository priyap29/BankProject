package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import com.revature.util.Constants;


public class AbstractDAO {

	public Connection con = null;

	public void getConnection() throws Exception {
		// step1 load the driver class
		Class.forName(Constants.ORACLE_DRIVER);

		// step2 create the connection object
		this.con = DriverManager.getConnection(Constants.CONN_STRING, Constants.USERNAME, Constants.PASSWORD);
		if(this.con==null) {
			throw new Exception("Connection object is null.");
		}
	}

}
