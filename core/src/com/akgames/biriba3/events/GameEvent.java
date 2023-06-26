package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;

// TODO: rename
// TODO: perhaps no need to overload. can use constructor
public interface GameEvent {
	
	GameController GAME_CONTROLLER = GameController.getInstance();
	public void execute();
	/** Return false if it cannot be undone */
	public boolean undo();
	boolean allowed();
}
