package com.akgames.biriba3.events;

import com.akgames.biriba3.model.Card;

public class EndRoundEvent implements GameEvent {
	@Override
	public void execute() {
		// Clear selected cards
		for(Card card : GAME_CONTROLLER.getCurrentPlayer().getHand()) {
			card.setSelected(false);
		}
		GAME_CONTROLLER.getSelectedCards().clear();
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
