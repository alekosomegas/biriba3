package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import com.akgames.biriba3.view.CardActor;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.List;

public class AddCardToTriti implements PlayerAction{
    @Override
    public void execute() {

    }

    @Override
    public void execute(List<?> params) {
        DragAndDrop.Payload payload = (DragAndDrop.Payload) params.get(0);
        CardActor cardActor = (CardActor) payload.getDragActor();
        Card card = cardActor.getCard();

        GameLogic.getInstance().getCurrentPlayer().getHand().remove(card);

        TritiActor tritiActor = (TritiActor)params.get(1);
        Triti triti = tritiActor.getTriti();

        triti.addCard(card);
    }

    @Override
    public void undo() {

    }
}
