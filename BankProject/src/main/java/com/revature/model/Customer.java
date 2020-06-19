package com.revature.model;

import java.util.List;

import org.apache.log4j.Logger;

public class Customer extends AbstractUser{
	
	static final Logger log = Logger.getLogger(Customer.class);
	List<AbstractAccount> accountList;
	
	public Customer(){
		log.debug("In no args Customer constructor.");
	}


	public List<AbstractAccount> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AbstractAccount> accountList) {
		this.accountList = accountList;
	}


	@Override
	public String toString() {
		int i;
		
		if(this.getAccountList() != null) {
		for(i=0;i<this.getAccountList().size();i++) {
			this.getAccountList().get(i).toString();
		}
		}
		return "Customer [accountList=" + accountList + ", getUserId()=" + getUserId() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + ", getRole()=" + getRoleId()
				+ ", getStrRole()=" + getRoleName() + "]";
	}

	
	
}
