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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Responsible for creating the DeckActor(Deck taken from Board)
 */
public class BoardActor extends Table implements PropertyChangeListener {
    private Board board;
    private GameLogic gameLogic;
    private DeckActor deckActor;
    private Label remainingDeckCards;
    private DiscardsPileActor discardsPileActor;
    private GroupTrites groupTrites;

    public BoardActor(Board board)  {
        gameLogic = GameLogic.getInstance();

        this.board = board;
        this.discardsPileActor = new DiscardsPileActor();
        this.groupTrites = new GroupTrites();

        deckActor = new DeckActor(board.getDeck());
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

        add(discardsPileActor).space(50);
        add(DeckVGroup).top().right().expand();
        add().row();
        add(groupTrites).expandX().fill().colspan(2);

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        remainingDeckCards.setText(evt.getNewValue().toString());
    }

    public DiscardsPileActor getDiscardsPileActor() {
        return discardsPileActor;
    }

    public GroupTrites getGroupTrites() {
        return groupTrites;
    }
}
