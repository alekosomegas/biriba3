package com.akgames.biriba3.view;

import com.akgames.biriba3.actions.TakeBiribaki;
import com.akgames.biriba3.controller.GameController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Responsible for displaying the deck and for user interaction with it.
 */
public class BiribakiaActor extends Stack {
	
	public BiribakiaActor() {
		int numBiribakia = GameController.getInstance().getBoard().getNumBiribakia();
		// Load images
		Texture texture = new Texture(Gdx.files.internal("assets/cardImgs/back90.png"));
		Image cardImage1 = new Image(new TextureRegion(texture));
		Image cardImage2 = new Image(new TextureRegion(new Texture(Gdx.files.internal("assets/cardImgs/biribaki.png"))));
		// decide which image should be displayed. Display nothing if both are taken
		if(numBiribakia == 2) {
			addActor(cardImage2);
		} else if(numBiribakia == 1) {
			addActor(cardImage1);
		}
		// Notify the controller
		addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent e, float x, float y) {
				GameController.getInstance().handleAction(new TakeBiribaki());
			}
			
		});
		
	}
	
	
}
