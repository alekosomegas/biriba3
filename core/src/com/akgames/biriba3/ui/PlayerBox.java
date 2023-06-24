package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.view.PlayerHandActor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class PlayerBox extends HorizontalGroup {
    private Label nameLabel;
    private Label cardCountLabel;
    private PlayerHandActor playerHandActor;

    public PlayerBox(Player player) {
        // Create labels for the player name and card count
        nameLabel = new Label(player.getName(), GameOptions.SKIN);
        cardCountLabel = new Label("Cards: " + player.getCardCount(), GameOptions.SKIN);
        this.playerHandActor = new PlayerHandActor(player);

        // Add the labels to the widget
        addActor(nameLabel);
        addActor(cardCountLabel);
        addActor(playerHandActor);
    }

    public void updateCardCount(int cardCount) {
        // Update the card count label
        cardCountLabel.setText("Cards: " + cardCount);
    }
}
