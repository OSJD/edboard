package com.acc.tools.ed.integration.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MSAccessTest {

	Connection connection;
	Statement statement;
	ResultSet resultSet;

	public MSAccessTest() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			System.out.println("DriverLoaded");

			InputStream dbfromJar = getClass().getResourceAsStream("/EngagementDashboard.accdb");

			String path = System.getProperty("java.io.tmpdir");

			System.out.println("The path of Temporary folder is:" + path);

			File localFile = new File(path + "EngagementDashboard.accdb");
			
			localFile.deleteOnExit();
			FileOutputStream fileOutputStream = new FileOutputStream(localFile,true);

			int copyFile;
			
			while ((copyFile = dbfromJar.read()) != -1) {
				fileOutputStream.write(copyFile);
			}
			fileOutputStream.close();
			
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+localFile.getAbsolutePath()+";Uid=dir\\murali.k.gavarasana;Pwd=Amma123!@#;";


			connection = DriverManager.getConnection(url,"dir\\murali.k.gavarasan","Amma123!@#");
			
			statement = connection.createStatement();



			resultSet = statement.executeQuery("SELECT * FROM EDB_PROJECT");
			while (resultSet.next()) {
				String name = resultSet.getString("PROJ_NAME");
				System.out.println("Name of the Project:" + name);
			}

			System.out.println("File Created Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could Not Connect to Database");
		}
	}

	public static void main(String args[]) {
		new MSAccessTest();
	}

}
