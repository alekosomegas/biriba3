package com.akgames.biriba3.model;


import com.akgames.biriba3.actions.*;
import com.akgames.biriba3.controller.GameLogic;

import java.util.List;
import java.util.Random;

//TODO: chose Actions at random
public class PlayerAI extends  Player{
    public PlayerAI(String name, int teamNumber) {
        super(name, teamNumber);
    }

    @Override
    public void act() {
        Random rand = new Random();
        // either 0 or 1
        int randomNum = rand.nextInt(2);
        int randNumCard = rand.nextInt(getHand().size());

        PlayerAction playerAction = randomNum == 0 ?
                new PickDiscards() : new PickFromDeck();
        GameLogic.getInstance().handleAction(playerAction);

        GameLogic.getInstance().handleAction(new AddCardToDiscards(getHand().get(randNumCard).turn()));
        GameLogic.getInstance().handleAction(new EndTurn());
    }


    @Override
    public void addToHand(Card card) {
        card.setShowFace(false);
        getHand().add(card);
    }

    @Override
    public void addToHand(List<Card> cards) {
        for (Card card : cards)
            {addToHand(card);
        }
    }
}
