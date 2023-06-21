package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

import java.util.ArrayList;
import java.util.List;


public class PlayerHandActor extends HorizontalGroup {
    private List<CardActor> hand;

    public PlayerHandActor(Player player) {
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
