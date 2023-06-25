package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;

public class ThrowCardToDiscards implements PlayerAction {
	private Card card;
	
	public ThrowCardToDiscards(Card card) {
		this.card = card;
		card.setShowFace(true);
		card.setSelected(false);
		card.setClickable(false);
		// TODO: refactor selected cards
		GameController.getInstance().getSelectedCards().remove(card);
	}
	
	@Override
	public void execute() {
		GameController.getInstance().getBoard().addToDiscardPile(card);
		GameController.getInstance().getCurrentPlayer().removeCard(card);
		GameController.getInstance().setCurrentPlayerHasThrownCard(true);
		Turn.nextPhase();
	}
	
	@Override
	public void execute(List<?> params) {
		//        DragAndDrop.Payload payload = (DragAndDrop.Payload) params.get(0);
		//        CardActor cardActor = (CardActor) payload.getDragActor();
		//        GameController.getInstance().getBoard().addToDiscardPile(cardActor.getCard());
		//        //TODO: refactor
		//        GameController.getInstance().getPlayers().get(0).getHand().remove(cardActor.getCard());
		//
		//        Turn.nextPhase();
	}
	
	@Override
	public void undo() {
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == DISCARD;
	}
}
