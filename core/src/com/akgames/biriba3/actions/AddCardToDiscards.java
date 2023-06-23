package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.view.CardActor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.List;

public class AddCardToDiscards implements PlayerAction{
    private Card card;

    public AddCardToDiscards(Card card) {
        this.card = card;
        card.setShowFace(true);
        card.setSelected(false);
        card.setClickable(false);
        GameLogic.getInstance().getSelectedCards().remove(card);
    }

    @Override
    public void execute() {
        GameLogic.getInstance().getBoard().addToDiscardPile(card);
        GameLogic.getInstance().getCurrentPlayer().getHand().remove(card);
    }

    @Override
    public void execute(List<?> params) {
        DragAndDrop.Payload payload = (DragAndDrop.Payload) params.get(0);
        CardActor cardActor = (CardActor) payload.getDragActor();
        GameLogic.getInstance().getBoard().addToDiscardPile(cardActor.getCard());
        //TODO: refactor
        GameLogic.getInstance().getPlayers().get(0).getHand().remove(cardActor.getCard());
    }

    @Override
    public void undo() {
    }
}
