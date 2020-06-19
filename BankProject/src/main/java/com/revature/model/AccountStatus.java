package com.revature.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.revature.dao.AccountStatusDAO;

public class AccountStatus {
	private static Map<Integer, String> accountStatusMap;
	static final Logger log = Logger.getLogger(AccountStatus.class);

	private AccountStatus() {
	};

	public static Map<Integer, String> getAccountStatusMap() {
		if (accountStatusMap == null) {
			AccountStatusDAO d = new AccountStatusDAO();
			setAccountStatusMap(d.getAccountStatus());
		}
		return accountStatusMap;
	}

	private static void setAccountStatusMap(Map<Integer, String> accountStatusMap) {
		AccountStatus.accountStatusMap = accountStatusMap;
	}

	// returns key for a passed in value from the map
	public static int getKey(String accountStatus) {

		Integer key = 0;

		Set<Entry<Integer, String>> entrySet = getAccountStatusMap().entrySet();
		for (Entry<Integer, String> m : entrySet) { // iterate through each entry in map
			if (m.getValue().equalsIgnoreCase(accountStatus)) {
				key = (Integer) m.getKey();
				log.debug("Key for " + accountStatus + " is " +key);
				break;
			}
		}
		return key.intValue();
	}

	public static void displayAccountStatus() {

		Iterator<Integer> it = getAccountStatusMap().keySet().iterator();
		while (it.hasNext()) {
			Integer key = (Integer) it.next();
			System.out.println("Key:" + key + " Value:" + getAccountStatusMap().get(key));
		}
	}
}
