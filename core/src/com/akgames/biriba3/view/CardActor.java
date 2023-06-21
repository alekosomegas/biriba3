package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.controller.GameOptions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Responsible for drawing the card and handling input events
 * Draws the card on screen using libgdx
 */
public class CardActor extends Actor {
    private Card card;
    private TextureRegion cardImage;
    private float width, height;


    public CardActor(final Card card) {
        this.card = card;
        Texture texture = new Texture(
                Gdx.files.internal("assets/cardImgs/" +
                        (card.isShowFace() ? card.getImageUrl() : "back.png")));
        cardImage = new TextureRegion(texture);
        this.width = GameOptions.CARD_SIZE[0];
        this.height = GameOptions.CARD_SIZE[1];
        this.setBounds(getX(), getY(), width, height);


        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(card.isClickable()) {
                    if (card.isSelected()) {
                        GameLogic.getInstance().removeFromSelectedCards(card);
                    } else {
                        GameLogic.getInstance().addToSelectedCards(card);
                    }

                    int m = card.isSelected() ? -1 : 1;
                    moveBy(0, 100 * m);
                    card.setSelected(!card.isSelected());
                }

            }
        });


    }

    @Override
    public void draw(Batch batch, float parentAlpha)  {

        batch.draw(cardImage, getX(), getY(), width, height);
    }

    public void setPosition(int x, int y) {

    }

    public void handleInput() {

    }

    public Card getCard() {
        return card;
    }


}
