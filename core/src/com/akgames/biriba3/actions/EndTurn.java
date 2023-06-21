package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;

import java.util.List;

public class EndTurn implements PlayerAction{
    @Override
    public void execute() {
        GameLogic.getInstance().handleAction(new SelectCards(false));
        GameLogic.getInstance().nextPlayer();

        GameLogic.getInstance().getCurrentPlayer().act();

        if(GameLogic.getInstance().currentPlayerIndex == 0) {
            GameLogic.getInstance().handleAction(new EndRound());
        }
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }
}
