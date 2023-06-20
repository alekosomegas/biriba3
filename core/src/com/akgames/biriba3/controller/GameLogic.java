package com.akgames.biriba3.controller;

import com.akgames.biriba3.actions.PlayerAction;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.view.GameScreen;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Responsible for game logic.
 * Creates the GameOptions
 * Creates the Board.
 */
public class GameLogic {
    private static GameLogic instance;
    private List<PlayerAction> playerActionsQueue;

    private List<Player> players;
    private Board board;
    public GameOptions gameOptions;
    public int currentPlayerIndex;
    private GameScreen gameScreen;
    private PropertyChangeSupport support;
    // teams?


    // players created by the gameController / setup screen
    private GameLogic() {
        this.gameOptions = new GameOptions(this);
        this.playerActionsQueue = new ArrayList<>();
        this.board = new Board();
        this.currentPlayerIndex = 0;
        support = new PropertyChangeSupport(this);
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public static GameLogic createNewGame() {
        instance = new GameLogic();
        return instance;
    }
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Board getBoard() {
        return board;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getNumOfPlayers() {
        return players.size();
    }

    public void handleAction(PlayerAction playerAction) {
        playerActionsQueue.add(playerAction);
        playerAction.execute();
        // refresh screen
        gameScreen.show();
    }
    public void handleAction(PlayerAction playerAction, List<?> params) {
        playerActionsQueue.add(playerAction);
        playerAction.execute(params);
        // refresh screen
        gameScreen.show();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}
