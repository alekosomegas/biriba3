package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;

import java.util.List;

// TODO: rename
// TODO: perhaps no need to overload. can use constructor
public interface PlayerAction {

    GameLogic gamelogic = GameLogic.getInstance();

    public void execute();
    public void execute(List<?> params);
    public void undo();

    boolean allowed();
}
