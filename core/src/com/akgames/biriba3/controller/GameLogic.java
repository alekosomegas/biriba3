package com.akgames.biriba3.controller;

import com.akgames.biriba3.actions.PlayerAction;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.view.GameScreen;
import com.akgames.biriba3.view.PlayerHandActor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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
    private boolean gameOver;
    private int numOfTeams;
    private PlayerHandActor mainPlayerHandActor;
    private boolean selectCardsActive;
    private List<Card> selectedCards;


    // players created by the gameController / setup screen
    private GameLogic() {
        this.gameOptions = new GameOptions(this);
        this.playerActionsQueue = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        this.selectedCards = new ArrayList<>();
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

    public void setUpGame(List<Player> players) {
        this.numOfTeams = players.size() == 3 ? 3 : 2;
        this.players = players;
        this.board = new Board(numOfTeams);
    }

    public int getNumOfTeams() {
        return numOfTeams;
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

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex +1) % players.size();
    }


    public void setMainPlayerHandActor(PlayerHandActor mainPlayerHandActor) {
        this.mainPlayerHandActor = mainPlayerHandActor;
    }

    public PlayerHandActor getMainPlayerHandActor() {
        return mainPlayerHandActor;
    }

    public boolean isSelectCardsActive() {
        return selectCardsActive;
    }

    public void setSelectCardsActive(boolean selectCardsActive) {
        this.selectCardsActive = selectCardsActive;
    }

    public void refreshUi() {
        gameScreen.show();
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public void addToSelectedCards(Card selectedCard) {
        this.selectedCards.add(selectedCard);
    }
    public void removeFromSelectedCards(Card card) {
        this.selectedCards.remove(card);
    }
}
