package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;

public class StartGameEvent implements GameEvent {
	Turn turnPhase;
	
	public StartGameEvent() {
		// Initiates the Turn
		turnPhase = Turn.getInstance();
		// Deal the cards
		GameController.getInstance().handleAction(new DealEvent());
	}
	
	@Override
	public void execute() {
	
	}
	
	@Override
	public boolean undo() {
		return false;
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
