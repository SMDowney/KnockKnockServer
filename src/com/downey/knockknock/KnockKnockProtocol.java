/**
 * KnockKnockProtocol
 * 
 * version 1.0
 * 
 * March 12, 2015
 * 
 * Protocol for Knock Knock jokes.  Reads knock knock jokes from .txt files and loads them into their
 * respective arraylists. Beginning joke is randomly selected for each client and then cycles through
 * the list.  Also houses the protocol for the server and defines messages sent from server to client
 */

package com.downey.knockknock;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class KnockKnockProtocol{
    
	//protocol state manages the state of the server protocol in 'processInput' method
    private ProtocolState state = ProtocolState.WAITING;

    private Random beginningJoke = new Random();
    private int currentJoke = beginningJoke.nextInt(4);

    private ArrayList<String> clues = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>(); 

    private BufferedReader jokesIn;
   
    public KnockKnockProtocol(){
    	
    	//reads clues and jokes from respective files and loads them into the arraylists
    	try{
    		String line = null;
    		jokesIn = new BufferedReader(new FileReader("KnockJokes.txt"));
    		while((line = jokesIn.readLine()) != null){
    			clues.add(line);
    		}
    		
    		jokesIn = new BufferedReader(new FileReader("KnockAnswers.txt"));
    		while((line = jokesIn.readLine()) != null){
    			answers.add(line);
    		}
    		jokesIn.close();
    		
    	}
    	catch(IOException ioException){
    		System.err.println("KKProtocol Buffer reader error");
    	}	
    }
    
    //method that process the input and defines what is being sent from the server to client based on state
    public String processInput(String theInput) {
        String theOutput = null;

        if (state == ProtocolState.WAITING) {
            theOutput = "Knock! Knock!";
            state = ProtocolState.SENTKNOCKKNOCK;
        } else if (state == ProtocolState.SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues.get(currentJoke);
                state = ProtocolState.SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
			    "Try again. Knock! Knock!";
            }
        } else if (state == ProtocolState.SENTCLUE) {
            if (theInput.equalsIgnoreCase(clues.get(currentJoke) + " who?")) {
                theOutput = answers.get(currentJoke) + " Want another? (y/n)";
                state = ProtocolState.ANOTHER;
            } else {
                theOutput = "You're supposed to say \"" + 
                clues.get(currentJoke) + 
			    " who?\"" + 
			    "! Try again. Knock! Knock!";
                state = ProtocolState.SENTKNOCKKNOCK;
            }
        } else if (state == ProtocolState.ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (clues.size()-1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = ProtocolState.SENTKNOCKKNOCK;
            } else {
                theOutput = "Bye.";
                state = ProtocolState.WAITING;
            }
        }
        return theOutput;
    }
}
