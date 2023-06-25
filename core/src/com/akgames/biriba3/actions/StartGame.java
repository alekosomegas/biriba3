package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;

import java.util.List;

public class StartGame implements PlayerAction {
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
	public void execute(List<?> params) {
	
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
