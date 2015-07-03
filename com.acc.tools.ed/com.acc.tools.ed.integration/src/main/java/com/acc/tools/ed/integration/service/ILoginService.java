package com.acc.tools.ed.integration.service;


import java.io.IOException;
import java.sql.SQLException;

import com.acc.tools.ed.integration.dto.EDBUser;

public interface ILoginService {
	public EDBUser searchUser(String name) throws IOException, SQLException;

	public void updateLogin(long lastLoginTime, Integer employeeId) throws SQLException, IOException;
	
	public void updateLogout(Integer employeeId) throws SQLException, IOException;

	public String updatePassword(String password, Integer integer) ;
}
