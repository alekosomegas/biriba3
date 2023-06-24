package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Triti;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * One for each team
 */
public class GroupTrites extends Table {
    private List<Triti> trites;
    private List<TritiActor> tritiActors;
    private int team;

    public GroupTrites(int team) {
        this.trites = GameLogic.getInstance().getBoard().getTrites(team);
        this.tritiActors = new ArrayList<>();


        int i = 0;
        for(Triti triti : trites) {
            TritiActor tritiActor = new TritiActor(triti);
            tritiActors.add(tritiActor);
            add(tritiActor).space(5);
            i++;
            if(i % 7 == 0) add().row();
        }
    }

    public List<TritiActor> getTritiActors() {
        return tritiActors;
    }
}
