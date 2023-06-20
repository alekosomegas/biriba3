package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.actions.AddCardToDiscards;
import com.akgames.biriba3.actions.AddCardToTriti;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Triti;
import com.akgames.biriba3.view.BoardActor;
import com.akgames.biriba3.view.CardActor;
import com.akgames.biriba3.view.MainPlayerHandActor;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.Arrays;
import java.util.Collections;

public class GroupParentTable extends Table{

    private GameLogic gameLogic;
    private BoardActor boardActor;
    private float[] size;
    private MainPlayerHandActor handActor;
    private TextButton exitGameBtn;

    public GroupParentTable(final Biriba3 game) {
        this.gameLogic = GameLogic.getInstance();
        this.size = new float[]{Gdx.graphics.getWidth() - 100f, Gdx.graphics.getHeight() - 100f};
        this.boardActor = new BoardActor(gameLogic.getBoard(), size);
        this.handActor = new MainPlayerHandActor(gameLogic.getPlayers().get(0), boardActor);

        this.setFillParent(true);
        this.setBounds(50, 50, size[0], size[1]);

        // Place UI elements
        exitGameBtn = new TextButton("Exit Game", GameOptions.SKIN);
        exitGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.createNewGame();
            }
        });
        add(exitGameBtn);
        add().row();

        if(gameLogic.getNumOfPlayers() == 2) {
            addPlayerBox(1);
            row();
            add(boardActor).pad(50).size(boardActor.getSize()[0], boardActor.getSize()[1]);
            row();
            add(handActor);
        } else {
            setFillParent(true);
            defaults().pad(10);
            addPlayerBox(2);
            row();
            addPlayerBox(1);
            add(boardActor).pad(50).size(boardActor.getSize()[0], boardActor.getSize()[1]);

            if (gameLogic.getNumOfPlayers() == 4) {
                addPlayerBox(3);
            }

            row();
            add(handActor).expandX().colspan(3);
        }

        final DragAndDrop dragAndDrop = new DragAndDrop();

        for (final CardActor cardActor : handActor.getHand()) {
            dragAndDrop.addSource(new DragAndDrop.Source(cardActor) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    // Set the data to be transferred in the payload
                    payload.setObject(getActor());
                    getStage().addActor(getActor());
                    // Set the visual representation of the dragged object
                    payload.setDragActor(getActor());
                    dragAndDrop.setDragActorPosition(getActor().getWidth() /2f ,-getActor().getHeight()/2f);

                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                    super.dragStop(event, x, y, pointer, payload, target);
                }
            });
            dragAndDrop.addTarget(new DragAndDrop.Target(boardActor.getDiscardsPileActor()) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    return true;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    GameLogic.getInstance().handleAction(new AddCardToDiscards(), Collections.singletonList(payload));
                }
            });
            for (final TritiActor triti : boardActor.getGroupTrites().getTritiActors()) {
                dragAndDrop.addTarget(new DragAndDrop.Target(triti) {
                    @Override
                    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        return true;
                    }

                    @Override
                    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        GameLogic.getInstance().handleAction(new AddCardToTriti(), Arrays.asList(payload, triti));
                    }
                });
            }
        }
    }

    private void addPlayerBox(int index) {
        String name = gameLogic.getPlayers().get(index).getName();
        int cardCount = gameLogic.getPlayers().get(index).getCardCount();
        add(new PlayerBox(name, cardCount)).expandX().fill();
    }

}
