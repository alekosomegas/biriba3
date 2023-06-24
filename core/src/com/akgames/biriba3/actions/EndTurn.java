package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.*;

public class EndTurn implements PlayerAction{


    public EndTurn() {
    }

    @Override
    public void execute() {
        Gdx.app.log(this.toString(), "Executing End Turn. " +
                "Turn phase : " + Turn.CurrentPhase() +
                 "\nCurrent player: " + GameLogic.getInstance().getCurrentPlayer().getName());
        // start next turn before executing
        Turn.nextPhase();

        // Next player turn starts
        Gdx.app.log(this.toString(), "\nSTARTS NEW TURN\n");
        GameLogic.getInstance().nextPlayer();
        GameLogic.getInstance().getCurrentPlayer().act();


        if(GameLogic.getInstance().currentPlayerIndex == 0) {
            GameLogic.getInstance().handleAction(new EndRound());
        }

        Gdx.app.log(this.toString(), "\n---------------------- END TURN\n");

    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }

    @Override
    public boolean allowed() {
        return Turn.CurrentPhase() == END;
    }
}
