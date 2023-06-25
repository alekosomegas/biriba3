package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;

import java.util.List;

// TODO: rename
// TODO: perhaps no need to overload. can use constructor
public interface PlayerAction {
	
	GameController gamelogic = GameController.getInstance();
	
	public void execute();
	
	public void execute(List<?> params);
	
	public void undo();
	
	boolean allowed();
}
