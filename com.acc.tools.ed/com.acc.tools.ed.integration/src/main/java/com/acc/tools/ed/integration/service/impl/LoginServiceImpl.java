package com.acc.tools.ed.integration.service.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.LoginDao;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.service.ILoginService;

@Service("iLoginService")
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private LoginDao loginDao;


	public EDBUser searchUser(String name) throws IOException, SQLException {
		return loginDao.searchuser(name); 
		
	}

}
