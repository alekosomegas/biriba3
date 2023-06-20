package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;

import java.util.List;

public class PickDiscards implements PlayerAction{
    @Override
    public void execute() {
        // TODO: refactor
        GameLogic.getInstance().getCurrentPlayer().addToHand( GameLogic.getInstance().getBoard().getDiscardPile());
        GameLogic.getInstance().getBoard().getDiscardPile().clear();
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }
}
