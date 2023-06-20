package com.akgames.biriba3.model;

/**
 *
 * value is the position of the card in an ordered deck.
 * It can change in the case of wild cards
 */
public class Card {
    private int suit, rank, value, points;
    private String imageUrl;
    private final String[] verbose_suit = {"Diamond", "Club", "Heart", "Spade"};
    private final String[] verbose_rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suit_symbol = {"\u2662", "\u2663", "\u2661", "\u2660"};

    private boolean showFace;

    // value is between 0-51, -1 for joker
    public Card(int value) {
        this.value = value;
        showFace = false;
        // Joker
        if (value == -1) {
            this.suit = -1;
            this.rank = -1;
        } else {
            this.suit = value / 13;
            this.rank = value % 13;
            this.imageUrl =
                    (verbose_rank[rank] + verbose_suit[suit] + ".png").toLowerCase();

        }
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

    @Override
    public String toString(){
        return suit_symbol[suit]+verbose_rank[rank%13];
    }
}
