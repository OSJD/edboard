package com.acc.tools.ed.integration.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.LoginDao;
import com.acc.tools.ed.integration.dto.EDBUser;

@Service("loginDao")
public class LoginDaoImpl extends AbstractEdbDao implements LoginDao{
	
	private final Logger log=LoggerFactory.getLogger(LoginDaoImpl.class);
	
	public EDBUser searchuser(String name) throws IOException, SQLException{
		
			final Connection connection=getConnection();
			Statement stmt=connection.createStatement();
			final String loginQuery="SELECT EMP_ID,EMP_EMPLOYEE_ID,EMP_ROLE,EMP_LEVEL FROM EDB_MSTR_EMP_DTLS WHERE EMP_ENTERPRISE_ID='"+name+"'";
			log.debug("loginQuery");
			final ResultSet resultSet = stmt.executeQuery(loginQuery);
			EDBUser user=null;
			while (resultSet.next()) {
				user=new EDBUser();
				user.setEnterpriseId(name);
				user.setEmployeeId(resultSet.getString("EMP_ID"));
				user.setSapId(resultSet.getString("EMP_EMPLOYEE_ID"));
				user.setRole(resultSet.getString("EMP_ROLE"));
				user.setLevel(resultSet.getString("EMP_LEVEL"));
			}

		
		return user;
	}
	
	public EDBUser getEmployeeById(String employeeId) throws IOException, SQLException{
		
		final Connection connection=getConnection();
		Statement stmt=connection.createStatement();
		final String empQuery="SELECT EMP_RESOURCE_NAME,EMP_EMPLOYEE_ID,EMP_ROLE,EMP_LEVEL FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID="+employeeId;
		final ResultSet resultSet = stmt.executeQuery(empQuery);
		EDBUser user=null;
		while (resultSet.next()) {
			user=new EDBUser();
			user.setEnterpriseId(resultSet.getString("EMP_RESOURCE_NAME"));
			user.setEmployeeId(employeeId);
			user.setSapId(resultSet.getString("EMP_EMPLOYEE_ID"));
			user.setRole(resultSet.getString("EMP_ROLE"));
			user.setLevel(resultSet.getString("EMP_LEVEL"));
		}
		return user;
		
	}

}

