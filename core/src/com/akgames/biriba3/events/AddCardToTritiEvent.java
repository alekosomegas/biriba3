package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;

import java.util.ArrayList;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

// TODO: CHECK FOR DUPLICATES
public class AddCardToTritiEvent implements GameEvent {
	private final Card card;
	private final Triti triti;
	private final ArrayList<Card> tritiCards;
	private final boolean hasPickedFromDiscards;
	private GameController controller;
	
	public AddCardToTritiEvent(Card card, Triti triti) {
		controller = Match.getController();
		this.card = card;
		this.triti = triti;
		this.tritiCards = triti.getCards();
		this.hasPickedFromDiscards = Turn.CurrentPhase() == TRITI;
	}
	
	@Override
	public void execute() {
		boolean success = triti.addCard(card);
		if(success) {
			controller.getCurrentPlayer().removeCard(card);
			// no need to do anything if in discard phase
			if(hasPickedFromDiscards) {
				// only if one of new cards involved
				Turn.setCurrentPhaseTo(DISCARD);
			}
		}
	}
	
	@Override
	public boolean undo() {
		controller.getCurrentPlayer().addToHand(card);
		if(controller.getPlayers().indexOf(controller.getCurrentPlayer()) != 0) {
			card.setShowFace(false);
		} else {
			card.setClickable(true);
		}
		triti.setCards(tritiCards);
		if(hasPickedFromDiscards) Turn.setCurrentPhaseTo(TRITI);
		return true;
	}
	
	@Override
	public boolean allowed() {
		// Allow when triti(picked from discards) and optional case Discard
		return Turn.CurrentPhase() == TRITI || Turn.CurrentPhase() == DISCARD;
	}
}
