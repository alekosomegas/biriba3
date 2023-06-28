package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Deck;
import com.akgames.biriba3.model.Player;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.GameOptions.*;

public class DealEvent implements GameEvent {
	private final Board board;
	private final Deck deck;
	private final List<Player> players;
	
	public DealEvent() {
		GameController gameLogic = Match.getController();
		this.board = gameLogic.getBoard();
		this.deck = gameLogic.getBoard().getDeck();
		this.players = gameLogic.getPlayers();
	}
	
	@Override
	public void execute() {
		// Deal cards to all Players
		for(Player player : players) {
			for(int i = 0; i < NUM_CARDS_PER_PLAYER; i++) {
				Card card = deck.getTopCard();
				if(players.indexOf(player) == 0) card.setShowFace(true);
				player.addToHand(card);
			}
		}
		
		List<Card> cards1 = new ArrayList<>(NUM_CARDS_BIRIBAKI_1), cards2 = new ArrayList<>(NUM_CARDS_BIRIBAKI_2);
		
		for(int i = 0; i < NUM_CARDS_BIRIBAKI_1; i++) {
			cards1.add(deck.getTopCard());
		}
		for(int i = 0; i < NUM_CARDS_BIRIBAKI_2; i++) {
			cards2.add(deck.getTopCard());
		}
		
		board.createBiribakia(cards1, cards2);
		Card koziCard = deck.getTopCard();
		int kozi = koziCard.getSuit();
		GAME_CONTROLLER.setKozi(kozi);
		board.addToDiscardPile(koziCard.setShowFace(true));
	}
	
	@Override
	public boolean undo() {
		// no undo
		return false;
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
