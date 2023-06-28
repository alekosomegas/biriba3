package com.akgames.biriba3.model;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {
	private final int teamNumber;
	private Team team;
	private final PropertyChangeSupport support;
	protected List<Card> handDiscarded;
	protected List<List<Card>> groups;
	protected List<Card> jokers;
	protected List<Card> twos;
	protected GameController gameController;
	private List<Card> hand;
	private String name;
	private boolean hasTakenBiribaki;
	
	public Player(String name, int teamNumber) {
		this.hand = new ArrayList<>();
		this.handDiscarded = new ArrayList<>();
		this.groups = new ArrayList<>();
		this.jokers = new ArrayList<>();
		this.twos = new ArrayList<>();
		this.name = name;
		this.teamNumber = teamNumber;
		this.hasTakenBiribaki = false;
		this.support = new PropertyChangeSupport(this);
	}
	
	public void addController(GameController controller) {
		this.gameController = controller;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public int getPenaltyPoints() {
		if (gameController == null || !gameController.isGameOver()) return 0;
		int penaltyPoints = 0;
		for(Card card : hand) {
			penaltyPoints += card.getPoints();
		}
		return penaltyPoints;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public void removeCard(Card card) {
		hand.remove(card);
		checkForEmptyHand();
	}
	
	private void checkForEmptyHand() {
		if(hand.size() == 0) {
			support.firePropertyChange("Empty Hand", null, 0);
		}
	}
	
	public void removeCard(List<Card> cards) {
		hand.removeAll(cards);
		checkForEmptyHand();
	}
	
	public void addToHand(Card card) {
		if(gameController.currentPlayerIndex == 0) card.setClickable(true);
		hand.add(card);
		Collections.sort(hand);
	}
	
	public void addDiscardedCards(List<Card> cards) {
		groups = groupHandBySuit();
		handDiscarded = cards;
		addToHand(cards);
	}
	
	//TODO: use this instead of Collections.sort and then display separately for main player
	List<List<Card>> groupHandBySuit() {
		List<Card> diamonds = new ArrayList<>();
		List<Card> clubs = new ArrayList<>();
		List<Card> hearts = new ArrayList<>();
		List<Card> spades = new ArrayList<>();
		
		groups = new ArrayList<>(Arrays.asList(diamonds, clubs, hearts, spades));
		
		for(Card card : getHand()) {
			if(card.isJoker) {
				jokers.add(card);
				continue;
			}
			if(card.getRank() == 2) {
				twos.add(card);
			}
			groups.get(card.getSuit()).add(card);
		}
		return groups;
	}
	
	public void addToHand(List<Card> cards) {
		hand.addAll(cards);
		Collections.sort(hand);
	}
	
	public List<Card> getHand() {
		return hand;
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
	
	public void act() {
	
	}
}
