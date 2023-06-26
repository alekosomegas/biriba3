package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

public class PickDiscardsEvent implements GameEvent {
	private List<Card> discards;
	
	public PickDiscardsEvent() {
		this.discards = new ArrayList<>(GAME_CONTROLLER.getBoard().getDiscardPile());
	}
	
	@Override
	public void execute() {
		for(Card card : discards) {
			card.setClickable(true);
		}
		GAME_CONTROLLER.getCurrentPlayer().addDiscardedCards(discards);
		GAME_CONTROLLER.getBoard().getDiscardPile().clear();
		GAME_CONTROLLER.setCurrentPlayerHasThrownCard(false);
		
		Turn.setCurrentPhaseTo(TRITI);
	}
	
	@Override
	public boolean undo() {
		for (Card card : discards) {
			card.setShowFace(true);
			card.setClickable(false);
		}
		GAME_CONTROLLER.getCurrentPlayer().removeCard(discards);
		GAME_CONTROLLER.getBoard().addAllToDiscardPile(discards);
		GAME_CONTROLLER.setCurrentPlayerHasThrownCard(true);
		
		Turn.setCurrentPhaseTo(PICK);
		return true;
	}
	
	@Override
	public boolean allowed() {
		// Only one action can be performed
		return Turn.CurrentPhase() == PICK;
	}
}
