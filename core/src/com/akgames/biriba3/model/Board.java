package com.akgames.biriba3.model;


import com.akgames.biriba3.controller.GameOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for creating and modeling the Deck, the discards pile, trites and biribakia.
 */
public class Board {
	private final List<Card> discardPile;
	// holds all the trites on the board
	private final ArrayList<ArrayList<Triti>> trites;
	private final Deck deck;
	private List<Card> biribaki1;
	private List<Card> biribaki2;
	private int numBiribakia;
	
	public Board(int numTeams) {
		this.deck = new Deck();
		this.discardPile = new ArrayList<>();
		this.trites = new ArrayList<>(numTeams);
		for(int i = 0; i < numTeams; i++) {
			trites.add(new ArrayList<Triti>());
		}
		this.biribaki1 = new ArrayList<>(GameOptions.NUM_CARDS_BIRIBAKI_1);
		this.biribaki2 = new ArrayList<>(GameOptions.NUM_CARDS_BIRIBAKI_2);
		this.numBiribakia = 2;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public List<Card> getDiscardPile() {
		return discardPile;
	}
	
	public void addToDiscardPile(Card discardedCard) {
		discardPile.add(discardedCard);
	}
	public void addAllToDiscardPile(List<Card> cards) {discardPile.addAll(cards);}
	public void removeFromDiscardedPile(Card card) {discardPile.remove(card);}
	public void addTriti(Triti triti) {
		trites.get(triti.getTeam()).add(triti);
	}
	public void removerTriti(Triti triti) {
		trites.get(triti.getTeam()).remove(triti);
	}
	
	public List<Triti> getTrites(int team) {
		return trites.get(team);
	}
	
	
	
	public void createBiribakia(List<Card> cards1, List<Card> cards2) {
		biribaki1 = cards1;
		biribaki2 = cards2;
	}
	
	public List<Card> getBiribaki() {
		numBiribakia--;
		return numBiribakia == 1 ? biribaki2 : biribaki1;
	}
	
	public int getNumBiribakia() {
		return numBiribakia;
	}
}
