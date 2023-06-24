package com.akgames.biriba3.model;

import com.akgames.biriba3.actions.CreateTritiAction;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.badlogic.gdx.Gdx;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {
    // playerindex?
    private List<Card> hand;
    protected List<Card> handDiscarded;
    protected List<List<Card>> groups;
    protected List<Card> jokers;
    protected List<Card> twos;
    private int score;
    private int penaltyPoints;
    private String name;
    private int teamNumber;
    private PropertyChangeSupport support;
    private boolean hasTakenBiribaki;

    public Player(String name, int teamNumber) {
        this.hand = new ArrayList<>();
        this.handDiscarded = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.jokers = new ArrayList<>();;
        this.twos = new ArrayList<>();;
        this.name = name;
        this.teamNumber = teamNumber;
        this.hasTakenBiribaki = false;
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // TODO: should this return a copy and be protected, the hand wont be able change?
    public List<Card> getHand() {
        return hand;
    }

    private void checkForEmptyHand() {
        if (hand.size() == 0) {
            support.firePropertyChange("Empty Hand", null, 0);
        }
    }
    public void removeCard(Card card) {
        hand.remove(card);
        checkForEmptyHand();
    }
    public void removeCard(List<Card> cards) {
        hand.removeAll(cards);
        checkForEmptyHand();
    }

    public void addToHand(Card card) {
        // TODO: check if works
        if (GameLogic.getInstance().currentPlayerIndex == 0) card.setClickable(true);
        hand.add(card);
        Collections.sort(hand);
    }
    public void addToHand(List<Card> cards) {
        hand.addAll(cards);
        Collections.sort(hand);
    }

    public void addDiscardedCards(List<Card> cards) {
        groups = groupHandBySuit();
        handDiscarded = cards;
        addToHand(cards);
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

    public void setHasTakenBiribaki() {
        this.hasTakenBiribaki = true;
    }

    public boolean hasTakenBiribaki() {
        return hasTakenBiribaki;
    }


    //TODO: use that instead of Collections.sort and then display separately for main player
    List<List<Card>> groupHandBySuit() {
        List<Card> diamonds    = new ArrayList<>();
        List<Card> clubs       = new ArrayList<>();
        List<Card> hearts      = new ArrayList<>();
        List<Card> spades      = new ArrayList<>();


        groups = new ArrayList<>(Arrays.asList(diamonds, clubs, hearts, spades));

        for (Card card : getHand()) {
            if (card.isJoker) {
                jokers.add(card);
                continue;
            }
            if (card.getRank() == 2) {
                twos.add(card);
            }
            groups.get(card.getSuit()).add(card);
        }
        return groups;
    }

    public void act() {

    }
}
