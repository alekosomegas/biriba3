package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.model.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Responsible for drawing the cards and handling input events
 */
public class CardActor extends Actor {
	private final Card card;
	private final TextureRegion cardImage;
	private final float width, height;
	
	public CardActor(final Card card, float width, float height) {
		this.card = card;
		this.width = width;
		this.height = height;
		// load image of card or its back
		Texture texture = new Texture(Gdx.files.internal("cardImgs/" + (card.isShowFace() ? card.getImageUrl() : "back.png")));
		this.cardImage = new TextureRegion(texture);
		// TODO: update when card is moved(selected) so it can be clicked
		// Set clickable bounds
		this.setBounds(getX(), getY(), width, height);
		// TODO: pass event to controller, it should decide what to do when the card is clicked
		// On right click select the card
		this.addListener(new ClickListener(Input.Buttons.RIGHT) {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(card.isClickable()) {
					if(card.isSelected()) {
						Match.getController().removeFromSelectedCards(card);
					} else {
						Match.getController().addToSelectedCards(card);
					}
					card.setSelected(!card.isSelected());
				}
			}
		});
		
		// TODO: scale on hover
		this.addListener(new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			}
		});
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		float offset = card.isSelected() ? 100 : 0;
		batch.draw(cardImage, getX(), getY() + offset, width, height);
	}
	
	public Card getCard() {
		return card;
	}
	
	
}
