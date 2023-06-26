package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;

public class ThrowCardToDiscardsEvent implements GameEvent {
	private final Card card;
	private Turn.TurnPhases enterPhase;
	
	// constructor for drag and drop
	public ThrowCardToDiscardsEvent(Card card) {
		this.card = card;
		this.enterPhase = Turn.CurrentPhase();
		card.setShowFace(true);
		card.setSelected(false);
		card.setClickable(false);
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
	public boolean undo() {
		GAME_CONTROLLER.getBoard().removeFromDiscardedPile(card);
		GAME_CONTROLLER.getCurrentPlayer().addToHand(card);
		GAME_CONTROLLER.setCurrentPlayerHasThrownCard(false);
		Turn.setCurrentPhaseTo(enterPhase);
		return true;
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == DISCARD;
	}
}
