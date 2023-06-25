package com.akgames.biriba3.model;

import com.akgames.biriba3.actions.Utils.CheckTriti;
import com.akgames.biriba3.controller.GameController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model of a triti. Finds if a set of cards can form a valid triti and if a card can be added to an existing one.
 * Handles all the internal shifts of the wild cards and the state of each card.
 * <br><br>
 * Rules for a valid triti:
 * <ol>
 *      <li>The list must contain at least three cards.</li>
 *      <li>All cards  in the list (except Jokers) must be of the same suit.</li>
 *      <li>The ranks of the cards must be consecutive.</li>
 *      <li>Only one Joker(or a 2) is allowed. Joker takes new value</li>
 *       <li>A 2 becomes a joker :</li>
 *       <ul>
 *           <li>if a 2 already exists</li>
 *           <li>if no other 2 exists</li>
 *       </ul>
 *      <li>An Ace is a 13 if K and Q exist, 1 if not.<</li>
 * </ol>
 * <br>
 * Cases:
 * <ul>
 *      <li> A. No Jokers</li>
 *          <ul>
 *              <li>Case A-1: Has zero 2</li>
 *              <li>Case A-2: Has one 2</li>
 *              <li>Case A-3: Has two 2</li>
 *          </ul>
 *      <li>B. 1 Joker</li>
 *      <ul>
 *            <li>Case B-1: Has zero 2</li>
 *            <li>Case B-2: Has only one 2</li>
 *      </ul>
 * </ul>
 */
public class Triti {
	private final int team;
	private ArrayList<Card> cards;
	private ArrayList<Card> tempTritiCards;
	private int suit;
	
	// The constructor is private so a triti can only be created from a static context after being validated
	private Triti(ArrayList<Card> cards, Characteristics characteristics) {
		this.cards = cards;
		// team number starts at 1
		this.team = GameController.getInstance().getCurrentPlayer().getTeamNumber();
		this.suit = cards.get(0).getSuit();
	}
	
	// Validates the list of cards and if they can form a triti creates one
	@SuppressWarnings("Since15")
	public static Triti createTriti(List<Card> cards) {
		if(canCreateValidTriti(cards)) {
			cards.sort(Collections.<Card>reverseOrder());
			// TODO: checkAce(cards); needed here?
			return new Triti((ArrayList<Card>) cards, Characteristics.getCharacteristics());
		}
		return null;
	}
	
	// Checks if list cards can create a triti
	public static boolean canCreateValidTriti(List<Card> cards) {
		// Basic validity checks
		if(!checkSize(cards)) return false;
		// sort by value
		Collections.sort(cards);
		// gather information
		Characteristics characteristics = Characteristics.getNewCharacteristics();
		setCharacteristics(cards, characteristics);
		if(characteristics.failsBasicChecks()) return false;
		return checkCase(characteristics, cards);
	}
	
	private static boolean checkSize(List<Card> cards) {
		return cards.size() >= 3;
	}
	
	private static void setCharacteristics(List<Card> cards, Characteristics characteristics) {
		characteristics.noJokers = new ArrayList<>(cards);
		for(Card card : cards) {
			if(card.isJoker) {
				characteristics.joker = card;
				characteristics.numJokers++;
				characteristics.noJokers.remove(card);
			}
			if(card.getRank() == 2) {
				characteristics.twosList.add(card);
				characteristics.numTwos++;
			}
		}
	}
	
	// Executes the appropriate case and returns true if it can form a triti. Case returns true if all checks pass
	private static boolean checkCase(Characteristics characteristics, List<Card> cards) {
		// CASE 1: JOKER
		if(characteristics.numJokers == 1) {
			// Case 1-A: J
			if(characteristics.numTwos == 0) {
				return CheckTriti.checkCase_1A_Joker(characteristics, cards);
			}
			// Case 1-B: J + 2
			if(characteristics.numTwos == 1) {
				return CheckTriti.checkCase_1B_Joker_Two(characteristics, cards);
			}
		}
		
		// CASE 2: NO JOKER
		if(characteristics.numJokers == 0) {
			// Case 2-A: no wildcards
			if(characteristics.numTwos == 0) {
				return CheckTriti.checkCase_2A_NoWildCards(characteristics, cards);
			}
			// Case 2-B: 2
			if(characteristics.numTwos == 1) {
				return CheckTriti.checkCase_2B_Two(characteristics, cards);
			}
			// Case 2-C: 2 + 2
			if(characteristics.numTwos == 2) {
				return CheckTriti.checkCase_2B_TwoTwo(characteristics, cards);
			}
		}
		// if none of the case are applicable something must be wrong
		return false;
	}
	
	// Used for testing
	public ArrayList<Integer> getCardsAsValues() {
		ArrayList<Integer> result = new ArrayList<>(cards.size());
		for(Card card : cards) {
			result.add(card.getRank());
		}
		return result;
	}
	
	public boolean addCard(Card card) {
		if(!validateAddCard(card)) return false;
		// The temporary cards became the cards of the triti
		cards = tempTritiCards;
		cards.sort(Collections.<Card>reverseOrder());
		
		return true;
	}
	
	// For playerAI to check if card can be added without affecting the triti
	public boolean validateAddCard(Card card) {
		// triti is complete
		if(this.cards.size() == 13) return false;
		// create a deep copy of the triti cards and store it
		tempTritiCards = new ArrayList<>(this.getCards().size());
		for(int i = 0; i < this.getCards().size(); i++) {
			tempTritiCards.add(new Card(this.getCards().get(i).getInitialValue()));
		}
		// add the card to them
		tempTritiCards.add(card);
		// try to create a triti with all the previous triti cards and the new card
		return canCreateValidTriti(tempTritiCards);
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public int getTeam() {
		return team;
	}
	
	// Used to collect information from the list of cards that try to create a triti
	public static class Characteristics {
		private static Characteristics instance;
		public int numTwos;
		public List<Card> twosList;
		public List<Card> noJokers;
		public Card twoAsAJoker;
		public Card joker;
		int numJokers;
		
		private Characteristics() {
			this.numJokers = 0;
			this.numTwos = 0;
			this.twosList = new ArrayList<>();
			this.noJokers = new ArrayList<>();
			this.twoAsAJoker = null;
			this.joker = null;
		}
		
		// replace the old static instance with a new one for a new list of cards
		public static Characteristics getNewCharacteristics() {
			instance = new Characteristics();
			return instance;
		}
		
		public static Characteristics getCharacteristics() {
			return instance;
		}
		
		public boolean failsBasicChecks() {
			// Only one Joker allowed
			if(numJokers > 1) return true;
			// Maximum two 2s allowed
			if(numTwos > 2) return true;
			// Not allowed to have a 2 as a joker and a Joker
			if(numTwos == 2 && numJokers == 1) return true;
			
			return false;
		}
		
	}
}
