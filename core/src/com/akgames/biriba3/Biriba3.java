package com.akgames.biriba3;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.view.GameScreen;
import com.akgames.biriba3.view.MainMenuScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

public class Biriba3 extends Game {
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private GameLogic gameLogic;

	public Biriba3() {
		// instantiates all the game components
		// gameLogic -> Board -> Deck -> Cards
		gameLogic = GameLogic.getInstance();
	}
	
	@Override
	public void create () {
		GameOptions.SKIN =  new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		gameLogic.setGameScreen(gameScreen);
		setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
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

	//TODO: no need
	public GameLogic getGameLogic() {
		return gameLogic;
	}

	public void createNewGame() {
		gameLogic = GameLogic.createNewGame();
		create();
		setScreen(mainMenuScreen);
	}
}
