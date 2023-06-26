package com.akgames.biriba3.model;


import com.akgames.biriba3.events.*;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;
import static com.akgames.biriba3.model.PlayerAI.Decision.*;

// TODO: Use a decisions Tree
public class PlayerAI extends Player {
	// list of cards from discards and hand that can create a triti
	private List<Card> validCombination;
	private Triti validTriti;
	private Card validCard;
	
	;
	
	public PlayerAI(String name, int teamNumber) {
		super(name, teamNumber);
		this.validCombination = new ArrayList<>();
	}
	
	@Override
	public void act() {
		Random rand = new Random();
		// Look at the discards and decide on action
		Decision decision = analyzeDiscards();
		
		if(decision == NEW_TRITI_FROM_DISCARDS) {
			gameController.handleAction(new PickDiscards());
			gameController.handleAction(new CreateTritiAction(validCombination));
		} else if(decision == ADD_TO_TRITI_FROM_DISCARDS) {
			gameController.handleAction(new PickDiscards());
			gameController.handleAction(new AddCardToTriti(validCard, validTriti));
		} else if(decision == PICK_FROM_DECK) {
			gameController.handleAction(new PickFromDeck());
		}
		Turn.setCurrentPhaseTo(DISCARD);
		// TODO: joker only as last resort
		int randNumCard = rand.nextInt(getCardCount());
		gameController.handleAction(new ThrowCardToDiscards(getHand().get(randNumCard)));
		gameController.handleAction(new EndTurn());
	}
	
	/**
	 * Analyses the discards to see if:
	 * <ol>
	 *     <li>Does one card from discards be added to existing triti</li>
	 *     <li>Do discards hold a triti?</li>
	 *     <li>Do 2 cards from the discards + 1 from hand form a triti</li>
	 *     <li>Does 1 card from discards + 2 from hand form a triti</li>
	 *     <li>A card from discards plus 1 from hand can be added to existing triti</li>
	 * </ol>
	 * And return a decision
	 */
	private Decision analyzeDiscards() {
		// TODO: check if n cards from discards + n cards from hand can be added to an existing triti
		List<Card> discards = new ArrayList<>(gameController.getBoard().getDiscardPile());
		this.groups = groupHandBySuit();
		// Does one card from discards be added to existing triti?
		for(Card card : discards) {
			List<Triti> trites = gameController.getBoard().getTrites(getTeamNumber());
			for(Triti triti : trites) {
				// TODO: don't stop at the first. Store all valid cards and find best
				if(triti.validateAddCard(card)) {
					validTriti = triti;
					validCard = card;
					return ADD_TO_TRITI_FROM_DISCARDS;
				}
			}
		}
		// Do discards hold a triti?
		if(discards.size() > 2) {
			if(findValidTritiIn(discards)) {
				return NEW_TRITI_FROM_DISCARDS;
			}
			;
		}
		// Do 2 cards from the discards + 1 from hand form a triti?
		if(discards.size() > 1) {
			if(findValidTriti(discards)) {
				return NEW_TRITI_FROM_DISCARDS;
			}
		}
		
		// Does 1 card from discards + 2 from hand form a triti?
		for(Card card : discards) {
			if(findValidTriti(card)) {
				return NEW_TRITI_FROM_DISCARDS;
			}
		}
		// tried everything
		return PICK_FROM_DECK;
	}
	
	private boolean findValidTritiIn(List<Card> discards) {
		List<List<Card>> combinationsFromDiscards = generateCombinations3s(discards);
		for(List<Card> combination : combinationsFromDiscards) {
			if(Triti.canCreateValidTriti(combination)) {
				validCombination = combination;
				return true;
			}
		}
		return false;
	}
	
	private boolean findValidTriti(List<Card> discards) {
		List<List<Card>> combinationsFromDiscards = generateCombinationsPairs(discards);
		// TODO: remove jokers add them later( check normals, check jokers, check 2 not optimal but ok)
		for(Card cardHand : getHand()) {
			for(List<Card> combination : combinationsFromDiscards) {
				combination.add(cardHand);
				if(Triti.canCreateValidTriti(combination)) {
					validCombination = combination;
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean findValidTriti(Card card) {
		List<List<Card>> groups = this.groups;
		List<List<Card>> combinations;
		int i = 0;
		// check suits groups
		for(List<Card> group : groups) {
			if(i > 3) break;
			combinations = generateCombinationsPairs(group);
			for(List<Card> combination : combinations) {
				combination.add(card);
				if(Triti.canCreateValidTriti(combination)) {
					validCombination = combination;
					return true;
				}
			}
			i++;
		}
		//
		// try jokers, if no jokers try 2s
		if(jokers.size() > 0) {
			Card joker = jokers.get(0);
			ArrayList<Card> combination = new ArrayList<>(Arrays.asList(joker, card));
			for(Card handCard : getHand()) {
				Gdx.app.log("Fails", joker.toString());
				combination.add(handCard);
				if(Triti.canCreateValidTriti(combination)) {
					validCombination = combination;
					return true;
				}
			}
		}
		if(twos.size() > 0) {
			Card joker = twos.get(0);
			ArrayList<Card> combination = new ArrayList<>(Arrays.asList(joker, card));
			for(Card handCard : getHand()) {
				Gdx.app.log("Fails 2", joker.toString());
				combination.add(handCard);
				if(Triti.canCreateValidTriti(combination)) {
					validCombination = combination;
					return true;
				}
			}
		}
		
		return false;
	}
	
	private List<List<Card>> generateCombinations3s(List<Card> group) {
		List<List<Card>> combinations = new ArrayList<>();
		for(int i = 0; i < group.size() - 2; i++) {
			for(int j = i + 1; j < group.size() - 1; j++) {
				for(int k = 0; k < group.size(); k++) {
					List<Card> combination = new ArrayList<>();
					combination.add(group.get(i));
					combination.add(group.get(j));
					combination.add(group.get(k));
					combinations.add(combination);
				}
			}
		}
		return combinations;
	}
	
	private List<List<Card>> generateCombinationsPairs(List<Card> group) {
		List<List<Card>> combinations = new ArrayList<>();
		for(int i = 0; i < group.size() - 1; i++) {
			for(int j = i + 1; j < group.size(); j++) {
				List<Card> combination = new ArrayList<>();
				combination.add(group.get(i));
				combination.add(group.get(j));
				combinations.add(combination);
			}
		}
		return combinations;
	}
	
	@Override
	public void addToHand(List<Card> cards) {
		for(Card card : cards) {
			addToHand(card);
		}
	}
	
	@Override
	public void addToHand(Card card) {
		card.setShowFace(false);
		getHand().add(card);
	}
	
	enum Decision {PICK_FROM_DECK, NEW_TRITI_FROM_DISCARDS, ADD_TO_TRITI_FROM_DISCARDS}
}
