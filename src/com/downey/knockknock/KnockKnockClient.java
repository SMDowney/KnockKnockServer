/**
 * KnockKnockClient
 * 
 * version 1.0
 * 
 * March 12, 2015
 * 
 * KnockKnockClient class that holds the client window when execute from main
 * GUI window.  Starts and connect socket on client's side and initiates the client/server
 * conversation.
 */

package com.downey.knockknock;

import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.IOException;
import javax.swing.JScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JOptionPane;

public class KnockKnockClient {
	
	private JFrame clientFrame = new JFrame("Client Started");
	private JPanel clientPanel = new JPanel();
	private JTextArea textArea = new JTextArea(11,45);
	private JScrollPane scrollPane = new JScrollPane(textArea);
	
	private final JTextField clientResponse = new JTextField(19);
	
	private Socket kkSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String fromServer;
    private String fromUser;
    private int protocol = 4445;  //default protocol to use
    
    //constructor for client - creates client window when launched from main GUI
    public KnockKnockClient(int protocol){
    	
    	this.protocol = protocol;
    	
		clientResponse.addKeyListener(
				new KeyAdapter(){
					public void keyPressed(KeyEvent event){
						if(event.getKeyCode() == KeyEvent.VK_ENTER){
							fromUser = clientResponse.getText();
							clientConvo();
							clientResponse.selectAll();
							clientResponse.replaceSelection("");
							
						}
					}
				}
				);
		
		scrollPane.scrollRectToVisible(scrollPane.getBounds());
		
		textArea.setEditable(false);
		clientPanel.add(scrollPane);
		clientPanel.add(clientResponse);
		
		clientFrame.add(clientPanel);
		clientFrame.setSize(600,250);
		clientFrame.setVisible(true);
    }
    
    //method that 'runs' the client (starts socket and connects to same server host/protocol) 
    public void runClient() throws IOException {
    	String hostName = InetAddress.getLocalHost().getHostName();
        
        
        try {
            kkSocket = new Socket(hostName, protocol);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            clientConvo();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + InetAddress.getLocalHost().getHostName());
            System.exit(1);
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Server not started - client cannot connect without server started");
        	clientFrame.dispose();
            System.err.println("Couldn't get I/O for the connection to: " + InetAddress.getLocalHost().getHostName());
        }     
    }
    
    //method that initiates the conversation between client and server
    public void clientConvo(){
    	try{
            if (fromUser != null) {
            		textArea.append("Client: " + fromUser +"\n");
            		out.println(fromUser);
            }
            
    		if ((fromServer = in.readLine()) != null) {				
                textArea.append("Server: " + fromServer + "\n");       	
                if (fromServer.equals("Bye.")){
                	clientFrame.dispose();
                	closeClient();
                }
            }	
    	} catch(IOException ioException){
    		System.err.println("This won't work");
    	}
    }
    
    //method that closes the client (input/output streams and socket)
    public void closeClient(){
    	try{
    		out.close();
    		in.close();
    		kkSocket.close();
    	} catch(IOException ioException){
    		System.err.println("Error closing the client");
    	}
    }   
}
