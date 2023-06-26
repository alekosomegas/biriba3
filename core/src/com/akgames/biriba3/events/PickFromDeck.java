package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;

public class PickFromDeck implements GameEvent {
	
	public PickFromDeck() {
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
	public void undo() {
		// not allowed
	}
	
	@Override
	public boolean allowed() {
		// Only one action can be performed
		return Turn.CurrentPhase() == PICK;
	}
}
