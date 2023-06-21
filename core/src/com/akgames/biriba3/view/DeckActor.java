package com.akgames.biriba3.view;

import com.akgames.biriba3.actions.PickFromDeck;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Deck;
import com.akgames.biriba3.controller.GameOptions;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for creating all the cardActors(cards taken from Deck)
 */
public class DeckActor extends Actor {
    private GameLogic gameLogic;
    private Deck deck;
    private List<CardActor> cardActors;
    private Sprite sprite;
    private TextureRegion cardImage;
    private float width, height;


    public DeckActor(Deck deck) {
        this.gameLogic = GameLogic.getInstance();
        this.deck = deck;
        cardActors = new ArrayList<>();

        //TODO: change this to Texture region
        Texture texture = new Texture(
                Gdx.files.internal("assets/cardImgs/back90.png"));

        cardImage = new TextureRegion(texture);
        this.width = GameOptions.CARD_SIZE[1];
        this.height = GameOptions.CARD_SIZE[0];
        this.setBounds(getX(), getY(), width, height);


        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Clicked", "Deck was clicked at position (" + x + ", " + y + ")");
                gameLogic.handleAction(new PickFromDeck());
            }
        });


//        loadCardActors();

    }

    @Override
    public void draw(Batch batch, float parentAlpha)  {
//        sprite.draw(batch);
        batch.draw(cardImage, getX(), getY(), width, height);
    }

    private void loadCardActors() {
        for (Card card : deck.getCards()) {
            cardActors.add(new CardActor(card));
        }
    }

}
