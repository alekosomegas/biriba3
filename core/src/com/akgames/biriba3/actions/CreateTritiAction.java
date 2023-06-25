package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.model.Triti;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.actions.Utils.Utils.listToString;
import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;
import static com.akgames.biriba3.controller.Turn.TurnPhases.TRITI;

// TODO: Bug when new cards come to hand. They are not in the temp Cards list so the cant be used
// TODO: Check A-2-Q
public class CreateTritiAction implements PlayerAction {
	private GameController gameLogic;
	private List<Card> selectedCards;
	private Player currentPLayer;
	private int teamNum;
	private Board board;
	
	public CreateTritiAction() {
		this.gameLogic = GameController.getInstance();
		this.selectedCards = gameLogic.getSelectedCards();
		this.currentPLayer = gameLogic.getCurrentPlayer();
		this.board = gameLogic.getBoard();
	}
	
	public CreateTritiAction(List<Card> cards) {
		this.gameLogic = GameController.getInstance();
		this.selectedCards = cards;
		this.currentPLayer = gameLogic.getCurrentPlayer();
		this.board = gameLogic.getBoard();
	}
	
	@Override
	public void execute() {
		Gdx.app.log(getClass().getName(), "Selected Cards : " + listToString(selectedCards));
		Triti triti = Triti.createTriti(new ArrayList<>(selectedCards));
		if(triti != null) {
			for(Card card : selectedCards) {
				card.setSelected(false);
				card.setClickable(false);
				card.setShowFace(true);
			}
			currentPLayer.removeCard(selectedCards);
			selectedCards.clear();
			board.addTriti(triti);
			
			// TODO: check
			// no need to do anything if in discard phase
			if(Turn.CurrentPhase() == TRITI) {
				// only if one of new cards involved
				Turn.setCurrentPhaseTo(DISCARD);
			}
		}
	}
	
	@Override
	public void execute(List<?> params) {
		//        Triti triti = Triti.createTriti((List<Card>) params);
		//        GameController.getInstance().getBoard().addTriti(triti, 0);
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		// Allow when triti(picked from discards) and optional case Discard
		return Turn.CurrentPhase() == TRITI || Turn.CurrentPhase() == DISCARD;
		
	}
}
