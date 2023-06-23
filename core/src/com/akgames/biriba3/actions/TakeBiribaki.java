package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;

import java.util.List;

public class TakeBiribaki implements PlayerAction {

    @Override
    public void execute() {
        List<Card> biribaki = GameLogic.getInstance().getBoard().getBiribaki();
        GameLogic.getInstance().getCurrentPlayer().addToHand(biribaki);
        if(GameLogic.getInstance().getPlayers().indexOf(GameLogic.getInstance().getCurrentPlayer()) == 0) {
            for (Card card : biribaki) {
                card.turn();
                card.setClickable(true);
            }
        }
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }
}
