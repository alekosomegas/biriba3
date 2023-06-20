package com.akgames.biriba3.ui;

import com.akgames.biriba3.actions.PickDiscards;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.view.CardActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.ibm.j9ddr.vm29.structure.CardAction;

import java.util.ArrayList;
import java.util.List;

public class DiscardsPileActor extends Stack {
    private List<Card> discardPile;
    private List<HorizontalGroup> horizontalGroupList;

    private Image backgroundImage;
    private VerticalGroup cardRows;


    public DiscardsPileActor() {
        this.discardPile = GameLogic.getInstance().getBoard().getDiscardPile();
        this.horizontalGroupList = new ArrayList<>();
        horizontalGroupList.add(new HorizontalGroup());
        this.cardRows = new VerticalGroup();
        int currentRowIndex = 0;

        for (Card card : discardPile) {
            //TODO:this removes it from its previous parent. Perhaps no need to refresh stage?
            horizontalGroupList.get(currentRowIndex).addActor(new CardActor(card));
            horizontalGroupList.get(currentRowIndex).space(GameOptions.CARD_SIZE[0] * (-0.8f));
            if(horizontalGroupList.get(currentRowIndex).getChildren().size > 6) {
                horizontalGroupList.add(new HorizontalGroup());
                currentRowIndex = currentRowIndex + 1;
            }
        }

        for (HorizontalGroup hg : horizontalGroupList) {
            cardRows.addActor(hg);
        }

        cardRows.space(4);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameLogic.getInstance().handleAction(new PickDiscards());
            }
        });


        // Create a new background image
        TextureRegion backgroundRegion = new TextureRegion(new Texture("badlogic.jpg"));
        backgroundImage = new Image(backgroundRegion);


        // Add the background image to the group
        addActor(backgroundImage);
        addActor(cardRows);



    }


}
