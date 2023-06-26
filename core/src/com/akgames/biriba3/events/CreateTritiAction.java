package com.akgames.biriba3.events;

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
public class CreateTritiAction implements GameEvent {
	
	private final List<Card> selectedCards;
	private final Player currentPLayer;
	private final Board board;
	
	// Used by GameUI
	public CreateTritiAction() {
		this.selectedCards = GAME_CONTROLLER.getSelectedCards();
		this.currentPLayer = GAME_CONTROLLER.getCurrentPlayer();
		this.board = GAME_CONTROLLER.getBoard();
	}
	
	// Used by player Ai
	public CreateTritiAction(List<Card> cards) {
		this.selectedCards = cards;
		this.currentPLayer = GAME_CONTROLLER.getCurrentPlayer();
		this.board = GAME_CONTROLLER.getBoard();
	}
	
	@Override
	public void execute() {
		Triti triti = Triti.createTriti(new ArrayList<>(selectedCards));
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
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		// Allow when triti(picked from discards) and optional case Discard
		return Turn.CurrentPhase() == TRITI || Turn.CurrentPhase() == DISCARD;
	}
}
