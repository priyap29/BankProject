package com.revature.model;

import org.apache.log4j.Logger;

import com.revature.util.Constants;

public class SavingsAccount extends AbstractAccount{
	
	double interestRate = 0.9;
	static final Logger log = Logger.getLogger(SavingsAccount.class);
	
	public SavingsAccount() {};
	
	public SavingsAccount(double balance)
	{
		this.setBalance(balance);
		this.setType(AccountType.getKey(Constants.SAVINGS));
	}

	
	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	@Override
	public String toString() {
		return "SavingsAccount [interestRate=" + interestRate + ", getStrStatus()=" + getStrStatus() + ", getStrType()="
				+ getStrType() + ", getAccountId()=" + getAccountId() + ", getBalance()=" + getBalance()
				+ ", getStatus()=" + getStatus() + ", getType()=" + getType()  + "]";
	}

	

	
}
