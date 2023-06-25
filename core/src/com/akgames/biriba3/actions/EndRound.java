package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.model.Card;

import java.util.List;

public class EndRound implements PlayerAction {
	@Override
	public void execute() {
		for(Card card : GameController.getInstance().getCurrentPlayer().getHand()) {
			card.setSelected(false);
		}
		GameController.getInstance().getSelectedCards().clear();
	}
	
	@Override
	public void execute(List<?> params) {
	
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
