package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;

import java.util.List;

public interface PlayerAction {


    GameLogic gameLogic = GameLogic.getInstance();

    public void execute();
    public void execute(List<?> params);
    public void undo();
}
