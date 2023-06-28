package com.akgames.biriba3;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.ui.ScreenGame;
import com.akgames.biriba3.ui.ScreenGameOver;
import com.akgames.biriba3.ui.ScreenMainMenu;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
// TODO: wrong player turn in new game
// TODO: has biribaki for team, not player
// TODO: player score to be round score for team, not total score

public class Biriba3 extends Game {
	private ScreenMainMenu mainMenuScreen;
	private ScreenGame gameScreen;
	private ScreenGameOver gameOverScreen;
	private GameController gameController;
	private Match match;
	public Biriba3() {
		// instantiates all the game components
		// gameLogic -> Board -> Deck -> Cards
//		gameController = Match.getController();
		GameOptions gameOptions = new GameOptions();
		this.match = new Match(this, gameOptions);
		gameController = Match.getController();
	}
	
	public void createNewGame() {
//		gameController = GameController.createNewGame();
		create();
		setScreen(mainMenuScreen);
	}
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		GameOptions.SKIN = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
		mainMenuScreen = new ScreenMainMenu(this);
		gameScreen = new ScreenGame(this);
		gameOverScreen = new ScreenGameOver(this);
		setScreen(mainMenuScreen);
	}
	
	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();
	}
	
	@Override
	public void dispose() {
		// Dispose of resources when the game exits

	}
	
	public ScreenGame getGameScreen() {
		return gameScreen;
	}
	
	public void showGameScreen() {
		gameScreen = new ScreenGame(this);
		gameController.setGameScreen(gameScreen);
		setScreen(gameScreen);
	}
	
	public void showGameOverScreen() {
		gameScreen.dispose();
		setScreen(gameOverScreen);
	}
}
