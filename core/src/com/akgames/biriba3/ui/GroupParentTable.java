package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.actions.*;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.view.BoardActor;
import com.akgames.biriba3.view.CardActor;
import com.akgames.biriba3.view.PlayerHandActor;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.Arrays;

public class GroupParentTable extends Table{

    private GameLogic gameLogic;
    private BoardActor boardActor;
    private PlayerHandActor mainPlayerHandActor;
    private TextButton exitGameBtn;
    private TextButton endTurn;
    private TextButton selectCards;
    private TextButton createNewTriti;


    public GroupParentTable(final Biriba3 game) {
        this.gameLogic = GameLogic.getInstance();

        this.boardActor = new BoardActor(gameLogic.getBoard());
        this.mainPlayerHandActor = new PlayerHandActor(gameLogic.getPlayers().get(0));
        gameLogic.setMainPlayerHandActor(mainPlayerHandActor);

        // Place UI elements
        exitGameBtn = new TextButton("Exit Game", GameOptions.SKIN);
        exitGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.createNewGame();
            }
        });

        endTurn = new TextButton("End round", GameOptions.SKIN);
        endTurn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameLogic.handleAction(new EndTurn());}
        });

        selectCards = new TextButton("Select mode", GameOptions.SKIN);
        selectCards.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameLogic.setSelectCardsActive(!gameLogic.isSelectCardsActive());
                gameLogic.handleAction(new SelectCards(gameLogic.isSelectCardsActive()));
            }
        });
        createNewTriti = new TextButton("Create new Triti", GameOptions.SKIN);
        createNewTriti.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameLogic.handleAction(new CreateTritiAction());
            }
        });

        add(exitGameBtn).colspan(2);
        add(selectCards);
        add(createNewTriti);
        add().row();

        if(gameLogic.getNumOfPlayers() == 2) {
            addPlayerBox(1).colspan(3);
            row();
            add(boardActor).pad(150);
            row();
            add(mainPlayerHandActor);
        } else {
            defaults().pad(10);
            addPlayerBox(2);
            row();
            addPlayerBox(1);
            add(boardActor).pad(50);

            if (gameLogic.getNumOfPlayers() == 4) {
                addPlayerBox(3);
            }

            row();
            add(mainPlayerHandActor).expandX().colspan(3);
        }
            add(endTurn);

        final DragAndDrop dragAndDrop = new DragAndDrop();
        setDragAndDrop(dragAndDrop);
    }

    private void setDragAndDrop(final DragAndDrop dragAndDrop) {
        for (final CardActor cardActor : mainPlayerHandActor.getHand()) {
            if(cardActor.getCard().isClickable()) continue;
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
                    gameLogic.refreshUi();
                }
            });
            dragAndDrop.addTarget(new DragAndDrop.Target(gameLogic.getMainPlayerHandActor()) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    return true;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    gameLogic.refreshUi();
                }
            });

            dragAndDrop.addTarget(new DragAndDrop.Target(boardActor.getDiscardsPileActor()) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    return true;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    CardActor cardActor = (CardActor)payload.getDragActor();
                    Card card = cardActor.getCard();
                    GameLogic.getInstance().handleAction(new AddCardToDiscards(card));
                }
            });
            for(GroupTrites groupTrites : boardActor.getGroupTrites()) {
                for (final TritiActor triti : groupTrites.getTritiActors()) {
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
    }

    private Cell<?> addPlayerBox(int index) {
       return add(new PlayerBox(gameLogic.getPlayers().get(index))).expandX().fill();
    }

}
