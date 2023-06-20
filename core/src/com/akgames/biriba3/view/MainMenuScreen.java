package com.akgames.biriba3.view;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.ui.GroupInputPlayers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen extends ScreenAdapter {
    private final Biriba3 game;
    private GameOptions gameOptions;
    private final Skin skin;
    private Stage stage;
    private Table table;
    private Button startButton;


    public MainMenuScreen(final Biriba3 game) {
        this.game = game;
        this.gameOptions = game.getGameLogic().gameOptions;
        this.skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
    }

    @Override
    public void show() {
        // Create the stage
        stage = new Stage(new FitViewport(900, 900));
        Gdx.input.setInputProcessor(stage);

        // Create the layout of the UI elements
        table = new Table();
        table.setFillParent(true);

        startButton = new TextButton("Start Game", skin);
        startButton.addListener(new StartButtonClickListener());

        GroupInputPlayers groupInputPlayers = new GroupInputPlayers(gameOptions);

        table.add(groupInputPlayers.getTable());
        table.row();
        table.add(startButton);

        stage.addActor(table);

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
            // Start the game with the selected players.
            // gameOptions creates the players and sends them to gameLogic
            gameOptions.createPlayers();
            game.setScreen(game.getGameScreen());
        }
    }

}
