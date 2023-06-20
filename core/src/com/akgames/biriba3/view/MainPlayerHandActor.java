package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.ArrayList;
import java.util.List;


public class MainPlayerHandActor extends HorizontalGroup {
    private List<CardActor> hand;

    public MainPlayerHandActor(Player player, final BoardActor boardActor) {
        hand = new ArrayList<>();
        for(Card card : player.getHand()){
            CardActor cardActor = new CardActor(card);
            hand.add(cardActor);
            addActor(cardActor);
            space(GameOptions.CARD_SIZE[0] * (-0.8f));
        }
    }

    public List<CardActor> getHand() {
        return hand;
    }
}
