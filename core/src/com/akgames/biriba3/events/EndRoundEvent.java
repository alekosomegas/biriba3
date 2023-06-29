package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.model.Card;

public class EndRoundEvent implements GameEvent {
	private GameController controller;
	
	public EndRoundEvent() {
		controller = Match.getController();
	}
	
	@Override
	public void execute() {
		// Clear selected cards
		for(Card card : controller.getCurrentPlayer().getHand()) {
			card.setSelected(false);
		}
		controller.getSelectedCards().clear();
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
