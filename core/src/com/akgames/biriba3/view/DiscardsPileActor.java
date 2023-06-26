package com.akgames.biriba3.view;

import com.akgames.biriba3.Utils.Utils;
import com.akgames.biriba3.events.PickDiscardsEvent;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.model.Card;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.GameOptions.*;

/**
 * Responsible for displaying the discarded cards and handling user interaction. Displays them in rows.
 */
public class DiscardsPileActor extends Table {
	
	public DiscardsPileActor() {
		// The discarded cards
		List<Card> discardPile = GameController.getInstance().getBoard().getDiscardPile();
		// List of all the horizontal groups
		List<HorizontalGroup> horizontalGroupList = new ArrayList<>();
		// Group will lay the cards' actors in horizontally
		horizontalGroupList.add(new HorizontalGroup());
		// Group that will lay all the rows vertically, forming a table
		VerticalGroup cardRows = new VerticalGroup();
		int currentRowIndex = 0;
		// Create the card actor and create necessary rows
		for(Card card : discardPile) {
			//TODO:this removes it from its previous parent. Perhaps no need to create new objects and refresh stage?
			horizontalGroupList.get(currentRowIndex).addActor(new CardActor(card, CARD_SIZE_SM[0], CARD_SIZE_SM[1]));
			horizontalGroupList.get(currentRowIndex).space(CARD_SIZE_SM[0] * (-0.8f));
			if(horizontalGroupList.get(currentRowIndex).getChildren().size > 20) {
				horizontalGroupList.add(new HorizontalGroup());
				currentRowIndex = currentRowIndex + 1;
			}
		}
		// place the rows in vertical group
		for(HorizontalGroup hg : horizontalGroupList) {
			cardRows.addActor(hg);
		}
		// Overlap rows by half a card
		cardRows.space(-CARD_SIZE[1] / 2f);
		// Add a background colour
		Utils.setBackground(this, BG_COLOR);
		pad(25);
		// Notify the controller
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.getInstance().handleAction(new PickDiscardsEvent());
			}
		});
		
		add(cardRows);
	}
}
