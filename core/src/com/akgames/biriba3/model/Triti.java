package com.akgames.biriba3.model;

import com.akgames.biriba3.controller.GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triti {
    private int team;
    private ArrayList<Card> cards;

    private Triti(ArrayList<Card> cards) {
        this.cards = cards;
        // team number starts at 1
        this.team = GameLogic.getInstance().getCurrentPlayer().getTeamNumber()-1;
    }

    @SuppressWarnings("Since15")
    public static Triti createTriti(List<Card> cards) {
        if (validate(cards)) {
            cards.sort(Collections.<Card>reverseOrder());
            checkAce(cards);
            return new Triti((ArrayList<Card>) cards);
        }
        return null;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        // Validate card
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
     *         // Case 1: Has a Joker
     *             // Case 1-a: Has zero 2
     *             // Case 1-b: Has only one 2
     *
     *         // Case 2: Does not have a Joker
     *             // Case 2-a: Has zero 2
     *             // Case 2-b: Has one 2
     *             // Case 2-c: Has two 2
     *
     */
    private static class Characteristics {
        int numJokers;
        int numTwos;
        List<Card> twosList;
        List<Card> noJokers;
        Card twoAsAJoker;
        Card joker;
        public Characteristics() {
            this.numJokers = 0;
            this.numTwos = 0;
            this.twosList = new ArrayList<>();
            this.noJokers = new ArrayList<>();
            this.twoAsAJoker = null;
            this.joker = null;
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
    private static boolean validate(List<Card> cards) {
        // Basic validity checks
        if (!checkSize(cards)) return false;
        Collections.sort(cards);

        Characteristics characteristics = new Characteristics();
        setCharacteristics(cards, characteristics);
        if(characteristics.failsBasicChecks()) return false;

        // -------------------------------------------------------//
        // Case 1: Has a Joker
        if(characteristics.numJokers == 1) {
            // -------------------------------------------------------//
            // Case 1-a: Has zero 2
            if(characteristics.numTwos == 0) {
                // remove joker, check rest for same suit, if not seq for gap
                if (differentSuit(characteristics.noJokers)) return false;
                checkAce(cards);
                // Must be consecutive. If case J-4-5 will return true
                if(isNotSequential(characteristics.noJokers)) {
                    // Fill gap with Joker
                    int missingRank = singleGapExists(characteristics.noJokers);
                    if( missingRank != -1) {
                        characteristics.joker.setValueAndRank(missingRank,characteristics.noJokers.get(0).getSuit());
                    }
                    else return false;
                } else {
                    characteristics.joker.setValueAndRank(characteristics.noJokers.get(0).getRank() -1,characteristics.noJokers.get(0).getSuit());
                }

            }
            // -------------------------------------------------------//
            // Case 1-b: Has only one 2
            if(characteristics.numTwos == 1) {
                //TODO: refactor same as above except 1st line
                if(!canHaveANormalTwo(characteristics.noJokers)) return false;
                // remove joker, check rest for same suit, if not seq for gap
                if (differentSuit(characteristics.noJokers)) return false;
                checkAce(cards);
                // Must be consecutive. If case J-4-5 will return true
                if(isNotSequential(characteristics.noJokers)) {
                    // Fill gap with Joker
                    int missingRank = singleGapExists(characteristics.noJokers);
                    if( missingRank != -1) {
                        characteristics.joker.setValueAndRank(missingRank,characteristics.noJokers.get(0).getSuit());
                    }
                    else return false;
                } else {
                    characteristics.joker.setValueAndRank(characteristics.noJokers.get(0).getRank() -1,characteristics.noJokers.get(0).getSuit());
                }
            }
        }
        // -------------------------------------------------------//
        // Case 2: Does not have a Joker
        if(characteristics.numJokers == 0) {
            // -------------------------------------------------------//
            // Case 2-a: Has zero 2
            if(characteristics.numTwos == 0) {
                checkAce(cards);
                // Must have the same suit and be sequential
                if (differentSuit(cards) ||
                        isNotSequential(cards)) return false;
            }
            // -------------------------------------------------------//
            // Case 2-b: Has one 2 and no joker
            if(characteristics.numTwos == 1) {
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
                    if (missingRank != -1) {
                        // Turn 2 into a joker and fill the gap
                        two.setValueAndRank(missingRank, characteristics.noJokers.get(0).getSuit());
                    }
                    else return false;
                } else {
                    two.setValueAndRank(characteristics.noJokers.get(0).getRank() -1, characteristics.noJokers.get(0).getSuit());
                }
            }
            // -------------------------------------------------------//
            // Case 2-c: Has two 2
            if(characteristics.numTwos == 2) {
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
                        characteristics.twoAsAJoker = two;
                    }
                }
                // If both have the same suit use first as joker
                if(characteristics.twoAsAJoker == null) characteristics.twoAsAJoker = characteristics.twosList.get(0);

                // remove the 2 that is a joker from the cards
                characteristics.noJokers.remove(characteristics.twoAsAJoker);

                // must be same suit
                if(differentSuit(characteristics.noJokers)) return false;

                // if the rest is in sequence (e.g. 4-3-2) then attach 2 at bottom
                if(!isNotSequential(characteristics.noJokers)){
                    // turn 2 to a joker
                    characteristics.twoAsAJoker.setValueAndRank(characteristics.noJokers.get(0).getRank(),
                            characteristics.noJokers.get(0).getSuit());
                    return true;
                }
                // Find and fill the gap
                else {
                    int missingRank = singleGapExists(characteristics.noJokers);
                    if (missingRank != -1) {
                        // Turn 2 into a joker and fill the gap
                        characteristics.twoAsAJoker.setValueAndRank(missingRank, cards.get(0).getSuit());
                        return true;
                    } else return false;
                }
            }
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
        for (Card card : cards) {
            if (card.getRank() == 1) {
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

    private static void setCharacteristics(List<Card> cards, Characteristics characteristics) {
        characteristics.noJokers = new ArrayList<>(cards);
        for(Card card : cards) {
            if (card.isJoker) {
                characteristics.joker = card;
                characteristics.numJokers++;
                characteristics.noJokers.remove(card);
            }
            if (card.getRank() == 2) {
                characteristics.twosList.add(card);
                characteristics.numTwos++;
            }
        }
    }
    private static boolean checkSize(List<Card> cards) {
        return cards.size() >= 3;
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
            // found a gap bigger than one
            if(next.getRank() - current.getRank() > 2) {
                return -1;
            }
            // found a gap
            if (current.getRank() + 2 == next.getRank()) {
                // return -1 if a second gap is found
                if(validGaps == 1) return -1;
                // update only on first find
                validGaps = 1;
                missingRank = current.getRank() + 1;
            }
        }
        return missingRank;
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


    /**
     * Check if K and Q exist and change to 13
     */
    private static void checkAce(List<Card> cards) {
        boolean hasKing = false;
        boolean hasQueen = false;
        Card ace = null;

        for(Card card : cards) {
            if (card.getRank() == 13) hasKing = true;
            if (card.getRank() == 12) hasQueen = true;
            if (card.getRank() == 1) ace = card;
        }

        if(ace != null && hasKing && hasQueen) {
            // It is ok to have value of 13 (same as A of next suit)
            // because it will never leave the triti
            ace.setValueAndRank(14, ace.getSuit());
            // sort again to place ace on top
            Collections.sort(cards);
        }

    }


}
