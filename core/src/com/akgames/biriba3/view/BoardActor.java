package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Board;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Responsible for creating the DeckActor(Deck taken from Board)
 */
public class BoardActor extends Group {
    private Board board;

    private DeckActor deckActor;
    public BoardActor(Board board) {
        this.board = board;
        deckActor = new DeckActor(board.getDeck());

        deckActor.setPosition(50, 250);
        addActor(deckActor);
    }
}
