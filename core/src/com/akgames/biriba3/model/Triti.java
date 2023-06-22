package com.akgames.biriba3.model;

import com.akgames.biriba3.controller.GameLogic;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triti {
    private int team;
    private List<Card> cards;

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
                // Must have the same suit and be sequential
                if (differentSuit(cards) ||
                        isNotSequential(cards)) return false;
            }
            // -------------------------------------------------------//
            // Case 2-b: Has one 2
            if(characteristics.numTwos == 1) {
                // 2 is normal if (3 AND A) or (4 AND 3) exist
                // and is a joker if a gap exists

                // Normal 2 (all cards in sequence)  => 3-2-A, 4-3-2,
                if(!isNotSequential(cards)) return true;

                // Joker normal gap                  => 8-2-3, 9-8-2
                // Joker when 3 and 4 exist          => 6-2-4-3
                // remove the 2 and check if a gap exists
                Card two = characteristics.twosList.get(0);
                characteristics.noJokers.remove(two);
                int missingRank = singleGapExists(characteristics.noJokers);
                if (missingRank != -1) {
                    // Turn 2 into a joker and fill the gap
                    two.setValueAndRank(missingRank, cards.get(0).getSuit());
                }
            }
            // -------------------------------------------------------//
            // Case 2-c: Has two 2
            if(characteristics.numTwos == 2) {
                // One 2 must be normal and other a joker.
                // To be normal at least one of the is same suit
                // AND if (4-2-2, 3-2-2, 2-2-A, 2-3-2-A )

                // if not (4 or 3 or A) not valid
                if(!canHaveANormalTwo(cards)) return false;

                // set joker and normal 2
                // TODO: BUG same suit.
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

//                for(Card two : characteristics.twosList) {
//                    // if different suit
//                    if (two.getSuit() != cards.get(cards.size()-1).getSuit()) {
//                        // Both twos can not have different suit from the largest card in set
//                        if(characteristics.twoAsAJoker != null) return false;
//                        characteristics.twoAsAJoker = two;
//                    }
//                    if(characteristics.twoAsAJoker == null) characteristics.twoAsAJoker = two;
//                }

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




//        return checkSize(cards) && checkJokers(cards) && checkTwos(cards);
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

    //TODO: refactor. Split case noJoker, Joker, 2 as a Joker, 2 2s
    private static boolean checkJokers(List<Card> cards) {
        int numJokers = 0;
        Card joker = null;
        // all cards except jokers
        List<Card> tempList = new ArrayList<>(cards);
        for(Card card : cards) {
            if (card.isJoker) {
                joker = card;
                tempList.remove(card);
                numJokers++;
            }
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
                //TODO: not checking 2
                else return false;
            }else {
                return false;
            }
        }

        // If it is sequential and a Joker exist put it on bottom
        if (numJokers == 1 && !isNotSequential(tempList)) {
            joker.setValueAndRank(tempList.get(0).getRank() -1, tempList.get(0).getSuit());
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

    private static boolean checkTwos(List<Card> cards) {
        // Can only have one as this comes after the joker check
        boolean hasJoker = false;
        int numTwos = 0;
        List<Card> twosList = new ArrayList<>(2);
        Card TwoAsAJoker = null;

        // all cards except jokers
        List<Card> tempList = new ArrayList<>(cards);
        for(Card card : cards) {
            if (card.isJoker) {
                tempList.remove(card);
                hasJoker = true;
            }
            if (card.getRank() == 2) {
                // not allowed to have more than two 2s
                if(numTwos == 2) return false;
                numTwos++;
                twosList.add(card);
            }
        }

        // One 2
        if(numTwos == 1) {
            // Is it a normal 2?
            isNotSequential(tempList);

        }

        //  Two 2s
        if(numTwos == 2) {

        }

        // including no 2s
        return true;
    }
}
