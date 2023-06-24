package com.akgames.biriba3.model;

/**
 *
 * value is the position of the card in an ordered deck.
 * It can change in the case of wild cards
 */
public class Card implements Comparable<Card> {
    private int suit, rank, value, initialValue, points;
    private String imageUrl;
    private final String[] verbose_suit = {"Diamond", "Club", "Heart", "Spade"};
    private final String[] verbose_rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suit_symbol = {"\u2662", "\u2663", "\u2661", "\u2660"};
    public boolean isJoker;

    private boolean showFace;

    private boolean clickable;
    private boolean selected;

    // value is between 0-51, -1 for joker
    public Card(int value) {
        this.value = value;
        this.initialValue = value;
        showFace = false;
        // Joker
        if (value == -1) {
            this.suit = -1;
            this.rank = -1;
            this.isJoker = true;
            this.imageUrl = "joker.png";
        } else {
            this.suit = value / 13;
            this.rank = value % 13 +1;
            this.isJoker = false;
            this.imageUrl =
                    (verbose_rank[rank -1] + verbose_suit[suit] + ".png").toLowerCase();
        }

        this.clickable = false;
        this.selected = false;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isShowFace() {
        return showFace;
    }
    public Card turn() {
        showFace = !showFace;
        return this;
    }

    public void setShowFace(boolean showFace) {
        this.showFace = showFace;
    }

    @Override
    public String toString(){
        return isJoker ? "Joker" : verbose_suit[suit]+verbose_rank[(rank -1) % 13 ];
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSuit() {
        return suit;
    }

    /**
     *
     * @return rank
     */
    public int getRank() {
        return rank;
    }


    public void setValueAndRankAndSuit(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = suit * 13 + rank -1;
    }

    @Override
    public int compareTo(Card c){
        return this.value - c.value;
    }

    public void resetValues() {
        this.value = initialValue;
        if(this.isJoker) {
            this.suit = -1;
            this.rank = -1;
        }else {
            this.suit = value / 13;
            this.rank = value % 13 +1;
        }
    }

    public int getInitialValue() {
        return initialValue;
    }

    public int getValue() {
        return value;
    }
    //    @Override
//    public boolean equals(Object obj) {
//        if (obj == this) {
//            return true;
//        }
//        if (!(obj instanceof Card)) {
//            return false;
//        }
//        Card other = (Card) obj;
//        return initialValue == other.initialValue;
//    }
}
