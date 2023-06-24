package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import com.akgames.biriba3.view.CardActor;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

// TODO: CHECK FOR DUPLICATES
public class AddCardToTriti implements PlayerAction{
    private Card card;
    private Triti triti;

    public AddCardToTriti(Card card, Triti triti) {
        this.card = card;
        this.triti = triti;
    }

    public AddCardToTriti() {

    }

    @Override
    public void execute() {
        boolean success = triti.addCard(card);

        if (success) {
            GameLogic.getInstance().getCurrentPlayer().removeCard(card);
            card.setShowFace(true);
            // no need to do anything if in discard phase
            if (Turn.CurrentPhase() == TRITI) {
                // only if one of new cards involved
                Turn.setCurrentPhaseTo(DISCARD);
            }
        }
    }

    @Override
    public void execute(List<?> params) {
        DragAndDrop.Payload payload = (DragAndDrop.Payload) params.get(0);
        CardActor cardActor = (CardActor) payload.getDragActor();
        Card card = cardActor.getCard();

        TritiActor tritiActor = (TritiActor)params.get(1);
        Triti triti = tritiActor.getTriti();

        boolean success = triti.addCard(card);

        if (success) {
            GameLogic.getInstance().getCurrentPlayer().removeCard(card);
            card.setShowFace(true);
            // no need to do anything if in discard phase
            if (Turn.CurrentPhase() == TRITI) {
                // only if one of new cards involved
                Turn.setCurrentPhaseTo(DISCARD);
            }
        }


    }

    @Override
    public void undo() {

    }

    @Override
    public boolean allowed() {
        // Allow when triti(picked from discards) and optional case Discard
        return Turn.CurrentPhase() == TRITI || Turn.CurrentPhase() == DISCARD;
    }
}
