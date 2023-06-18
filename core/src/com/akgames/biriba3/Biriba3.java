package com.akgames.biriba3;

import com.akgames.biriba3.model.GameLogic;
import com.akgames.biriba3.view.GameScreen;
import com.akgames.biriba3.view.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Biriba3 extends Game {
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private GameLogic gameLogic;

	public Biriba3() {
		// instantiates all the game components
		// gameLogic -> Board -> Deck -> Cards
		gameLogic = new GameLogic();
	}
	
	@Override
	public void create () {
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
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

	public GameLogic getGameLogic() {
		return gameLogic;
	}
}
