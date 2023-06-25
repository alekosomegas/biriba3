package com.akgames.biriba3.view;

import com.akgames.biriba3.Utils;
import com.akgames.biriba3.model.Board;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.ui.BiribakiaActor;
import com.akgames.biriba3.ui.DiscardsPileActor;
import com.akgames.biriba3.ui.GroupTrites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;

import javax.swing.*;
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

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        int numBiribakia = board.getNumBiribakia();
        add(biribakiaActor).size(GameOptions.CARD_SIZE[numBiribakia == 2 ? 1 : 0], GameOptions.CARD_SIZE[1]).
                left();
        add(discardsPileActor).center().grow().width(sw/3);

        add(DeckVGroup).top().right();
        row();

        Table tritesTable = new Table();
        for (GroupTrites groupTriti : groupTrites) {
            tritesTable.add(groupTriti).left().top().prefSize(sw/2, sh/2).grow().space(50);
            Utils.setBackground(groupTriti, new Color(0.1f,0.1f,0.1f,0.5f));
        }

        add(tritesTable).colspan(3).grow().pad(40);


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
