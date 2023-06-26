package com.akgames.biriba3;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.ui.GameScreen;
import com.akgames.biriba3.ui.MainMenuScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

public class Biriba3 extends Game {
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private GameController gameLogic;
	
	public Biriba3() {
		// instantiates all the game components
		// gameLogic -> Board -> Deck -> Cards
		gameLogic = GameController.getInstance();
	}
	
	public void createNewGame() {
		gameLogic = GameController.createNewGame();
		create();
		setScreen(mainMenuScreen);
	}
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		GameOptions.SKIN = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		gameLogic.setGameScreen(gameScreen);
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
		mainMenuScreen.dispose();
		gameScreen.dispose();
	}
	
	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	// TODO: no need
	public GameController getGameLogic() {
		return gameLogic;
	}
	
	
}
