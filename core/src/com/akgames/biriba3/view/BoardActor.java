package com.akgames.biriba3.view;

import com.akgames.biriba3.actions.Utils.Utils;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.ui.TeamsTrites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.GameOptions.BG_COLOR;
import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE;

/**
 * Responsible for displaying the Board and its content, ie the deck, the discards pile, biribakia and trites.
 */
public class BoardActor extends Table implements PropertyChangeListener {
	private final GameController gameLogic;
	private final DeckActor deckActor;
	private final Label remainingDeckCards;
	private final DiscardsPileActor discardsPileActor;
	private final List<TeamsTrites> groupTrites;
	
	public BoardActor(Board board) {
		this.gameLogic = GameController.getInstance();
		this.groupTrites = new ArrayList<>(gameLogic.getNumOfTeams());
		this.deckActor = new DeckActor(board.getDeck());
		this.discardsPileActor = new DiscardsPileActor();
		this.remainingDeckCards = new Label(board.getDeck().getNumOfRemainingCards(), GameOptions.SKIN);
		// Listens to the number of remaining cards in the deck
		board.getDeck().addPropertyChangeListener(this);
		int numBiribakia = board.getNumBiribakia();
		BiribakiaActor biribakiaActor = new BiribakiaActor();
		// Place UI elements to table
		add(biribakiaActor).size(GameOptions.CARD_SIZE[numBiribakia == 2 ? 1 : 0], GameOptions.CARD_SIZE[1]).left();
		add(discardsPileActor).center().grow().width(CARD_SIZE[0] * 0.2f * 20);
		add(new DeckTable()).top().right();
		row();
		add(new TritesTable()).colspan(3).grow().pad(40);
	}
	
	// Updates the label with number of cards remaining in deck
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		remainingDeckCards.setText(evt.getNewValue().toString());
	}
	
	public DiscardsPileActor getDiscardsPileActor() {
		return discardsPileActor;
	}
	
	public List<TeamsTrites> getGroupTrites() {
		return groupTrites;
	}
	
	private class DeckTable extends Table {
		Label label = new Label("Remaining Cards: ", GameOptions.SKIN);
		
		public DeckTable() {
			//TODO: add kozi
			add(deckActor).colspan(2);
			row();
			add(label);
			add(remainingDeckCards);
		}
	}
	
	private class TritesTable extends Table {
		float sw = Gdx.graphics.getWidth();
		float sh = Gdx.graphics.getHeight();
		
		public TritesTable() {
			for(int i = 0; i < gameLogic.getNumOfTeams(); i++) {
				groupTrites.add(new TeamsTrites(i));
			}
			for(TeamsTrites groupTriti : groupTrites) {
				add(groupTriti).left().top().prefSize(sw / 2, sh / 2).grow().space(50);
				Utils.setBackground(groupTriti, new Color(BG_COLOR));
			}
		}
	}
}
