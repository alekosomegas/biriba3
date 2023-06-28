package com.akgames.biriba3.ui;

import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.view.PlayerHandActor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.akgames.biriba3.controller.GameOptions.SKIN;

/**
 * Displays the Players' hand with some additional information.
 */
public class PlayerBox extends HorizontalGroup {
	private final float width, height;
	private PlayerHandActor playerHandActor;
	
	public PlayerBox(Player player) {
		this.playerHandActor = new PlayerHandActor(player);
		// Create labels for the player name and card count
		Label nameLabel = new Label(player.getName(), SKIN);
		Label cardCountLabel = new Label("Cards: " + player.getCardCount(), SKIN);
		Label scoreLbl = new Label("Score: " + player.getTeam().getRoundsScore(), SKIN);
		// add information to the table
		Table infoTable = new Table();
		infoTable.add(nameLabel);
		infoTable.row();
		infoTable.add(scoreLbl);
		infoTable.row();
		infoTable.add(cardCountLabel);
		// Add the tables to the widget
		space(20);
		addActor(infoTable);
		addActor(playerHandActor);
		// set the size so it can be centered
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
	
	public PlayerHandActor getPlayerHandActor() {
		return playerHandActor;
	}
}
