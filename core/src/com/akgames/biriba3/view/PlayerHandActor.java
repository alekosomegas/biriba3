package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE_LG;
import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE_SM;


public class PlayerHandActor extends HorizontalGroup {
    private List<CardActor> hand;
    private float width, height;

    public PlayerHandActor(Player player) {
        hand = new ArrayList<>();
        float w = CARD_SIZE_SM[0];
        float h = CARD_SIZE_SM[1];
        float d = w * -0.9f;

        for(Card card : player.getHand()){
            if (GameLogic.getInstance().getPlayers().indexOf(player) == 0) {
                w = CARD_SIZE_LG[0];
                h = CARD_SIZE_LG[1];
                d = w * -0.8f;
            }
            CardActor cardActor = new CardActor(card, w, h);
            hand.add(cardActor);
            addActor(cardActor);
            space(d);
        }

        this.width = player.getCardCount() * w * 0.2f + w *0.8f;
        this.height = h;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public List<CardActor> getHand() {
        return hand;
    }
}
