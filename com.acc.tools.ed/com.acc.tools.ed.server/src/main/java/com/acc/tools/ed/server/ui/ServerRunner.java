package com.acc.tools.ed.server.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.acc.tools.ed.server.JettyServer;
import com.acc.tools.ed.server.context.AppContextBuilder;
import com.acc.tools.ed.serverui.listener.ServerStartStopActionListner;

public class ServerRunner extends JFrame{
	private static final long serialVersionUID = 8261022096695034L;
	


	public ServerRunner() throws IOException {
		super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        JPanel contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setBackground(new Color(135,206,250));
        contentPane.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentPane.setLayout(null);
        InputStream is1=ServerRunner.class.getClassLoader().getResourceAsStream("image/logo_edb_sm.jpg");
        ImageIcon hcscImage = new ImageIcon(ImageIO.read(is1));
        JLabel hcscImageLabel = new JLabel(hcscImage);
        hcscImageLabel.setSize(630, 80);
        hcscImageLabel.setLocation(3, 3);
        contentPane.add(hcscImageLabel);
        
        JButton btnClose = new JButton("Close");
        btnClose.setSize(100, 30);
        btnClose.setLocation(278, 146);
        contentPane.add(btnClose);
        setContentPane(contentPane);
        
        final JButton btnStartStop = new JButton("Start");
		btnStartStop.setActionCommand("Start EDB");
        btnStartStop.setSize(100, 30);
        btnStartStop.setLocation(148, 146);
        final JettyServer jettyServer=createServer();
		btnStartStop.addActionListener(new ServerStartStopActionListner(jettyServer));
        contentPane.add(btnStartStop);
        

        
		//Window size
		setSize(635,200);
		//Window center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

       // setLocationByPlatform(true);
        setVisible(true);
		
		//Close/Terminate Main window
		btnClose.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	setVisible(false);
            	dispose();
            	System.out.println("*********** Server window closed **************");
            }
        });   
	}
	
	private JettyServer createServer(){
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		
		contexts.setHandlers(new Handler[] {new AppContextBuilder().buildWebAppContext()});
		
		final JettyServer jettyServer = new JettyServer();
		jettyServer.setHandler(contexts);
		return jettyServer;
		
	}

}

