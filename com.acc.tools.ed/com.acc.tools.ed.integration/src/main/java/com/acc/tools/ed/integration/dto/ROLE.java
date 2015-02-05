package com.acc.tools.ed.integration.dto;

public enum ROLE {
	
	DEVELOPER(new String[]{"Dvlp","Testing","BA"}),
	SUPERVISOR(new String[]{"Lead","Module Lead","Test Lead"}),
	MANAGER(new String[]{"M"});
	
	private String[] roles;
	private ROLE(String[] roles){
		this.roles=roles;
	}
	
	public static String getActualRole(String roleName){
		for(ROLE role:values()){
			for(String lowLevelRoleName :role.roles){
				if(lowLevelRoleName.equalsIgnoreCase(roleName))
					return role.name();
			}
		}
		return "NOROLE";
	}

}
