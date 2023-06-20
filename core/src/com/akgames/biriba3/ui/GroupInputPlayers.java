package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameOptions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GroupInputPlayers extends Group {
    InputPlayer p1;
    InputPlayer p2;
    InputPlayer p3;
    InputPlayer p4;
    GameOptions gameOptions;
    private Table table;
    private Skin skin;

    //TODO: perhaps make game available as a static
    //TODO: USE OBSERVER PATTERN
    public GroupInputPlayers(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
        this.skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        table = new Table();

        p1 = new InputPlayer(gameOptions, 0);
        p2 = new InputPlayer(gameOptions, 1);
        p3 = new InputPlayer(gameOptions, 2);
        p4 = new InputPlayer(gameOptions, 3);

        gameOptions.addPropertyChangeListener(p1);
        gameOptions.addPropertyChangeListener(p2);
        gameOptions.addPropertyChangeListener(p3);
        gameOptions.addPropertyChangeListener(p4);

        table.add(p1.getTable()).padBottom(6);
        table.row();
        table.add(p2.getTable()).padBottom(6);
        table.row();
        table.add(p3.getTable()).padBottom(6);
        table.row();
        table.add(p4.getTable()).padBottom(6);
        table.row();
    }
    public Table getTable() {
        return table;
    }

    //Refactor: Use update method to update ui when any event fires
    private void update() {

    }
}
