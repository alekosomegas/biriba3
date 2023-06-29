package com.akgames.biriba3.events;


public interface GameEvent {
	
	public void execute();
	/** Return false if it cannot be undone */
	public boolean undo();
	boolean allowed();
}
