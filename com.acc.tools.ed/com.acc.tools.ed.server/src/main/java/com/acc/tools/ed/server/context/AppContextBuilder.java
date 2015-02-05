package com.acc.tools.ed.server.context;

import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.webapp.WebAppContext;


public class AppContextBuilder {
	


	public WebAppContext buildWebAppContext(){
		final WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/edb");
        ProtectionDomain domain = AppContextBuilder.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
       	String path=location.getPath();
        if(path==null)
        	throw new RuntimeException("EDB War file not loaded properly!");
        else
           	path=path.substring(1, path.lastIndexOf("/"))+"/edb.war";    
        System.out.println("WAR Path:"+path);
        
		webAppContext.setWar(path);

		return webAppContext;
	}

}
