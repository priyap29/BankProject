package com.revature.model;

import org.apache.log4j.Logger;

import com.revature.util.Constants;

public class CheckingAccount extends AbstractAccount{
	static final Logger log = Logger.getLogger(CheckingAccount.class);
	public CheckingAccount() {};
	
	public CheckingAccount(double balance)
	{
		this.setBalance(balance);
		this.setType(AccountType.getKey(Constants.CHECKING));
	}

	@Override
	public String toString() {
		return "CheckingAccount [getStrStatus()=" + getStrStatus() + ", getStrType()=" + getStrType()
				+ ", getAccountId()=" + getAccountId() + ", getBalance()=" + getBalance() + ", getStatus()="
				+ getStatus() + ", getType()=" + getType() + "]";
	}

	

	
}
