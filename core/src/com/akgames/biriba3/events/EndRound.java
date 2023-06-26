package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.model.Card;

import java.util.List;

public class EndRound implements GameEvent {
	@Override
	public void execute() {
		// Clear selected cards
		for(Card card : GAME_CONTROLLER.getCurrentPlayer().getHand()) {
			card.setSelected(false);
		}
		GAME_CONTROLLER.getSelectedCards().clear();
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
