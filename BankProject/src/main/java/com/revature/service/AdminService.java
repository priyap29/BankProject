package com.revature.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.AbstractDAO;
import com.revature.dao.UsersDAO;
import com.revature.model.AbstractUser;

public class AdminService extends AbstractDAO{

	public List<AbstractUser> getAllUsers(){
		UsersDAO userDAO = new UsersDAO();
		List<AbstractUser>  users = new ArrayList<AbstractUser>();

		try {
			getConnection();
			users = userDAO.getAllUsers(con);
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return users;
		
	}
}
