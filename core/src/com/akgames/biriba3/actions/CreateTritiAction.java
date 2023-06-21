package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.model.Triti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateTritiAction implements PlayerAction{
    private GameLogic gameLogic;
    private List<Card> selectedCards;
    private List<Card> hand;
    private int teamNum;
    private Board board;

    public CreateTritiAction() {
        this.gameLogic = GameLogic.getInstance();
        this.selectedCards = gameLogic.getSelectedCards();
        this.hand = gameLogic.getCurrentPlayer().getHand();
        this.board = gameLogic.getBoard();

    }

    @Override
    public void execute() {
        for(Card card : selectedCards) {
            card.setSelected(false);
        }
        Triti triti = Triti.createTriti(new ArrayList<>(selectedCards));
        if(triti != null) {
            hand.removeAll(selectedCards);
            selectedCards.clear();
            board.addTriti(triti);
        }
    }

    @Override
    public void execute(List<?> params) {
//        Triti triti = Triti.createTriti((List<Card>) params);
//        GameLogic.getInstance().getBoard().addTriti(triti, 0);
    }

    @Override
    public void undo() {

    }
}
