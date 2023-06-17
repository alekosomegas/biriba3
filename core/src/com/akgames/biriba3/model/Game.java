package com.akgames.biriba3.model;

import java.util.List;

/**
 * Responsible for game logic
 */
public class Game {
    private List<Player> players;
    private Board board;
    public int currentPlayerIndex;
    // teams?


    // players created by the gameController / setup screen
    public Game(List<Player> players) {
        this.players = players;
        // board depends on players
        this.board = new Board();
        this.currentPlayerIndex = 0;
    }
}
