package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

public class PickDiscards implements GameEvent {
	@Override
	public void execute() {
		List<Card> discards = new ArrayList<>(GAME_CONTROLLER.getBoard().getDiscardPile());
		for(Card card : discards) {
			card.setClickable(true);
		}
		
		GAME_CONTROLLER.getCurrentPlayer().addDiscardedCards(discards);
		GAME_CONTROLLER.getBoard().getDiscardPile().clear();
		GAME_CONTROLLER.setCurrentPlayerHasThrownCard(false);
		
		Turn.setCurrentPhaseTo(TRITI);
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		// Only one action can be performed
		return Turn.CurrentPhase() == PICK;
	}
}
