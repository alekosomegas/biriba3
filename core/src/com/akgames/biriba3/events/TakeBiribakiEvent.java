package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.*;

public class TakeBiribakiEvent implements GameEvent {
	private Player player ;
	private GameController controller;
	
	public TakeBiribakiEvent() {
		controller = Match.getController();
		player  = controller.getCurrentPlayer();
	}
	
	@Override
	public void execute() {
		// A player can only take one biribaki
		if(player.getTeam().hasTakenBiribaki()) return;
		List<Card> biribaki = controller.getBoard().getBiribaki();
		player.addToHand(biribaki);
		player.getTeam().setHasTakenBiribaki();
		if(controller.getPlayers().indexOf(player) == 0) {
			for(Card card : biribaki) {
				card.setShowFace(true);
				card.setClickable(true);
			}
		}
		
		if(Turn.CurrentPhase() == BIRIBAKI_END) {
			Turn.setCurrentPhaseTo(END);
		}
		if(Turn.CurrentPhase() == BIRIBAKI_PLAY) {
			Turn.setCurrentPhaseTo(DISCARD);
		}
	}
	
	@Override
	public boolean undo() {
		return false;
	
	}
	
	@Override
	public boolean allowed() {
		return Turn.CurrentPhase() == BIRIBAKI_END || Turn.CurrentPhase() == BIRIBAKI_PLAY;
	}
}
