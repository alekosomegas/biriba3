package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import static com.akgames.biriba3.controller.Turn.TurnPhases.END;

public class EndTurn implements GameEvent {
	
	public EndTurn() {
	}
	
	@Override
	public void execute() {
		Gdx.app.log(this.toString(), "Executing End Turn. " + "Turn phase : " + Turn.CurrentPhase() + "\nCurrent player: " + GameController.getInstance().getCurrentPlayer().getName());
		// start next turn before executing
		Turn.nextPhase();
		// Next player turn starts
		Gdx.app.log(this.toString(), "\nSTARTS NEW TURN\n");
		GameController.getInstance().nextPlayer();
		GameController.getInstance().getCurrentPlayer().act();
		// Every player had a turn
		if(GameController.getInstance().currentPlayerIndex == 0) {
			GameController.getInstance().handleAction(new EndRound());
		}
		Gdx.app.log(this.toString(), "\n---------------------- END TURN\n");
	}
	
	@Override
	public void undo() {
		// No undo
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == END;
	}
}
