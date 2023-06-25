package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE_SM;

/*
 * Displays triti. A group creating and holding all the CardActors of the triti.
 */
public class TritiActor extends VerticalGroup {
	private final Triti triti;
	
	public TritiActor(Triti triti) {
		this.triti = triti;
		
		// Create a new CardActor for each card in triti and add space.
		for(Card card : triti.getCards()) {
			addActor(new CardActor(card, CARD_SIZE_SM[0], CARD_SIZE_SM[1]));
			space(CARD_SIZE_SM[1] * 0.15f - CARD_SIZE_SM[1]);
		}
	}
	
	public Triti getTriti() {
		return triti;
	}
}
