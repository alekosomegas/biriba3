package com.akgames.biriba3.Utils;

import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO: perhaps simplify by looping over the ranks and assign it to the joker and then just
//check if cards values are in sequence.
public class CheckTriti {
	
	public static boolean checkCase_1A_Joker(Triti.Characteristics characteristics, List<Card> cards) {
		// remove joker, check rest for same suit, if not seq for gap
		if(differentSuit(characteristics.noJokers)) return false;
		checkAce(cards);
		// Must be consecutive. If case J-4-5 will return true
		if(isNotSequential(characteristics.noJokers)) {
			// Fill gap with Joker
			int missingRank = singleGapExists(characteristics.noJokers);
			if(missingRank != -1) {
				characteristics.joker.setValueAndRankAndSuit(missingRank, characteristics.noJokers.get(0).getSuit());
			} else return false;
		} else {
			characteristics.joker.setValueAndRankAndSuit(characteristics.noJokers.get(0).getRank() - 1, characteristics.noJokers.get(0).getSuit());
		}
		return true;
	}
	
	private static boolean differentSuit(List<Card> cards) {
		int suit = cards.get(0).getSuit();
		for(Card card : cards) {
			if(card.getSuit() != suit) return true;
		}
		return false;
	}
	
	/**
	 * Check if K and Q exist and change to 13
	 */
	private static void checkAce(List<Card> cards) {
		boolean hasKing = false;
		boolean hasQueen = false;
		boolean hasJoker = false;
		Card ace = null;
		
		for(Card card : cards) {
			if(card.getRank() == 13) hasKing = true;
			if(card.getRank() == 12) hasQueen = true;
			if(card.isJoker) hasJoker = true;
			if(card.getRank() == 1) ace = card;
		}
		
		// A-K-Q, A-J-Q, A-K-J
		if((ace != null) && ((hasKing && hasQueen) || ((hasKing || hasQueen) && hasJoker))) {
			// It is ok to have value of 13 (same as A of next suit)
			// because it will never leave the triti
			ace.setValueAndRankAndSuit(14, ace.getSuit());
			// sort again to place ace on top
			Collections.sort(cards);
		}
		
	}
	
