package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;

public class StartGame implements GameEvent {
	Turn turnPhase;
	
	public StartGame() {
		// Initiates the Turn
		turnPhase = Turn.getInstance();
		// Deal the cards
		GameController.getInstance().handleAction(new DealAction());
	}
	
	@Override
	public void execute() {
	
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
