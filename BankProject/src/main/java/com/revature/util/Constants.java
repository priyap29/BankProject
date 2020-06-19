package com.revature.util;

public interface Constants {

	// Transaction types
	public static final String TT_LOGIN = "User Login";
	public static final String TT_REGISTER_CHECKING = "REGISTER CHECKING";
	public static final String TT_REGISTER_SAVINGS = "REGISTER SAVINGS";

	// TYPES OF ACCOUNTS
	public static final String CHECKING = "CHECKING";
	public static final String SAVINGS = "SAVINGS";

	// TYPES OF USER ROLES
	public static final String ADMIN = "ADMIN";
	public static final String STANDARD = "STANDARD";
	public static final String PREMIUM = "PREMIUM";
	public static final String EMPLOYEE = "EMPLOYEE";

	// STATUS OF ACCOUNTS
	public static final String OPEN = "OPEN";
	public static final String CLOSED = "CLOSED";
	public static final String PENDING = "PENDING";
	public static final String DENIED = "DENIED";
	
	//db connection
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String CONN_STRING = "jdbc:oracle:thin:@localhost:1521:ORCL";
	public static final String USERNAME = "priya";
	public static final String PASSWORD = "123";

	//messages
	public static final String ACCOUNT_OPEN_SUCCESS="Account opened successfully!";
	public static final String ACCOUNT_OPEN_FAILED ="Account open failed! Please try again later.";
	public static final String USER_ALREADY_EXISTS ="Username already exists.";
	
	public static final String PASSWORD_UPDATE_SUCCESS="Password updated successfully!";
	public static final String PASSWORD_UPDATE_FAILED ="Password update Failed! Please try again later.";
	
	public static final String PROFILE_UPDATE_SUCCESS="Profile updated successfully!";
	public static final String PROFILE_UPDATE_FAILED ="Profile update Failed! Please try again later.";
	
	public static final String DEPOSIT_SUCCESS="Your amount is deposited!";
	public static final String DEPOSIT_FAILED ="Balance update failed. Please try again later.";
	
	public static final String TRANSFER_SUCCESS="Your amount is transferred successfully!";
	public static final String TRANSFER_FAILED ="Transfer failed. Please try again later.";
	
	public static final String NO_SUFFICIENT_FUNDS ="No sufficient funds.";
	
	public static final String WITHDRAW_SUCCESS="Your balance is updated!";
	public static final String WITHDRAW_FAILURE="Balance update failed. Please try again later.";
	public static final String INVALID_ACCOUNT_NO="Destination account number not valid.";
	
	public static final double PREMIUM_FEE=100;

	
	public static final String PREMIUM_UPGRADE_SUCCESS="Your balance is updated!";
	public static final String NO_FUNDS_FOR_PREMIUM_UPGRADE ="Not enough balance to upgrade to Premium.Please contact customer service at 1-800-000-000.";
	public static final String PREMIUM_UPGRADE_FAILURE="Upgrade to premium failed. Please try again later.";
	
	
	public static final String JOINT_HOLDER_SUCCESS="Joint Holder added successfully!!";
	public static final String JOINT_HOLDER_FAILED ="Adding joint holder failed! Please try again later.";
	
}