	private static boolean isNotSequential(List<Card> cards) {
		Collections.sort(cards);
		for(int i = 0; i < cards.size() - 1; i++) {
			Card current = cards.get(i);
			Card next = cards.get(i + 1);
			if(current.getRank() + 1 != next.getRank()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds the missing cards Rank is a single gap exists in the list. 5-gap-7 => 6.
	 *
	 * @param cards
	 * 		No jokers, sorted.
	 * @return The missing Rank int if and only if only one gap is found. -1 Otherwise.
	 */
	private static int singleGapExists(List<Card> cards) {
		Collections.sort(cards);
		int validGaps = 0;
		int missingRank = -1;
		for(int i = 0; i < cards.size() - 1; i++) {
			Card current = cards.get(i);
			Card next = cards.get(i + 1);
			// found a gap bigger than one
			if(next.getRank() - current.getRank() > 2) {
				return -1;
			}
			// found a gap
			if(current.getRank() + 2 == next.getRank()) {
				// return -1 if a second gap is found
				if(validGaps == 1) return -1;
				// update only on first find
				validGaps = 1;
				missingRank = current.getRank() + 1;
			}
		}
		return missingRank;
	}
	
	public static boolean checkCase_1B_Joker_Two(Triti.Characteristics characteristics, List<Card> cards) {
		if(!canHaveANormalTwo(characteristics.noJokers)) return false;
		// remove joker, check rest for same suit, if not seq for gap
		if(differentSuit(characteristics.noJokers)) return false;
		checkAce(cards);
		// Must be consecutive. If case J-4-5 will return true
		if(isNotSequential(characteristics.noJokers)) {
			// Fill gap with Joker
			int missingRank = singleGapExists(characteristics.noJokers);
			if(missingRank != -1) {
				characteristics.joker.setValueAndRankAndSuit(missingRank, characteristics.noJokers.get(0).getSuit());
			} else return false;
		} else {
			// TODO: refactor. very confusing
			// if an ace exist at rank 1 joker should go up
			int up = characteristics.noJokers.get(characteristics.noJokers.size() - 1).getRank() + 1;
			int down = characteristics.noJokers.get(0).getRank() - 1;
			characteristics.joker.setValueAndRankAndSuit(characteristics.noJokers.get(0).getRank() == 1 ? up : down, characteristics.noJokers.get(0).getSuit());
		}
		return true;
	}
	
	/**
	 * Checks if ace or three exist
	 */
	private static boolean canHaveANormalTwo(List<Card> cards) {
		// check if cards exist
		boolean ace = false;
		boolean three = false;
		boolean four = false;
		for(Card card : cards) {
			if(card.getRank() == 1) {
				ace = true;
				continue;
			}
			if(card.getRank() == 3) {
				three = true;
				continue;
			}
			if(card.getRank() == 4) {
				four = true;
			}
		}
		return (ace || three || four);
	}
	
	public static boolean checkCase_2A_NoWildCards(Triti.Characteristics characteristics, List<Card> cards) {
		if(characteristics.numTwos == 0) {
			checkAce(cards);
			// Must have the same suit and be sequential
			if(differentSuit(cards) || isNotSequential(cards)) return false;
		}
		return true;
	}
	
	public static boolean checkCase_2B_Two(Triti.Characteristics characteristics, List<Card> cards) {
		// 2 is normal if (3 AND A) or (4 AND 3) exist
		// and is a joker if a gap exists
		checkAce(cards);
		// Normal 2 (all cards in sequence ans same suit)  => 3-2-A, 4-3-2,
		if(!isNotSequential(cards) && !differentSuit(cards)) return true;
		// Joker normal gap                  => 8-2-3, 9-8-2
		// Joker when 3 and 4 exist          => 6-2-4-3
		// remove the 2 and check if a gap exists
		Card two = characteristics.twosList.get(0);
		characteristics.noJokers.remove(two);
		// check that the rest have the same suit
		if(differentSuit(characteristics.noJokers)) return false;
		if(isNotSequential(characteristics.noJokers)) {
			int missingRank = singleGapExists(characteristics.noJokers);
			if(missingRank != -1) {
				// Turn 2 into a joker and fill the gap
				two.setValueAndRankAndSuit(missingRank, characteristics.noJokers.get(0).getSuit());
				characteristics.twoAsAJoker = two;
			} else return false;
		} else {
			two.setValueAndRankAndSuit(characteristics.noJokers.get(0).getRank() - 1, characteristics.noJokers.get(0).getSuit());
			characteristics.twoAsAJoker = two;
		}
		return true;
	}
	
	public static boolean checkCase_2B_TwoTwo(Triti.Characteristics characteristics, List<Card> cards) {
		// One 2 must be normal and other a joker.
		// To be normal at least one of the is same suit
		// AND if (4-2-2, 3-2-2, 2-2-A, 2-3-2-A )
		checkAce(cards);
		// if not (4 or 3 or A) not valid
		if(!canHaveANormalTwo(cards)) return false;
		// set joker and normal 2
		// Take both 2s out temporarily, check suit of remaining and save it
		// then loop through twos find first that matches suit assign other as joker.
		// if none found return false
		List<Card> noTwos = new ArrayList<>(cards);
		noTwos.removeAll(characteristics.twosList);
		if(differentSuit(noTwos)) return false;
		int suit = noTwos.get(0).getSuit();
		for(Card two : characteristics.twosList) {
			if(two.getSuit() != suit) {
				// Both twos can not have different suit from the rest
				if(characteristics.twoAsAJoker != null) return false;
				// find 2 as a joker
				characteristics.twoAsAJoker = two;
			}
		}
		// If both have the same suit use first as joker
		if(characteristics.twoAsAJoker == null) characteristics.twoAsAJoker = characteristics.twosList.get(0);
		// remove the 2 that is a joker from the cards
		characteristics.noJokers.remove(characteristics.twoAsAJoker);
		// must be same suit
		if(differentSuit(characteristics.noJokers)) return false;
		// if the rest is in sequence (e.g. 4-3-2) then attach 2
		if(!isNotSequential(characteristics.noJokers)) {
			// turn 2 to a joker at bottom if no ace there
			int index = characteristics.noJokers.get(0).getRank() == 1 ? characteristics.noJokers.size() - 1 : 0;
			// TODO: refactor.
			// this is to offset the way setRank works(removes 1)
			int i = index == 0 ? -1 : 1;
			characteristics.twoAsAJoker.setValueAndRankAndSuit(characteristics.noJokers.get(index).getRank() + i, characteristics.noJokers.get(index).getSuit());
			return true;
		}
		// Find and fill the gap
		else {
			int missingRank = singleGapExists(characteristics.noJokers);
			if(missingRank != -1) {
				// Turn 2 into a joker and fill the gap
				characteristics.twoAsAJoker.setValueAndRankAndSuit(missingRank, cards.get(0).getSuit());
				return true;
			} else return false;
		}
	}
	
	
}
