package com.revature.model;

import java.sql.Timestamp;

public class TransactionLog {

	int transactionID; 
	String username;
	int accountID;
    double amount;
    String TransactionType;
    Timestamp TransactionDate;
    
    
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return TransactionType;
	}
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}
	
	public Timestamp getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Timestamp transactionDate) {
		TransactionDate = transactionDate;
	}
	@Override
	public String toString() {
		return "TransactionLog [transactionID=" + transactionID + ", username=" + username + ", accountID=" + accountID
				+ ", amount=" + amount + ", TransactionType=" + TransactionType + ", TransactionDate=" + TransactionDate
				+ "]";
	}
    
    
    
}
