package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;

public class ThrowCardToDiscards implements GameEvent {
	private final Card card;
	
	// constructor for drag and drop
	public ThrowCardToDiscards(Card card) {
		this.card = card;
		card.setShowFace(true);
		card.setSelected(false);
		card.setClickable(false);
		// TODO: refactor selected cards
		GAME_CONTROLLER.getSelectedCards().remove(card);
	}
	
	@Override
	public void execute() {
		GAME_CONTROLLER.getBoard().addToDiscardPile(card);
		GAME_CONTROLLER.getCurrentPlayer().removeCard(card);
		GAME_CONTROLLER.setCurrentPlayerHasThrownCard(true);
		Turn.nextPhase();
	}
	
	@Override
	public void undo() {
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == DISCARD;
	}
}
