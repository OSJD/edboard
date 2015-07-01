package com.acc.tools.ed.database;

import java.io.IOException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author murali.k.gavarasana
 *
 */
@Service
final public class MicroSoftAccessDatabase {
	
	private final Logger log=LoggerFactory.getLogger(MicroSoftAccessDatabase.class);
	
	private static final String accessDBFile="EngagementDashboard.accdb"; 
	
	private Connection edbConnection;
	
	/**
	 * This method will copy the MS Access linked tables with sharepoint from application 
	 * to temporary directory  and open JDBC connection.
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
    public Connection getConnection() throws IOException, SQLException {
    	
    	if(this.edbConnection==null){
    	
			//Read ms access file from classpath
            ProtectionDomain domain = MicroSoftAccessDatabase.class.getProtectionDomain();
            URL location = domain.getCodeSource().getLocation();
           	String path=location.getPath();
           	String dbpath=path.substring(1,path.lastIndexOf("/lib/"))+"/classes/"+accessDBFile;
           	
			log.debug("\t\tEngagementDashBoard Database location:[{}]",dbpath);
			
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=C:\\Users\\sathish.sampath\\Desktop\\EDB\\"+accessDBFile+";";
	
			//MS Access datasource 
			
			this.edbConnection = DriverManager.getConnection(url);
			log.debug("\t\tEngagementDashBoard Database linked with sharepoint successfully!");
    	}
		return this.edbConnection;

    }

}


//http://www.javaworld.com/article/2072937/java-web-development/solving-the-logout-problem-properly-and-elegantly.html