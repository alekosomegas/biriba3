package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class TritiActor extends VerticalGroup {
    private Triti triti;

    public TritiActor(Triti triti) {
        this.triti = triti;

        for(Card card : triti.getCards()) {
            addActor(new CardActor(card));
            space(GameOptions.CARD_SIZE[1] * 0.15f - GameOptions.CARD_SIZE[1]);
        }
    }

    public Triti getTriti() {
        return triti;
    }
}
