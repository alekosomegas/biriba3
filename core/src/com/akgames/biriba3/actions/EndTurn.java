package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.END;

public class EndTurn implements PlayerAction {
	
	
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
		
		
		if(GameController.getInstance().currentPlayerIndex == 0) {
			GameController.getInstance().handleAction(new EndRound());
		}
		
		Gdx.app.log(this.toString(), "\n---------------------- END TURN\n");
		
	}
	
	@Override
	public void execute(List<?> params) {
	
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == END;
	}
}
