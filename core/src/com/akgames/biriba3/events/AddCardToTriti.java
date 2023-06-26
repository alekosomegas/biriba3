package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

// TODO: CHECK FOR DUPLICATES
public class AddCardToTriti implements GameEvent {
	private final Card card;
	private final Triti triti;
	
	public AddCardToTriti(Card card, Triti triti) {
		this.card = card;
		this.triti = triti;
	}
	
	@Override
	public void execute() {
		boolean success = triti.addCard(card);
		
		if(success) {
			GAME_CONTROLLER.getCurrentPlayer().removeCard(card);
			card.setShowFace(true);
			// no need to do anything if in discard phase
			if(Turn.CurrentPhase() == TRITI) {
				// only if one of new cards involved
				Turn.setCurrentPhaseTo(DISCARD);
			}
		}
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		// Allow when triti(picked from discards) and optional case Discard
		return Turn.CurrentPhase() == TRITI || Turn.CurrentPhase() == DISCARD;
	}
}
