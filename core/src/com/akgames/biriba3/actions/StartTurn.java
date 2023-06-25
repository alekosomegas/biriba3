package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.Turn;

import java.util.List;

// TODO: start -> new start Turn -> Deal cards

public class StartTurn implements PlayerAction{
    Turn turnPhase;

    public StartTurn() {
        turnPhase = Turn.getInstance();
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }

    @Override
    public boolean allowed() {
        return true;
    }
}
