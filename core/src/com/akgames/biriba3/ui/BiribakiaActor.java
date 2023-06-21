package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Deck;
import com.akgames.biriba3.view.CardActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import java.util.List;



public class BiribakiaActor extends Stack {
    private Image cardImage1;
    private Image cardImage2;


    public BiribakiaActor() {
        Texture texture = new Texture(
                Gdx.files.internal("assets/cardImgs/back90.png"));
        cardImage1 = new Image(new TextureRegion(texture));
        cardImage2 = new Image(new TextureRegion(new Texture(Gdx.files.internal("assets/cardImgs/back.png"))));

//        addActor(cardImage1);
        addActor(cardImage2);
    }

}
