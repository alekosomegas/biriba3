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
    private List<Card> cards;
    private int size;
    private PropertyChangeSupport support;

    public Deck() {
        support = new PropertyChangeSupport(this);
        cards = new ArrayList<>();
        // add two decks
        for (int i=0; i<52*2; i++) {
            cards.add(new Card(i%52));
        }
        //TODO: image for jokers in card getImgURL
//        // add jokers
//        for (int i=0; i < 4; i++) {
//            cards.add(new Card(-1));
//        }
        this.size = cards.size();
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    // TODO: what happens when no cards left?
    public Card getTopCard() {
        setSize(cards.size()-1);
        return cards.remove(cards.size()-1);
    }

    public void addCard(Card card) {
        cards.add(card);
        setSize(cards.size() +1);
    }

    private void setSize(int newValue) {
        int oldValue = size;
        size = newValue;
        support.firePropertyChange("Deck Size Changed", oldValue, newValue);

    }

    //TODO: remove this
    public List<Card> getCards() {
        return cards;
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


