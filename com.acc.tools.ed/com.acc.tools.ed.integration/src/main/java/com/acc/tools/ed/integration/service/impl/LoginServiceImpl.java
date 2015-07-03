package com.acc.tools.ed.integration.service.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.LoginDao;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.service.ILoginService;

@Service("iLoginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private LoginDao loginDao;

	public EDBUser searchUser(String name) throws IOException, SQLException {
		return loginDao.searchuser(name);

	}
	public EDBUser searchCredentails(String name,String password) throws IOException, SQLException {
		return loginDao.searchCredentails(name,password);

	}
	
	public void updateLogin(long lastLoginTime, Integer employeeId)
			throws SQLException, IOException {

		loginDao.updateLogin(lastLoginTime, employeeId);
	}

	public void updateLogout(Integer employeeId) throws SQLException,
			IOException {

		loginDao.updateLogout(employeeId);
	}
	
	public String updatePassword(String password, Integer empId) {
		
		return loginDao.updatePassword(password,empId);
		
	}

}
