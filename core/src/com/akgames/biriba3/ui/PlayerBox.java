package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.view.PlayerHandActor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerBox extends HorizontalGroup {
    private Label nameLabel;
    private Label cardCountLabel;
    private PlayerHandActor playerHandActor;
    private float width, height;

    public PlayerBox(Player player) {
        // Create labels for the player name and card count
        Table infoTable = new Table();
        nameLabel = new Label(player.getName(), GameOptions.SKIN);
        cardCountLabel = new Label("Cards: " + player.getCardCount(), GameOptions.SKIN);
        this.playerHandActor = new PlayerHandActor(player);

        // Add the labels to the widget
        infoTable.add(nameLabel);
        infoTable.row();
        infoTable.add(cardCountLabel);

        space(20);
        addActor(infoTable);
        addActor(playerHandActor);
        this.width = playerHandActor.getWidth();
        this.height = playerHandActor.getHeight();
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    // Instead of creating a new object?
    public void updateCardCount(int cardCount) {
        // Update the card count label
        cardCountLabel.setText("Cards: " + cardCount);
    }
}
