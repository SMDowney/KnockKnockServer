/**
 * ProtocolState
 * 
 * version 1.0
 * 
 * March 12, 2015
 * 
 * Enumeration class that manages the state of the Knock Knock jokes in KnockKnockProtocol
 */

package com.downey.knockknock;

public enum ProtocolState {
	WAITING,
    SENTKNOCKKNOCK,
    SENTCLUE,
    ANOTHER;
}
