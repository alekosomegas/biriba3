package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.badlogic.gdx.Gdx;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;

public class ThrowCardToDiscards implements PlayerAction{
    private Card card;

    public ThrowCardToDiscards(Card card) {
        this.card = card;
        card.setShowFace(true);
        card.setSelected(false);
        card.setClickable(false);
        // TODO: refactor selected cards
        GameLogic.getInstance().getSelectedCards().remove(card);
    }

    @Override
    public void execute() {
        GameLogic.getInstance().getBoard().addToDiscardPile(card);
        GameLogic.getInstance().getCurrentPlayer().removeCard(card);
        GameLogic.getInstance().setCurrentPlayerHasThrownCard(true);
        Turn.nextPhase();
    }

    @Override
    public void execute(List<?> params) {
//        DragAndDrop.Payload payload = (DragAndDrop.Payload) params.get(0);
//        CardActor cardActor = (CardActor) payload.getDragActor();
//        GameLogic.getInstance().getBoard().addToDiscardPile(cardActor.getCard());
//        //TODO: refactor
//        GameLogic.getInstance().getPlayers().get(0).getHand().remove(cardActor.getCard());
//
//        Turn.nextPhase();
    }

    @Override
    public void undo() {
    }

    @Override
    public boolean allowed() {
        return Turn.CurrentPhase() == DISCARD;
    }
}
