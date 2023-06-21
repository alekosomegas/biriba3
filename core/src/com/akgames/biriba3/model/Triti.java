package com.akgames.biriba3.model;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;

import java.util.List;

public class Triti {
    private int team;
    List<Card> cards;

    private Triti(List<Card> cards) {
        this.cards = cards;
        // team number starts at 1
        this.team = GameLogic.getInstance().getCurrentPlayer().getTeamNumber()-1;
    }

    public static Triti createTriti(List<Card> cards) {
        if(cards.size() < 3) {
            return null;
        }
        return new Triti(cards);
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
}
