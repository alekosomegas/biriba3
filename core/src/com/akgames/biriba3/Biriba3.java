package com.akgames.biriba3;

import com.akgames.biriba3.view.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Biriba3 extends Game {
	private MainMenuScreen setupScreen;
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		setupScreen = new MainMenuScreen(this);
		// new gameController, setScreen gameController.currentScreen
		setScreen(setupScreen);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		// Render the current screen
		super.render();
	}
	
	@Override
	public void dispose () {
		// Dispose of resources when the game exits
		setupScreen.dispose();
	}
}
