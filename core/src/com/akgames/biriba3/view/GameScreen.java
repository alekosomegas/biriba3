package com.akgames.biriba3.view;

import com.akgames.biriba3.Biriba3;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Represents the game screen, responsible for rendering the game graphics and handling user input.
 */
public class GameScreen extends ScreenAdapter {
    private final Biriba3 game;
    private final Skin skin;
    private Stage stage;



    public GameScreen(final Biriba3 game) {
        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
    }

    @Override
    public void show() {
//        CardActor card = new CardActor(1);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Instantiates all the board actor elements. BoardActor -> DeckActor -> CardActors
        //TODO: find a better way
        BoardActor boardActor = new BoardActor(game.getGameLogic().getBoard());

//        stage.addActor(card);
//        stage.addActor(deckActor);
        stage.addActor(boardActor);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 0, 1);
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
}
