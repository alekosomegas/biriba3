package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;

public class PickFromDeckEvent implements GameEvent {
	private GameController controller;
	public PickFromDeckEvent() {
		controller = Match.getController();
	}
	
	@Override
	public void execute() {
		Card card = controller.getBoard().getDeck().getTopCard();
		if(controller.currentPlayerIndex == 0) {
			card.setShowFace(true);
			card.setSelected(true);
			controller.getSelectedCards().add(card);
		}
		controller.getCurrentPlayer().addToHand(card);
		controller.setCurrentPlayerHasThrownCard(false);
		controller.checkForDiscards = false;
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
