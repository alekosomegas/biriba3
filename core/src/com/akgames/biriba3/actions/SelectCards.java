package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;

import java.util.List;

public class SelectCards implements PlayerAction{
    private boolean active;

    public SelectCards(boolean active) {
        this.active = active;
    }

    @Override
    public void execute() {
        for (Card card : GameLogic.getInstance().getCurrentPlayer().getHand()) {
            card.setClickable(active);
        }
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }
}
