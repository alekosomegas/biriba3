package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.view.InputPlayerRow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/*
 * To be shown before the game starts. Collects information in order to set up the game.
 */
public class MainMenuScreen extends ScreenAdapter {
	private final Biriba3 game;
	private final Skin skin;
	private final GameOptions gameOptions;
	private Stage stage;
	
	
	public MainMenuScreen(final Biriba3 game) {
		this.game = game;
		this.gameOptions = game.getGameLogic().gameOptions;
		// This could be a different skin for this screen only
		this.skin = GameOptions.SKIN;
	}
	
	@Override
	public void show() {
		// Create the stage
		stage = new Stage(new FitViewport(900, 900));
		Gdx.input.setInputProcessor(stage);
		// Create the layout of the UI elements
		Table rootTable = new Table();
		rootTable.setFillParent(true);
		// On click Start the game with the selected players.
		Button startButton = new TextButton("Start Game", skin);
		startButton.addListener(new StartButtonClickListener());
		// Add the players input
		rootTable.add(new InputPlayersTable());
		rootTable.row();
		rootTable.add(startButton);
		
		stage.addActor(rootTable);
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		// Draw the UI elements
		stage.act();
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void pause() {
	
	}
	
	@Override
	public void resume() {
	
	}
	
	@Override
	public void hide() {
	
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
	}
	
	private class StartButtonClickListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// gameOptions creates the players and sends them to gameLogic
			gameOptions.createPlayers();
			game.setScreen(game.getGameScreen());
		}
	}
	
	private class InputPlayersTable extends Table {
		public InputPlayersTable() {
			defaults().padBottom(6);
			for(int i = 0; i < 4; i++) {
				InputPlayerRow inputPlayerRow = new InputPlayerRow(i);
				gameOptions.addPropertyChangeListener(inputPlayerRow);
				add(inputPlayerRow);
				row();
			}
		}
	}
	
}
