package com.akgames.biriba3.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Responsible for creating all the new Cards
 */
public class Deck {
	private final List<Card> cards;
	private final PropertyChangeSupport support;
	private int size;
	
	public Deck() {
		this.support = new PropertyChangeSupport(this);
		this.cards = new ArrayList<>();
		// add two decks
		for(int i = 0; i < 52 * 2; i++) {
			cards.add(new Card(i % 52));
		}
		// add jokers
		for(int i = 0; i < 4; i++) {
			cards.add(new Card(-1));
		}
		this.size = cards.size();
		shuffle();
	}
	
	private void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card getTopCard() {
		setSize(cards.size() - 1);
		return cards.remove(cards.size() - 1);
	}
	
	private void setSize(int newValue) {
		int oldValue = size;
		size = newValue;
		support.firePropertyChange("Deck Size Changed", oldValue, newValue);
		
	}
	
	public void addCard(Card card) {
		cards.add(card);
		setSize(cards.size() + 1);
	}
	
	public String getNumOfRemainingCards() {
		return String.valueOf(size);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	
}


