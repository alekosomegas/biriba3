package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.*;

public class PickFromDeck implements PlayerAction{
    private Card tempCard;
    private GameLogic gameLogic;

    public PickFromDeck() {
        this.gameLogic = GameLogic.getInstance();;
    }

    @Override
    public void execute() {
        Card card = gameLogic.getBoard().getDeck().getTopCard();
        tempCard = card;
        if(gameLogic.currentPlayerIndex == 0) {
            card.setShowFace(true);
            card.setSelected(true);
            GameLogic.getInstance().getSelectedCards().add(card);
        }
        gameLogic.getPlayers().get(gameLogic.currentPlayerIndex).addToHand(card);

        Turn.nextPhase();
    }

    @Override
    public void execute(List<?> params) {
        GameLogic.getInstance().setCurrentPlayerHasThrownCard(false);

    }

    @Override
    public void undo() {
        gameLogic.getBoard().getDeck().addCard(tempCard);
        //TODO: refactor
        gameLogic.getPlayers().get(gameLogic.currentPlayerIndex).removeCard(tempCard);
    }

    @Override
    public boolean allowed() {
        // Only one action can be performed
        return Turn.CurrentPhase() == PICK;
    }
}
