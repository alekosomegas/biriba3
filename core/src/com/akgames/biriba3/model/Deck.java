package com.akgames.biriba3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Responsible for creating all the new Cards
 */
public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        // add two decks
        for (int i=0; i<52*2; i++) {
            cards.add(new Card(i%52));
        }
        // add jokers
        for (int i=0; i < 4; i++) {
            cards.add(new Card(-1));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    // TODO: what happens when no cards left?
    public Card getTopCard() {
        return cards.remove(0);}

    public List<Card> getCards() {
        return cards;
    }
}


