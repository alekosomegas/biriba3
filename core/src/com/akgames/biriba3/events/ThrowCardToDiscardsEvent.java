package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;

public class ThrowCardToDiscardsEvent implements GameEvent {
	private final Card card;
	private Turn.TurnPhases enterPhase;
	private GameController controller;
	
	// constructor for drag and drop
	public ThrowCardToDiscardsEvent(Card card) {
		controller = Match.getController();
		this.card = card;
		this.enterPhase = Turn.CurrentPhase();
		card.setShowFace(true);
		card.setSelected(false);
		card.setClickable(false);
		controller.getSelectedCards().remove(card);
	}
	
	@Override
	public void execute() {
		controller.getBoard().addToDiscardPile(card);
		controller.getCurrentPlayer().removeCard(card);
		controller.setCurrentPlayerHasThrownCard(true);
		Turn.nextPhase();
	}
	
	@Override
	public boolean undo() {
		controller.getBoard().removeFromDiscardedPile(card);
		controller.getCurrentPlayer().addToHand(card);
		controller.setCurrentPlayerHasThrownCard(false);
		Turn.setCurrentPhaseTo(enterPhase);
		return true;
	}
	
	@Override
	public boolean allowed() {
		// prevent execution if player has picked from discards and has not used card
		if (controller.checkForDiscards
				&& !controller.hasUsedDiscardedCard()) {
			return false;
		}
		return Turn.CurrentPhase() == DISCARD;
	}
}
