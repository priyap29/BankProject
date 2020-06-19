package com.revature.model;

import org.apache.log4j.Logger;

public class Admin extends AbstractUser{
	
	static final Logger log = Logger.getLogger(Admin.class);
	
	public Admin(){
		log.debug("In no args Admin constructor.");
	}


	@Override
	public String toString() {
	
		return "Admin [getUserId()=" + getUserId() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + ", getRole()=" + getRoleId()
				+ ", getStrRole()=" + getRoleName() + "]";
	}

	
	
}
