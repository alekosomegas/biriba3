package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;

import java.util.List;

public class PickFromDeck implements PlayerAction{
    private Card tempCard;
    private GameLogic gameLogic;

    public PickFromDeck() {
        this.gameLogic = GameLogic.getInstance();
    }

    @Override
    public void execute() {
        Card card = gameLogic.getBoard().getDeck().getTopCard();
        tempCard = card;
        if(gameLogic.currentPlayerIndex == 0) {
            card.turn();
        }
        gameLogic.getPlayers().get(gameLogic.currentPlayerIndex).addToHand(card);
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {
        gameLogic.getBoard().getDeck().addCard(tempCard);
        //TODO: refactor
        gameLogic.getPlayers().get(gameLogic.currentPlayerIndex).getHand().remove(tempCard);
    }
}
