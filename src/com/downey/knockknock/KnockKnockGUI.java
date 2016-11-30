/**
 * KnockKnockGUI
 * 
 * version 1.0
 * 
 * March 12, 2015
 * 
 * Main GUI window for the knock knock application.  Holds buttons to start the server, stop the
 * server and have various clients connect.  Also contains text areas to notify user when server
 * has started or stopped.
 */

package com.downey.knockknock;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class KnockKnockGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JPanel serverPanel;
	private JButton startServer;
	private JButton stopServer;
	private JButton startClient;
	private JTextArea serverTextArea;
	private boolean serverOn = false;
	private final KKMultiServer server;
	private final int protocol = 4445;
	
	public KnockKnockGUI(){
		super("Knock-Knock Server");
		
		serverPanel = new JPanel();
		startServer = new JButton("Start Server");
		stopServer = new JButton("Stop Server");
		startClient = new JButton("Start a Client");
		serverTextArea = new JTextArea("Server Stopped. . .", 6 ,22);
		server = new KKMultiServer(protocol);

		serverTextArea.setEditable(false);
		
		//ActionListener to start Knock Knock Server and notify user server has started
		startServer.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
					
						try{
							serverTextArea.selectAll();
							serverTextArea.replaceSelection("Server Started!\n\n"
												+ "Server Name: " + server.getHostName()
												+	"\nServer Protocol: 4445");
							
							if(serverOn == false){
								server.createSocket();
								serverOn = true;
								new Thread(server).start();
							}
								
						}
						catch(Exception exception){
							System.err.println("server exception thrown");
						}
					}
				}
				);
		
		//button that starts the client
		startClient.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						try{
						
						KnockKnockClient client = new KnockKnockClient(protocol);
						client.runClient();
						}catch(IOException ioException){
							System.err.println("Error running client in GUI");
						}		
					}
				}
				);
		
		//ActionListener to stop Knock Knock Server and notify user server has stopped
		stopServer.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						
						server.stopServer();
						serverOn = false;
						serverTextArea.selectAll();
						serverTextArea.replaceSelection("Server Stopped. . .");
					}
				}
				);
		
		
		serverPanel.add(startServer);
		serverPanel.add(stopServer);
		serverPanel.add(startClient);
		serverPanel.add(serverTextArea);
		
		add(serverPanel);
	}
	
	//method to start up the GUI interface
	public void start(){
		setSize(300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
}
