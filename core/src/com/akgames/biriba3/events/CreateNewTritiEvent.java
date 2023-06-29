package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.model.Triti;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

// TODO: Check A-2-Q
public class CreateNewTritiEvent implements GameEvent {
	
	private List<Card> selectedCards;
	private final Player currentPLayer;
	private final Board board;
	private final boolean hasPickedFromDiscards;
	private Triti triti;
	private GameController controller;
	// Used by GameUI
	public CreateNewTritiEvent() {
		controller = Match.getController();
		System.out.println("ΝΕ΅ΤΡΙΤΙ ΕVENT 111111111111\n . ");
		
		this.selectedCards = controller.getSelectedCards();
		this.currentPLayer = controller.getCurrentPlayer();
		this.board = controller.getBoard();
		this.hasPickedFromDiscards = Turn.CurrentPhase() == TRITI;
	}
	
	// Used by player Ai
	public CreateNewTritiEvent(List<Card> cards) {
		this();
		this.selectedCards = cards;
	}
	
	@Override
	public void execute() {
			System.out.println("ΝΕ΅ΤΡΙΤΙ ΕVENT \n . ");
		for(Card card : selectedCards) {
			System.out.println(card + " . ");
			
		}
		triti = Triti.createTriti(new ArrayList<>(selectedCards));
		if(triti != null) {
			for(Card card : selectedCards) {
				card.setSelected(false);
				card.setClickable(false);
				card.setShowFace(true);
			}
			currentPLayer.removeCard(selectedCards);
			selectedCards.clear();
			board.addTriti(triti);
			// no need to do anything if in discard phase
			if(Turn.CurrentPhase() == TRITI) {
				// only if one of new cards involved
				Turn.setCurrentPhaseTo(DISCARD);
			}
		}
	}
	
	@Override
	public boolean undo() {
		// button clicked but no triti created, do nothing
		if(triti == null) return true;
		for(Card card : triti.getCards()) {
			card.setSelected(true);
			card.setClickable(true);
			// reset values
			if(card.isJoker) card.setValueAndRankAndSuit(-1, -1);
			if(card.getRank() == 14) card.setValueAndRankAndSuit(1, card.getSuit());
			if(card.getInitialValue() % 13 == 1) card.setValueAndRankAndSuit(2, card.getSuit());
		}
		currentPLayer.addToHand(triti.getCards());
		selectedCards.clear();
		selectedCards.addAll(triti.getCards());
		board.removerTriti(triti);
		if(hasPickedFromDiscards) Turn.setCurrentPhaseTo(TRITI);
		
		return true;
	}
	
	@Override
	public boolean allowed() {
		// Allow when triti(picked from discards) and optional case Discard
		return Turn.CurrentPhase() == TRITI || Turn.CurrentPhase() == DISCARD;
	}
}
