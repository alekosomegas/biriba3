package com.akgames.biriba3.actions;

import com.akgames.biriba3.model.Card;

import java.util.List;

public class PickFromDeck implements PlayerAction{
    private Card tempCard;

    @Override
    public void execute() {
        Card card = gameLogic.getBoard().getDeck().getTopCard();
        tempCard = card;
        card.turn();
        gameLogic.getPlayers().get(gameLogic.currentPlayerIndex).addToHand(card);
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {
        System.out.println("undo");
        gameLogic.getBoard().getDeck().addCard(tempCard);
        //TODO: refactor
        gameLogic.getPlayers().get(gameLogic.currentPlayerIndex).getHand().remove(tempCard);
    }
}
