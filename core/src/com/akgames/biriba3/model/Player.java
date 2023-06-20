package com.akgames.biriba3.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // playerindex?
    private List<Card> hand;
    private int score;
    private int penaltyPoints;
    private String name;
    private boolean isAI;
    private int teamNumber;

    public Player(String name, boolean isAI, int teamNumber) {
        hand = new ArrayList<>();
        this.name = name;
        this.isAI = isAI;
        this.teamNumber = teamNumber;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }
    public void addToHand(List<Card> cards) {
        hand.addAll(cards);
    }
    public String getName() {
        return name;
    }
    public int getCardCount() {
        return hand.size();
    }
}
