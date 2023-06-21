package com.akgames.biriba3.view;

import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.ui.BiribakiaActor;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for creating the DeckActor(Deck taken from Board)
 */
public class BoardActor extends Table implements PropertyChangeListener {
    private Board board;
    private GameLogic gameLogic;
    private DeckActor deckActor;
    private Label remainingDeckCards;
    private DiscardsPileActor discardsPileActor;
    private List<GroupTrites> groupTrites;
    private BiribakiaActor biribakiaActor;

    public BoardActor(Board board)  {
        gameLogic = GameLogic.getInstance();

        this.board = board;
        this.discardsPileActor = new DiscardsPileActor();
        groupTrites = new ArrayList<>(gameLogic.getNumOfTeams());
        for(int i=0; i < gameLogic.getNumOfTeams(); i++) {
            groupTrites.add(new GroupTrites(i));
        }



        deckActor = new DeckActor(board.getDeck());
        board.getDeck().addPropertyChangeListener(this);

        biribakiaActor = new BiribakiaActor();


        VerticalGroup DeckVGroup = new VerticalGroup();
        HorizontalGroup RemainingCardsHGroup = new HorizontalGroup();
        Label label = new Label("Remaining Cards: ", GameOptions.SKIN);
        remainingDeckCards = new Label(board.getDeck().getNumOfRemainingCards(), GameOptions.SKIN);

        RemainingCardsHGroup.addActor(label);
        RemainingCardsHGroup.addActor(remainingDeckCards);

        //TODO: add kozi
        DeckVGroup.addActor(deckActor);
        DeckVGroup.addActor(RemainingCardsHGroup);

        add(biribakiaActor).size(GameOptions.CARD_SIZE[0], GameOptions.CARD_SIZE[1]).left();
        add(discardsPileActor).left();

        add(DeckVGroup).top().right().expand();
        add().row();
        for (GroupTrites groupTriti : groupTrites) {
            add(groupTriti).prefWidth(Gdx.graphics.getWidth()/2.2f).prefHeight(Gdx.graphics.getHeight()/3f);
        }

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        remainingDeckCards.setText(evt.getNewValue().toString());
    }

    public DiscardsPileActor getDiscardsPileActor() {
        return discardsPileActor;
    }

    public List<GroupTrites> getGroupTrites() {
        return groupTrites;
    }
}
