/**
 * KKMultiServerThread
 * 
 * version 1.0
 * 
 * March 12, 2015
 * 
 * MultiServerThread class that extends the Thread class
 */
package com.downey.knockknock;

import java.net.*;
import java.io.*;

public class KKMultiServerThread extends Thread {
    private Socket socket = null;

    public KKMultiServerThread(Socket socket) {
	super("KKMultiServerThread");
	this.socket = socket;
    }

    
    //method that run when thread is created
    @Override
    public void run() {
    	try {
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    		BufferedReader in = new BufferedReader(
    					new InputStreamReader(
    					socket.getInputStream()));

    		String inputLine, outputLine;
    		KnockKnockProtocol kkp = new KnockKnockProtocol();
    		outputLine = kkp.processInput(null);
    		out.println(outputLine);

    		while ((inputLine = in.readLine()) != null) {
    			outputLine = kkp.processInput(inputLine);
    			out.println(outputLine);
    			if (outputLine.equals("Bye"))
    				break;
    		}
    		out.close();
    		in.close();
    		socket.close();

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }    
}
