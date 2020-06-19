package com.revature.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.revature.dao.AccountTypeDAO;

public class AccountType {

	private static Map<Integer,String> accountTypesMap;
	static final Logger log = Logger.getLogger(AccountType.class);
	
	private AccountType() {
	};
	
	public static Map<Integer,String> getAccountTypesMap() {
		if(accountTypesMap == null) {
			AccountTypeDAO d = new AccountTypeDAO();
			setAccountTypesMap(d.getAccountTypes());
		}
		return accountTypesMap;
	}
	
	private static void setAccountTypesMap(Map<Integer, String> accountTypesMap) {
		AccountType.accountTypesMap = accountTypesMap;
	}

	
	//returns key for a passed in value from the map
		public static int getKey(String accountType) {
		
			Integer key=0;
			
			Set<Entry<Integer,String>> entrySet = getAccountTypesMap().entrySet();
			for(Entry<Integer,String> m:entrySet) {  //iterate through each entry in map
				if(m.getValue().equalsIgnoreCase(accountType)) {
					key = (Integer)m.getKey();
					log.debug("Found key for "+ accountType+ " , key="+key);
					break;
				}
			}
			return key.intValue();
		}
		
		public static void displayAccountTypes() {

			Iterator<Integer> it = getAccountTypesMap().keySet().iterator();
			while (it.hasNext()) {
				Integer key = (Integer) it.next();
				System.out.println("Key:" + key + " Value:" + getAccountTypesMap().get(key));
			}
		}
	  
}
