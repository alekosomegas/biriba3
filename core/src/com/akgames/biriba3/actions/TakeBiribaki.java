package com.akgames.biriba3.actions;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.Turn;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;

import java.util.List;

import static com.akgames.biriba3.controller.Turn.TurnPhases.*;

public class TakeBiribaki implements PlayerAction {
    Player player = GameLogic.getInstance().getCurrentPlayer();

    @Override
    public void execute() {
        // A player can only take one biribaki
        if (player.hasTakenBiribaki()) return;

        List<Card> biribaki = GameLogic.getInstance().getBoard().getBiribaki();
        player.addToHand(biribaki);
        player.setHasTakenBiribaki();
        if(GameLogic.getInstance().getPlayers().indexOf(player) == 0) {
            for (Card card : biribaki) {
                card.setShowFace(true);
                card.setClickable(true);
            }
        }

        if (Turn.CurrentPhase() == BIRIBAKI_END) {
            Turn.setCurrentPhaseTo(END);
        }
        if (Turn.CurrentPhase() == BIRIBAKI_PLAY) {
            Turn.setCurrentPhaseTo(DISCARD);
        }
    }

    @Override
    public void execute(List<?> params) {

    }

    @Override
    public void undo() {

    }

    @Override
    public boolean allowed() {
        return Turn.CurrentPhase() == BIRIBAKI_END || Turn.CurrentPhase() == BIRIBAKI_PLAY;
    }
}
