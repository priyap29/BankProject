package com.revature.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.dao.RolesDAO;

/*
 * This is class with only static private map for roles
 */
public class Roles {
	private static Map<Integer,String> roleMap;
	static final Logger log = Logger.getLogger(Roles.class);

	private Roles() {
	};
	
	public static Map<Integer,String> getRoleMap() {
		if(roleMap == null) {
			RolesDAO d = new RolesDAO();
			setRoleMap(d.getRoles());
		}
		return roleMap;
	}
	
	private static void setRoleMap(Map<Integer, String> roleMap) {
		Roles.roleMap = roleMap;
	}
	
	
	//returns role_id for role_name
	public static int getKey(String roleName) {
	
		Integer key=0;
		log.debug("getting roleMap");
		
		Set<Entry<Integer,String>> entrySet = getRoleMap().entrySet();
		log.debug("using for loop");
		for(Entry<Integer,String> m:entrySet) {  //iterate through each entry in map
			if(m.getValue().equalsIgnoreCase(roleName)) {
				key = (Integer)m.getKey();
				log.debug("Key for " + roleName + " is " +key);
				break;
			}
		}
		if(key==0) {
			log.debug("key not found");
		}
		return key.intValue();
	}
	
	public static void displayRoles() {
		
		Iterator<Integer> it = getRoleMap().keySet().iterator();	
		while(it.hasNext()) {
			Integer key =(Integer) it.next();
			log.debug("Key:" + key + " Value:" +getRoleMap().get(key));
		}
	}
	  
}
