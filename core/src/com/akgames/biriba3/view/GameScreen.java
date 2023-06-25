package com.akgames.biriba3.view;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.actions.DealAction;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.ui.GroupParentTable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ibm.gpu.spi.GPUAssist;

import static com.akgames.biriba3.controller.GameOptions.BG_COLOR;

/**
 * Represents the game screen, responsible for rendering the game graphics and handling user input.
 */
public class GameScreen extends ScreenAdapter {
    private final Biriba3 game;
    private final Skin skin;
    private Stage stage;
    private GroupParentTable parentTable;
    private TextButton dealBtn;
    private Table rootTable;
    private Container container;


    public GameScreen(final Biriba3 game) {
        this.game = game;
        this.skin = GameOptions.SKIN;
        dealBtn = new TextButton("Deal Cards", skin);
        dealBtn.addListener(new handleClickDealBtn());
        dealBtn.setColor(BG_COLOR);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(false);

        // Instantiates all the board actor elements. BoardActor -> DeckActor -> CardActors
        this.parentTable = new GroupParentTable(game);
        rootTable = new Table();
        rootTable.setFillParent(false);

        Stack stack = new Stack();
        stack.add(parentTable);
        stack.add(dealBtn);

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        rootTable.add(stack);
        container = new Container(rootTable);
        container.setSize(sw, sh);
        container.pad(50);

//        rootTable.add(stack);
//        stage.addActor(rootTable);
        stage.addActor(container);
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
        game.dispose();
    }

    private class handleClickDealBtn extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            game.getGameLogic().handleAction(new DealAction());
            actor.setVisible(false);
        }
    }
}
