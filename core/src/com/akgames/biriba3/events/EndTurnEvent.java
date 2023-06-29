package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import static com.akgames.biriba3.controller.Turn.TurnPhases.END;

public class EndTurnEvent implements GameEvent {
	private GameController controller;
	
	public EndTurnEvent() {
		controller = Match.getController();
		
	}
	
	@Override
	public void execute() {
		Gdx.app.log(this.toString(), "Executing End Turn. " + "Turn phase : " + Turn.CurrentPhase() + "\nCurrent player: " + controller.getCurrentPlayer().getName());
		if(controller.isGameOver()) {return;}
		// start next turn before executing
		Turn.nextPhase();
		// Next player turn starts
		Gdx.app.log(this.toString(), "\nSTARTS NEW TURN\n");
		controller.nextPlayer();
		controller.getCurrentPlayer().act();
		// Every player had a turn
		if(controller.currentPlayerIndex == 0) {
			controller.handleAction(new EndRoundEvent());
		}
		controller.clearPlayerActionsQueue();
		Gdx.app.log(this.toString(), "\n---------------------- END TURN\n");
	}
	
	@Override
	public boolean undo() {
		// No undo
		return false;
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == END;
	}
	
}
