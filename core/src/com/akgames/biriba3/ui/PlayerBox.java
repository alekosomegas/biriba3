package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameOptions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class PlayerBox extends HorizontalGroup {
    private Label nameLabel;
    private Label cardCountLabel;

    public PlayerBox(String playerName, int cardCount) {
        // Create labels for the player name and card count
        nameLabel = new Label(playerName, GameOptions.SKIN);
        cardCountLabel = new Label("Cards: " + cardCount, GameOptions.SKIN);

        // Add the labels to the widget
        addActor(nameLabel);
        addActor(cardCountLabel);
    }

    public void updateCardCount(int cardCount) {
        // Update the card count label
        cardCountLabel.setText("Cards: " + cardCount);
    }
}
