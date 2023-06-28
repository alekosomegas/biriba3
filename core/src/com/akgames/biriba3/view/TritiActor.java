package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE_SM;
import static com.akgames.biriba3.controller.GameOptions.SKIN;

/*
 * Displays triti. A group creating and holding all the CardActors of the triti.
 */
public class TritiActor extends Table implements PropertyChangeListener {
	private final Triti triti;
	private Label scoreLbl;
	
	public TritiActor(Triti triti) {
		this.triti = triti;
		
		VerticalGroup tritiVG = new VerticalGroup();
		
		// Create a new CardActor for each card in triti and add space.
		for(Card card : triti.getCards()) {
			CardActor cardActor = new CardActor(card, CARD_SIZE_SM[0], CARD_SIZE_SM[1]);
			tritiVG.addActor(cardActor);
			tritiVG.space(CARD_SIZE_SM[1] * 0.15f - CARD_SIZE_SM[1]);
		}
		scoreLbl = new Label("Score : " + triti.getScore(), SKIN);
		align(Align.top);
		add(scoreLbl).padBottom(2);
		row();
		add(tritiVG);
		// use this to avoid redrawing the whole screen
//		triti.addPropertyChangeListener(this);
	}
	
	public Triti getTriti() {
		return triti;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(Objects.equals(evt.getPropertyName(), "Card added to Triti")) {
			scoreLbl.setText("Score: " + evt.getNewValue());
			invalidate();
		}
		
	}
}
