package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Responsible for drawing the card and handling input events
 * Draws the card on screen using libgdx
 */
public class CardActor extends Actor {
    private Card card;
    private Sprite sprite;
    private int[] position;

    public CardActor(Card card) {
        Texture texture = new Texture(
                Gdx.files.internal("assets/cardImgs/" +
                        (card.isShowFace() ? card.getImageUrl() : "back.png")));
        sprite = new Sprite(texture);
        sprite.setSize(100, 150);
        sprite.setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)  {
        sprite.draw(batch);
    }

    public void setPosition(int x, int y) {

    }

    public void handleInput() {

    }
}
