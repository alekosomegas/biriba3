package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;

import java.util.List;

public class EndRound implements PlayerAction{
    @Override
    public void execute() {
        System.out.println("End round");
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }
}
