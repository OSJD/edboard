package com.acc.tools.ed.server.ui;


import java.awt.EventQueue;
import java.io.IOException;

public class EDBWebStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		

		Runnable runner = new Runnable() {
			public void run() {
					try {
						new ServerRunner();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		};
		
		EventQueue.invokeLater(runner);
	}

}
