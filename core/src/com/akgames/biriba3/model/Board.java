package com.akgames.biriba3.model;

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
    }

    public Deck getDeck() {
        return deck;
    }
}
