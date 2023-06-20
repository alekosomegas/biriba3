package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;

import java.util.List;

public class CreateTritiAction implements PlayerAction{
    @Override
    public void execute() {

    }

    @Override
    public void execute(List<?> params) {
        Triti triti = Triti.createTriti((List<Card>) params);
        GameLogic.getInstance().getBoard().addTriti(triti);
    }

    @Override
    public void undo() {

    }
}
