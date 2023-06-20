package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Triti;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupTrites extends VerticalGroup {
    private List<HorizontalGroup> horizontalGroupList;
    private List<Triti> trites;
    private List<TritiActor> tritiActors;

    public GroupTrites() {
        this.trites = GameLogic.getInstance().getBoard().getTrites();
        this.tritiActors = new ArrayList<>();

        for(Triti triti : trites) {
            TritiActor tritiActor = new TritiActor(triti);
            tritiActors.add(tritiActor);
            addActor(tritiActor);
        }
    }

    public List<TritiActor> getTritiActors() {
        return tritiActors;
    }
}
