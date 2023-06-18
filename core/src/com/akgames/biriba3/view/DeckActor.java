package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Deck;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

/**
 * Responsible for creating all the cardActors(cards taken from Deck)
 */
public class DeckActor extends Actor {
    private Deck deck;
    private List<CardActor> cardActors;
    private Sprite sprite;
    private int[] position;

    public DeckActor(Deck deck) {
        this.deck = deck;

        Texture texture = new Texture(
                Gdx.files.internal("assets/cardImgs/back.png"));
        sprite = new Sprite(texture);
        sprite.setSize(100, 150);
        sprite.setPosition(200, 0);

        loadCardActors();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)  {
        sprite.draw(batch);
    }

    private void loadCardActors() {
        for (Card card : deck.getCards()) {
            cardActors.add(new CardActor(card));
        }
    }

}
