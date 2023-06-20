package com.akgames.biriba3.actions;

import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Deck;
import com.akgames.biriba3.model.Player;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.GameOptions.NUM_CARDS_PER_PLAYER;

public class DealAction implements PlayerAction {
    private Board board = gameLogic.getBoard();
    private Deck deck = gameLogic.getBoard().getDeck();
    private List<Player> players = gameLogic.getPlayers();

    @Override
    public void execute() {
        // Deal cards to all Players
        for (Player player : players) {
            for (int i=0; i < NUM_CARDS_PER_PLAYER; i++) {
                player.addToHand(deck.getTopCard().turn());
            }
        }

        board.addToDiscardPile(deck.getTopCard().turn());

        List<Card> test = new ArrayList<>();
        test.add(deck.getTopCard().turn());
        test.add(deck.getTopCard().turn());
        test.add(deck.getTopCard().turn());
        gameLogic.handleAction(new CreateTritiAction(), test);

    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }
}
