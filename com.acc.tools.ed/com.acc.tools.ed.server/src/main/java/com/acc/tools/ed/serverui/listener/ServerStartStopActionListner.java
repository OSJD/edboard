package com.acc.tools.ed.serverui.listener;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.acc.tools.ed.server.JettyServer;

public class ServerStartStopActionListner implements ActionListener {

	private final JettyServer jettyServer;

	public ServerStartStopActionListner(JettyServer jettyServer) {
		this.jettyServer = jettyServer;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		 JButton btnStartStop =  (JButton) actionEvent.getSource();
		 if(jettyServer.isStarted()){
			 btnStartStop.setText("Stopping...");
			 btnStartStop.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			 try {
				jettyServer.stop();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			 btnStartStop.setText("Start");
			 btnStartStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		 }
		 else if(jettyServer.isStopped()){
			 btnStartStop.setText("Starting...");
			 btnStartStop.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			 try {
				jettyServer.start();
				Runtime.getRuntime().exec(new String [] {"rundll32", "url.dll,FileProtocolHandler","http://localhost:8585/edb"});
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			 btnStartStop.setText("Stop");
			 btnStartStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		 }
		 
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					if(jettyServer.isStarted()) {
						try {
							jettyServer.stop();
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				}
		},"Stop Jetty Hook")); 		 
	}
	
	
}
