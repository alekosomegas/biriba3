package com.akgames.biriba3.controller;

import com.akgames.biriba3.events.GameEvent;
import com.akgames.biriba3.events.GameOverEvent;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.ui.GameScreen;
import com.akgames.biriba3.view.PlayerHandActor;
import com.badlogic.gdx.Gdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.akgames.biriba3.controller.Turn.TurnPhases.BIRIBAKI_END;
import static com.akgames.biriba3.controller.Turn.TurnPhases.BIRIBAKI_PLAY;

/**
 * Responsible for game logic. Creates the GameOptions Creates the Board.
 */
public class GameController implements PropertyChangeListener {
	private static GameController instance;
	public GameOptions gameOptions;
	public int currentPlayerIndex;
	private List<GameEvent> playerActionsQueue;
	private List<Player> players;
	private Board board;
	private GameScreen gameScreen;
	private PropertyChangeSupport support;
	private boolean gameOver;
	private int numOfTeams;
	private PlayerHandActor mainPlayerHandActor;
	private List<Card> selectedCards;
	private boolean currentPlayerHasThrownCard;
	
	// players created by the gameController / setup screen
	private GameController() {
		this.gameOptions = new GameOptions(this);
		this.playerActionsQueue = new ArrayList<>();
		this.currentPlayerIndex = 0;
		this.gameOver = false;
		this.selectedCards = new ArrayList<>();
		this.currentPlayerHasThrownCard = false;
		this.support = new PropertyChangeSupport(this);
		
	}
	
	public static GameController getInstance() {
		if(instance == null) {
			instance = new GameController();
		}
		return instance;
	}
	
	public static GameController createNewGame() {
		instance = new GameController();
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
		// Add this as listener for each player. Listens for empty hand
		for(Player player : players) {
			player.addPropertyChangeListener(this);
		}
		this.board = new Board(numOfTeams);
		board.getDeck().addPropertyChangeListener(this);
	}
	
	public int getNumOfTeams() {
		return numOfTeams;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getMainPlayer() {
		return players.get(0);
	}
	
	public int getNumOfPlayers() {
		return players.size();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}
	
	public PlayerHandActor getMainPlayerHandActor() {
		return mainPlayerHandActor;
	}
	
	public void setMainPlayerHandActor(PlayerHandActor mainPlayerHandActor) {
		this.mainPlayerHandActor = mainPlayerHandActor;
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
	
	public void setCurrentPlayerHasThrownCard(boolean currentPlayerHasThrownCard) {
		this.currentPlayerHasThrownCard = currentPlayerHasThrownCard;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(Objects.equals(evt.getPropertyName(), "Empty Hand")) {
			if(getCurrentPlayer().hasTakenBiribaki()) {
				handleAction(new GameOverEvent());
				return;
			}
			if(currentPlayerHasThrownCard) {
				Turn.setCurrentPhaseTo(BIRIBAKI_END);
			} else {
				Turn.setCurrentPhaseTo(BIRIBAKI_PLAY);
			}
		}
		
		// End the game if only 2 cards left in deck
		// TODO: after the current turn finishes
		if(Objects.equals(evt.getPropertyName(), "Deck Size Changed")) {
			if(Objects.equals(evt.getNewValue(), 2)) {
				handleAction(new GameOverEvent());
			}
		}
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
	
	public void clearPlayerActionsQueue() {
		this.playerActionsQueue.clear();
	}
	
	public void handleAction(GameEvent gameEvent) {
		Gdx.app.log(this.getClass().getName(), "\n\tCurrent Player: " + getCurrentPlayer().getName() + "\n\tTurn Phase: " + Turn.CurrentPhase() + " \n\tAction: " + gameEvent.getClass().getName());
		if(!gameEvent.allowed()) return;
		Gdx.app.debug(getClass().getName(), "Action allowed, entering execution...");
		playerActionsQueue.add(gameEvent);
		gameEvent.execute();
		// refresh screen
		gameScreen.show();
	}
	
	public void undo() {
		if(playerActionsQueue.size() == 0) return;
		GameEvent lastEvent = playerActionsQueue.remove(playerActionsQueue.size() - 1);
		Gdx.app.debug(getClass().getName(), "Undo: " + lastEvent.getClass().getName());
		// clear the queue if reached an undoable action
		if(!lastEvent.undo()) {
			playerActionsQueue.clear();
		}
		// refresh screen
		gameScreen.show();
	}
}
