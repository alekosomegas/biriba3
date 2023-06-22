package com.akgames.biriba3.model;

import com.akgames.biriba3.controller.GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triti {
    private int team;
    List<Card> cards;

    private Triti(List<Card> cards) {
        this.cards = cards;
        // team number starts at 1
        this.team = GameLogic.getInstance().getCurrentPlayer().getTeamNumber()-1;
    }

    @SuppressWarnings("Since15")
    public static Triti createTriti(List<Card> cards) {
        if (validate(cards)) {
            cards.sort(Collections.<Card>reverseOrder());
            return new Triti(cards);
        }
        return null;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getTeam() {
        return team;
    }

    /**
     * Rules:
     *      1.  The list must contain at least three cards.
     *      2.  All cards  in the list (except Jokers) must be of the same suit.
     *      3.  The ranks of the cards must be consecutive.
     *
     *      4.  Only one Joker(or a 2) is allowed. Joker takes new value
     *      5.  A 2 becomes a joker :
     *              A. if a 2 already exists
     *                  # 1-2
     *                  # 2-3
     *              B. if no other 2 exist
     *                  # NOT 1-3
     *                  # NOT 3-4
     *      6.  An Ace is a 13 if K and Q exist, 1 if not.
     *
     *  Cases:
     *      A. No Jokers
     *          1.  All good, sequential and same suit : 5-6-7          TRUE
     *          2.  Same suit, not sequential : 5-7-10                  FALSE
     *          3.  Sequential, different suit : 5-6-7                  FALSE
     *
     *      B. 1 Joker
     *          1.  All sequential and valid plus a Joker : 5-6-J       TRUE
     *          2.  Sequential except ONLY 1 gap : 6-J-7 (find gaps)    TRUE
     *
     *
     */
    private static boolean validate(List<Card> cards) {
        return checkSize(cards) && checkJokers(cards);
    }

    private static boolean checkSize(List<Card> cards) {
        return cards.size() >= 3;
    }

    private static boolean checkJokers(List<Card> cards) {
        Collections.sort(cards);

        int numJokers = 0;
        int numTwos = 0;
        Card joker = null;
        // all cards except jokers
        List<Card> tempList = new ArrayList<>(cards);
        for(Card card : cards) {
            if (card.isJoker) {
                joker = card;
                tempList.remove(card);
                numJokers++;
            }
            if (card.getRank() == 2) numTwos++;
        }

        // Only one Joker allowed
        if(numJokers > 1) return false;

        // Must have same suit
        if (differentSuit(tempList)) return false;

        // Must be consecutive. If case J-4-5 will return true
        if(isNotSequential(tempList)) {
            // If a joker exist
            if (numJokers == 1) {
                // Fill gap with Joker
                int missingRank = singleGapExists(tempList);
                if( missingRank != -1) {
                    joker.setValueAndRank(missingRank,tempList.get(0).getSuit());
                }
            }else {
                return false;
            }
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
     * Finds the missing cards Rank is a single gap exists in the list. 5-gap-7 => 6.
     * @param cards No jokers, sorted.
     * @return The missing Rank int if and only if only one gap is found. -1 Otherwise.
     */
    private static int singleGapExists(List<Card> cards) {
        int validGaps = 0;
        int missingRank = -1;
        for (int i = 0; i < cards.size() - 1; i++) {
            Card current = cards.get(i);
            Card next = cards.get(i + 1);
            if (current.getRank() + 2 == next.getRank()) {
                validGaps++;
                missingRank = current.getRank() + 1;
            }
        }
        return validGaps == 1 ? missingRank : -1;
    }
    private static boolean isNotSequential(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            Card current = cards.get(i);
            Card next = cards.get(i + 1);
            if (current.getRank() + 1 != next.getRank()) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkAce(List<Card> tempList) {
        if (tempList.get(0).getRank() == 1) {
            if (tempList.get(tempList.size() - 1).getRank() == 12 && tempList.get(tempList.size() - 2).getRank() == 11) {
//                tempList.get(0).setRank(13);
            } else {
//                tempList.get(0).setRank(1);
            }
            Collections.sort(tempList);
        }

        return true;

    }
}
