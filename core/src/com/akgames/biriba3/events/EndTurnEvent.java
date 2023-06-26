package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import static com.akgames.biriba3.controller.Turn.TurnPhases.END;

public class EndTurnEvent implements GameEvent {
	
	public EndTurnEvent() {
	}
	
	@Override
	public void execute() {
		Gdx.app.log(this.toString(), "Executing End Turn. " + "Turn phase : " + Turn.CurrentPhase() + "\nCurrent player: " + GAME_CONTROLLER.getCurrentPlayer().getName());
		// start next turn before executing
		Turn.nextPhase();
		// Next player turn starts
		Gdx.app.log(this.toString(), "\nSTARTS NEW TURN\n");
		GAME_CONTROLLER.nextPlayer();
		GAME_CONTROLLER.getCurrentPlayer().act();
		// Every player had a turn
		if(GAME_CONTROLLER.currentPlayerIndex == 0) {
			GAME_CONTROLLER.handleAction(new EndRoundEvent());
		}
		GAME_CONTROLLER.clearPlayerActionsQueue();
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
