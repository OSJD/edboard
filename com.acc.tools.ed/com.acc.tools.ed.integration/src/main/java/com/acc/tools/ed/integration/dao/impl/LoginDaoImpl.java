package com.acc.tools.ed.integration.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jdt.core.dom.CatchClause;
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
			final String loginQuery="SELECT P.PROJ_ID,P.PROJ_NAME, E.EMP_ID,E.EMP_EMPLOYEE_ID,E.EMP_ROLE,E.EMP_LEVEL,E.EMP_SUP_EMP_ID, E.LOGOUT, E.LASTLOGINTIME ,E.PASSWORD,E.PWDFLAG FROM "
					+ "(EDB_MSTR_EMP_DTLS E LEFT JOIN  EDB_PROJ_EMP PE ON E.EMP_ID=PE.EMP_ID) "
					+ " LEFT JOIN EDB_PROJECT P ON P.PROJ_ID=PE.PROJ_ID WHERE E.EMP_ENTERPRISE_ID='"+name+"'";
			log.debug(loginQuery);
			final ResultSet resultSet = stmt.executeQuery(loginQuery);
			EDBUser user=null;
			while (resultSet.next()) {
				user=new EDBUser();
				user.setEnterpriseId(name);
				user.setEmployeeId(resultSet.getInt("EMP_ID"));
				user.setSapId(resultSet.getInt("EMP_EMPLOYEE_ID"));
				user.setRole(resultSet.getString("EMP_ROLE"));
				user.setLevel(resultSet.getString("EMP_LEVEL"));
				user.setSupervisorId(resultSet.getInt("EMP_SUP_EMP_ID"));
				user.setProjectId(resultSet.getInt("PROJ_ID"));
				user.setProjectName(resultSet.getString("PROJ_NAME"));
				user.setLogout(resultSet.getBoolean("LOGOUT"));
				user.setLastLoginTime(resultSet.getLong("LASTLOGINTIME"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setPasswordFlag(resultSet.getString("PWDFLAG"));
			};

		
		return user;
	}
	
	public EDBUser searchCredentails(String name,String password) throws IOException, SQLException{
		
		final Connection connection=getConnection();
		Statement stmt=connection.createStatement();
		final String loginQuery="SELECT P.PROJ_ID,P.PROJ_NAME, E.EMP_ID,E.EMP_EMPLOYEE_ID,E.EMP_ROLE,E.EMP_LEVEL,E.EMP_SUP_EMP_ID, E.LOGOUT, E.LASTLOGINTIME FROM "
				+ "(EDB_MSTR_EMP_DTLS E LEFT JOIN  EDB_PROJ_EMP PE ON E.EMP_ID=PE.EMP_ID) "
				+ " LEFT JOIN EDB_PROJECT P ON P.PROJ_ID=PE.PROJ_ID WHERE E.EMP_ENTERPRISE_ID='"+name+"' , E.password = '"+password+"'";
		log.debug(loginQuery);
		final ResultSet resultSet = stmt.executeQuery(loginQuery);
		EDBUser user=null;
		while (resultSet.next()) {
			user=new EDBUser();
			user.setEnterpriseId(name);
			user.setEmployeeId(resultSet.getInt("EMP_ID"));
			user.setSapId(resultSet.getInt("EMP_EMPLOYEE_ID"));
			user.setRole(resultSet.getString("EMP_ROLE"));
			user.setLevel(resultSet.getString("EMP_LEVEL"));
			user.setSupervisorId(resultSet.getInt("EMP_SUP_EMP_ID"));
			user.setProjectId(resultSet.getInt("PROJ_ID"));
			user.setProjectName(resultSet.getString("PROJ_NAME"));
			user.setLogout(resultSet.getBoolean("LOGOUT"));
			user.setLastLoginTime(resultSet.getLong("LASTLOGINTIME"));
		};

	
	return user;
}
	
	public EDBUser getEmployeeById(Integer employeeId) throws IOException, SQLException{
		
		final Connection connection=getConnection();
		Statement stmt=connection.createStatement();
		final String empQuery="SELECT EMP_RESOURCE_NAME,EMP_EMPLOYEE_ID,EMP_ROLE,EMP_LEVEL, LOGOUT, LASTLOGINTIME FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID="+employeeId;
		log.debug("Get Employee by Id Query:{}",empQuery);
		final ResultSet resultSet = stmt.executeQuery(empQuery);
		EDBUser user=null;
		while (resultSet.next()) {
			user=new EDBUser();
			user.setEnterpriseId(resultSet.getString("EMP_RESOURCE_NAME"));
			user.setEmployeeId(employeeId);
			user.setSapId(resultSet.getInt("EMP_EMPLOYEE_ID"));
			user.setRole(resultSet.getString("EMP_ROLE"));
			user.setLevel(resultSet.getString("EMP_LEVEL"));
			user.setLogout(resultSet.getBoolean("LOGOUT"));
			user.setLastLoginTime(resultSet.getLong("LASTLOGINTIME"));
		}
		return user;
		
	}
	
	
	public void updateLogin(long lastLoginTime, Integer employeeId) throws SQLException, IOException{
		final Connection connection=getConnection();
		Statement stmt=connection.createStatement();
		final String updateLoginQuery="UPDATE EDB_MSTR_EMP_DTLS SET LASTLOGINTIME="+lastLoginTime+ ", LOGOUT=NO WHERE EMP_ID="+employeeId;
		log.debug(updateLoginQuery);
		stmt.executeUpdate(updateLoginQuery);
	}
	
	public void updateLogout(Integer employeeId) throws SQLException, IOException{
		final Connection connection=getConnection();
		Statement stmt=connection.createStatement();
		final String updateLogoutQuery="UPDATE EDB_MSTR_EMP_DTLS SET LOGOUT=YES WHERE EMP_ID="+employeeId;
		log.debug(updateLogoutQuery);
		stmt.executeUpdate(updateLogoutQuery);
	}

	

	public String updatePassword(String password, Integer empId)  {
		
		try{
			
		
		final Connection connection=getConnection();
		Statement stmt=connection.createStatement();
		final String updatePwdQuery="UPDATE EDB_MSTR_EMP_DTLS SET PWDFLAG=? ,PASSWORD = ? WHERE EMP_ID= ?";
		log.debug(updatePwdQuery);
		
		int status =0;
		PreparedStatement  preparedStatement = getConnection().prepareStatement(updatePwdQuery);
		preparedStatement.setString(1, "YES");
		preparedStatement.setString(2, password);
		preparedStatement.setInt(3, empId);
		status=preparedStatement.executeUpdate();
		preparedStatement.close();
		
		System.out.println("rows UPDATED");
		//rowcount= selectStatement.executeUpdate(delIdQuery);
		if(status==0)
		{
			System.out.println("update failure");

		}
		else{

			System.out.println("update success");
			return "success";
		}
		return "failure";
	}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		return "failure";
	}
	
	

}

