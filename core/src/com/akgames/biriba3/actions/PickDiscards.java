package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.PICK;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

public class PickDiscards implements PlayerAction {
	@Override
	public void execute() {
		// TODO: refactor
		List<Card> discards = new ArrayList<>(GameController.getInstance().getBoard().getDiscardPile());
		for(Card card : discards) {
			card.setClickable(true);
		}
		
		GameController.getInstance().getCurrentPlayer().addDiscardedCards(discards);
		GameController.getInstance().getBoard().getDiscardPile().clear();
		GameController.getInstance().setCurrentPlayerHasThrownCard(false);
		
		Gdx.app.log(this.getClass().getCanonicalName(), "sets to TRITI-" + Turn.CurrentPhase().name());
		Turn.setCurrentPhaseTo(TRITI);
	}
	
	@Override
	public void execute(List<?> params) {
	
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		// Only one action can be performed
		return Turn.CurrentPhase() == PICK;
	}
}
