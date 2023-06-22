package com.akgames.biriba3.ui;

import com.akgames.biriba3.actions.TakeBiribaki;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Deck;
import com.akgames.biriba3.view.CardActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;



public class BiribakiaActor extends Stack {
    private Image cardImage1;
    private Image cardImage2;
    private int numBiribakia;


    public BiribakiaActor() {
        this.numBiribakia = GameLogic.getInstance().getBoard().getNumBiribakia();

        Texture texture = new Texture(
                Gdx.files.internal("assets/cardImgs/back90.png"));
        cardImage1 = new Image(new TextureRegion(texture));
        cardImage2 = new Image(new TextureRegion(new Texture(Gdx.files.internal("assets/cardImgs/biribaki.png"))));

        if (numBiribakia == 2) {
            addActor(cardImage2);
        } else if (numBiribakia == 1){
            addActor(cardImage1);
        } else {

        }


        addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent e, float x, float y) {
                GameLogic.getInstance().handleAction(new TakeBiribaki());
                Gdx.app.log(this.toString(), "clicked");

            }

        });

    }



}
