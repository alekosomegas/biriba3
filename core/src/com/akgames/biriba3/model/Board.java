package com.akgames.biriba3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for creating the Deck
 */
public class Board {
    private List<Card> discardPile;
    private List<Triti> trites;
    private Deck deck;

    public Board() {
        deck = new Deck();
        discardPile = new ArrayList<>();
        trites = new ArrayList<>();
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

    public void addTriti(Triti triti) {
        trites.add(triti);
    }

    public List<Triti> getTrites() {
        return trites;
    }
}
