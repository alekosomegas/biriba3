package com.akgames.biriba3.model;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    // playerindex?
    private List<Card> hand;
    private int score;
    private int penaltyPoints;
    private String name;
    private int teamNumber;

    public Player(String name, int teamNumber) {
        hand = new ArrayList<>();
        this.name = name;
        this.teamNumber = teamNumber;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card) {
        if (GameLogic.getInstance().currentPlayerIndex == 0) card.setClickable(true);
        hand.add(card);
        Collections.sort(hand);
    }
    public void addToHand(List<Card> cards) {
        hand.addAll(cards);
        Collections.sort(hand);
    }
    public String getName() {
        return name;
    }
    public int getCardCount() {
        return hand.size();
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void act() {

    }
}
