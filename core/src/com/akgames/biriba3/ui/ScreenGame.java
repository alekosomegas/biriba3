package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.events.DealEvent;
import com.akgames.biriba3.events.StartGameEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.akgames.biriba3.controller.GameOptions.BG_COLOR;

/**
 * Responsible for rendering the game graphics.
 */
public class ScreenGame extends ScreenAdapter {
	private final Biriba3 game;
	private final Skin skin = GameOptions.SKIN;
	;
	private final TextButton startBtn;
	private Stage stage;
	private boolean disposed = false;
	
	public ScreenGame(final Biriba3 game) {
		this.game = game;
		startBtn = new TextButton("Start Game", skin);
		startBtn.addListener(new handleClickStartGameBtn());
		startBtn.setColor(BG_COLOR);
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0.2f, 0, 0.2f);
		stage.act();
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		// refresh ui
		show();
	}
	
	@Override
	public void show() {
		if(disposed) return;
		System.out.println("SHOW SCREEN GAME ");
		// Create the stage
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		stage.setDebugAll(false);
		// get screen size
		float sw = Gdx.graphics.getWidth();
		float sh = Gdx.graphics.getHeight();
		// create containers
		Container<Table> rootContainer = new Container<>();
		Table rootTable = new Table();
		Stack stack = new Stack();
		// Instantiates all the board ui elements. BoardActor -> DeckActor -> CardActors
		GameUI gameUI = new GameUI(game);
		// add elements to stage
		stack.add(gameUI);
		stack.add(startBtn);
		rootTable.add(stack);
		rootContainer.setSize(sw, sh);
		rootContainer.pad(50);
		rootContainer.setActor(rootTable);
		
		stage.addActor(rootContainer);
	}
	
	@Override
	public void dispose() {
		this.disposed = true;
		stage.clear();
		stage.dispose();
	}
	
	private class handleClickStartGameBtn extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// Deal Cards
			Match.getController().handleAction(new DealEvent());
			actor.setVisible(false);
		}
	}
}
