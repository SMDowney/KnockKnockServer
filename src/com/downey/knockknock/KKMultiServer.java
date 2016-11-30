/**
 * KKMultiServer
 * 
 * version 1.0
 * 
 * March 12, 2015
 * 
 * KKMultiServer that implements the Runnable interface to be ran when passed to
 * a thread.  Connects to the chosen socket and executes a KKMultiServer thread when ran
 */

package com.downey.knockknock;

import java.net.*;
import java.io.*;

public class KKMultiServer implements Runnable{
	
    private boolean listening = true;
    private ServerSocket serverSocket = null;
    private int protocol = 4445;   //default protocol to use
    
    //constructor that passes chosen protocol to connect to
    public KKMultiServer(int protocol){
    	this.protocol = protocol;
    }
    
    
    //method to create the socket for the server
    public void createSocket() {    
        try {
        	listening = true;
            serverSocket = new ServerSocket(protocol);
            
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + protocol);
            System.exit(-1);
        }
    }
    
    //run method that executes when passed to a thread (and is started). Creates new KKMultiThread
    @Override
    public void run() {
    	try{
    		while (listening)
    			new KKMultiServerThread(serverSocket.accept()).start();
    		
    	} catch (IOException ioException){
    		System.err.println("Error creating a new KKMutliServerThread");
    	}
    }
    
    //method to stop the server (closes server's socket)
    public void stopServer(){
        try{
            serverSocket.close();
            
        } catch (IOException ioException){
            System.err.println("Error closing server's socket");
        } 
    }
    
    //returns the host name of the local computer the server is running on
    public String getHostName(){
    	String hostName = null;
    	try{
    		hostName = InetAddress.getLocalHost().getHostName();
    	
    	} catch(UnknownHostException hostException){
    		System.err.println("Error getting host name for server");
    	}
    	return hostName;
    }
    
}
