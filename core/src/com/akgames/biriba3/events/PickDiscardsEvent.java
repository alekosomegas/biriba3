package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

public class PickDiscardsEvent implements GameEvent {
	private final List<Card> discards;
	private GameController controller;
	
	public PickDiscardsEvent() {
		controller = Match.getController();
		this.discards = new ArrayList<>(controller.getBoard().getDiscardPile());
		controller.discardsTemp = discards;
	}
	
	@Override
	public void execute() {
		for(Card card : discards) {
			card.setClickable(true);
		}
		controller.getCurrentPlayer().addDiscardedCards(discards);
		controller.getBoard().getDiscardPile().clear();
		controller.setCurrentPlayerHasThrownCard(false);
		controller.checkForDiscards = true;
		
		Turn.setCurrentPhaseTo(TRITI);
	}
	
	@Override
	public boolean undo() {
		for (Card card : discards) {
			card.setShowFace(true);
			card.setClickable(false);
		}
		controller.getCurrentPlayer().removeCard(discards);
		controller.getBoard().addAllToDiscardPile(discards);
		controller.setCurrentPlayerHasThrownCard(true);
		controller.checkForDiscards = false;
		
		Turn.setCurrentPhaseTo(PICK);
		return true;
	}
	
	@Override
	public boolean allowed() {
		// Only one action can be performed
		return Turn.CurrentPhase() == PICK;
	}
}
