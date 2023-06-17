package com.akgames.biriba3.view;

import com.akgames.biriba3.Biriba3;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the game screen, responsible for rendering the game graphics and handling user input.
 */
public class GameScreen implements Screen {
    final private Biriba3 game;

    public GameScreen(final Biriba3 game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 0, 1);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
