package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.events.StartGameEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.akgames.biriba3.controller.GameOptions.SKIN;

public class ScreenGameOver extends ScreenAdapter {
	private final Biriba3 game;
	private Stage stage;
	private GameController gameController;
	
	public ScreenGameOver(final Biriba3 game) {
		this.game = game;
		gameController = Match.getController();
	}
	
	@Override
	public void show() {
		// Create the stage
		stage = new Stage((new FitViewport(900, 900)));
		Gdx.input.setInputProcessor(stage);
		// Create the layout of the UI elements
		Table rootTable = new Table();
		rootTable.setFillParent(true);
		rootTable.setDebug(false);
		Table infoTable = new Table();
		infoTable.defaults().space(10);
		infoTable.add();
		Label label;
		for(int i = 1; i < gameController.getNumOfTeams() + 1; i++) {
			label = new Label("Team " + i, SKIN);
			infoTable.add(label);
		}
		for(int roundNum = 0; roundNum < Match.getNumRounds(); roundNum++) {
			infoTable.row();
			label = new Label("Round " + String.valueOf(roundNum+1), SKIN);
			infoTable.add(label);
			// add teams score
			for(int teamIndex = 0; teamIndex < Match.getNumTeams(); teamIndex++) {
				label = new Label(String.valueOf(Match.getScoreForTeam(teamIndex, roundNum)), SKIN);
				infoTable.add(label);
			}
		}
		label = new Label("Total", SKIN);
		infoTable.row();
		infoTable.add(label);
		for(int teamIndex = 0; teamIndex < gameController.getNumOfTeams(); teamIndex++) {
			infoTable.add(new Label(String.valueOf(Match.getTotalScore(teamIndex)), SKIN));
		}
		infoTable.row();
		label = new Label("Points needed", SKIN);
		infoTable.add(label);
		for(int l = 0; l < gameController.getNumOfTeams(); l++) {
			infoTable.add(new Label(String.valueOf(GameOptions.pointsToWin - Match.getTotalScore(l)), SKIN));
		}
		
		rootTable.add(infoTable);
		
		rootTable.row();
		label = new Label("Total points to win: " + GameOptions.pointsToWin, SKIN);
		rootTable.add(label);
		
		TextButton startGameBtn = new TextButton("Play Next Round", SKIN);
		startGameBtn.addListener(new StartButtonClickListener());
		
		rootTable.row();
		rootTable.add(startGameBtn);
		
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
	public void dispose() {
		stage.dispose();
	}
	
	private class StartButtonClickListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			game.showGameScreen();
			new StartGameEvent().execute();
		}
	}
}
