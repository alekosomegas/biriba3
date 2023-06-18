package com.akgames.biriba3.model;

import java.util.List;

/**
 * Responsible for game logic.
 * Creates the GameOptions
 * Creates the Board.
 */
public class GameLogic {
    private List<Player> players;
    private Board board;
    public GameOptions gameOptions;
    public int currentPlayerIndex;
    // teams?


    // players created by the gameController / setup screen
    public GameLogic() {
        this.gameOptions = new GameOptions();
//        this.players = players;
        // board depends on players
        this.board = new Board();
        this.currentPlayerIndex = 0;
    }

    public Board getBoard() {
        return board;
    }


}
