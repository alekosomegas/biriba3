package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.ui.DiscardsPileActor;
import com.akgames.biriba3.ui.GroupTrites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Responsible for creating the DeckActor(Deck taken from Board)
 */
public class BoardActor extends Group implements PropertyChangeListener {
    private Board board;
    private GameLogic gameLogic;
    private float[] size;
    private float[] parentSize;

    private DeckActor deckActor;
    private Label remainingDeckCards;
    private DiscardsPileActor discardsPileActor;
    private GroupTrites groupTrites;

    public BoardActor(Board board, float[] parentSize)  {
        gameLogic = GameLogic.getInstance();
        this.size = new float[]{parentSize[0] - 400, parentSize[1] - 400};
        this.parentSize = parentSize;
        this.board = board;
        this.discardsPileActor = new DiscardsPileActor();
        this.groupTrites = new GroupTrites();

        deckActor = new DeckActor(board.getDeck(), size);
        board.getDeck().addPropertyChangeListener(this);


        VerticalGroup DeckVGroup = new VerticalGroup();
        HorizontalGroup RemainingCardsHGroup = new HorizontalGroup();
        Label label = new Label("Remaining Cards: ", GameOptions.SKIN);
        remainingDeckCards = new Label(board.getDeck().getNumOfRemainingCards(), GameOptions.SKIN);

        RemainingCardsHGroup.addActor(label);
        RemainingCardsHGroup.addActor(remainingDeckCards);

        //TODO: add kozi
        DeckVGroup.addActor(deckActor);
        DeckVGroup.addActor(RemainingCardsHGroup);

        DeckVGroup.setPosition(size[0] - GameOptions.CARD_SIZE[1]/2, size[1]);

        discardsPileActor.setPosition(size[0]/2 - GameOptions.CARD_SIZE[0] /2f, size[1]);
        addActor(DeckVGroup);
        addActor(discardsPileActor);
        addActor(groupTrites);

    }

    public float[] getSize() {
        return size;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Gdx.app.log("BoardActor", evt.getNewValue().toString());
        remainingDeckCards.setText(evt.getNewValue().toString());
    }

    public DiscardsPileActor getDiscardsPileActor() {
        return discardsPileActor;
    }

    public GroupTrites getGroupTrites() {
        return groupTrites;
    }
}
