package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;

public class PickFromDeckEvent implements GameEvent {
	
	public PickFromDeckEvent() {
	}
	
	@Override
	public void execute() {
		Card card = GAME_CONTROLLER.getBoard().getDeck().getTopCard();
		if(GAME_CONTROLLER.currentPlayerIndex == 0) {
			card.setShowFace(true);
			card.setSelected(true);
			GAME_CONTROLLER.getSelectedCards().add(card);
		}
		GAME_CONTROLLER.getCurrentPlayer().addToHand(card);
		GAME_CONTROLLER.setCurrentPlayerHasThrownCard(false);
		Turn.nextPhase();
	}
	
	@Override
	public boolean undo() {
		// not allowed
		return false;
	}
	
	@Override
	public boolean allowed() {
		// Only one action can be performed
		return Turn.CurrentPhase() == PICK;
	}
}
