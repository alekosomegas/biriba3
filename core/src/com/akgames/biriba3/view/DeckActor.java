package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.events.PickFromDeckEvent;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Deck;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Responsible for displaying the deck and for user interaction with it.
 */
public class DeckActor extends Actor {
	private final GameController gameLogic;
	private final TextureRegion cardImage;
	private final float width, height;
	
	
	public DeckActor(Deck deck) {
		this.gameLogic = Match.getController();
		this.width = GameOptions.CARD_SIZE[1];
		this.height = GameOptions.CARD_SIZE[0];
		// Create the image of the back of a card
		Texture texture = new Texture(Gdx.files.internal("cardImgs/back90.png"));
		cardImage = new TextureRegion(texture);
		// Set the clickable bounds
		this.setBounds(getX(), getY(), width, height);
		// Notify the controller
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameLogic.handleAction(new PickFromDeckEvent());
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(cardImage, getX(), getY(), width, height);
	}
	
	
}
