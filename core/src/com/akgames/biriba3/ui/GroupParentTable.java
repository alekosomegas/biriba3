package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.Utils;
import com.akgames.biriba3.actions.*;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.view.BoardActor;
import com.akgames.biriba3.view.CardActor;
import com.akgames.biriba3.view.PlayerHandActor;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import javax.swing.*;
import java.util.Arrays;

import static com.akgames.biriba3.controller.GameOptions.BG_COLOR;
import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE_LG;

public class GroupParentTable extends Table{

    private GameLogic gameLogic;
    private BoardActor boardActor;
    private PlayerHandActor mainPlayerHandActor;
    private TextButton exitGameBtn;
    private TextButton endTurn;
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

        TextButton saveGameBtn = new TextButton(("Save Game"), GameOptions.SKIN);
        saveGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: save gamme
            }
        });

        endTurn = new TextButton("End round", GameOptions.SKIN);
        endTurn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameLogic.handleAction(new EndTurn());}
        });
        endTurn.setHeight(500f);

        createNewTriti = new TextButton("Create\nTriti", GameOptions.SKIN);
        createNewTriti.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameLogic.handleAction(new CreateTritiAction());
            }
        });

        TextButton undoBtn = new TextButton("Undo", GameOptions.SKIN);
        undoBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: undo
            }
        });

        Table controlsTable = new Table();
        controlsTable.defaults().pad(20);
        Table leftTable = new Table();
        leftTable.defaults().space(20).width(100);
        leftTable.add(createNewTriti).grow();
        leftTable.row();
        leftTable.add(undoBtn).grow();
        controlsTable.add(leftTable).grow();
        Table rightTable = new Table();
        rightTable.add(endTurn).grow().width(100);
        controlsTable.add(rightTable).grow();

        Table gameControlsTable = new Table();
        gameControlsTable.defaults().space(20).width(100).padRight(140);
        gameControlsTable.add(saveGameBtn).grow();
        gameControlsTable.row();
        gameControlsTable.add(exitGameBtn).grow();

        Table bottomRowTable = new Table();
        bottomRowTable.padTop(50).padBottom(50).padLeft(20).padRight(20);
        bottomRowTable.add(gameControlsTable).growY();
        bottomRowTable.add(mainPlayerHandActor).width(mainPlayerHandActor.getWidth()).grow().center();
        bottomRowTable.add(controlsTable).growY();


        Table topRowTable = new Table();
        topRowTable.defaults().padTop(50).padBottom(50).padLeft(20).padRight(20);

        Utils.setBackground(bottomRowTable, BG_COLOR);
        Utils.setBackground(topRowTable, BG_COLOR);
        Utils.setBackground(this, BG_COLOR);

        if(gameLogic.getNumOfPlayers() == 2) {
            PlayerBox p1 = new PlayerBox(gameLogic.getPlayers().get(1));
            topRowTable.add(p1);
            add(topRowTable).center().top().grow().height(p1.getHeight() + 100);
            row();
            add(boardActor).pad(50);
            row();
            add(bottomRowTable).grow().height(mainPlayerHandActor.getHeight() + 100);
        } else {
            defaults().pad(10);
            PlayerBox p1 = new PlayerBox(gameLogic.getPlayers().get(1));
            topRowTable.add(p1).center().width(p1.getWidth()).grow();
            PlayerBox p2 = new PlayerBox(gameLogic.getPlayers().get(2));
            topRowTable.add(p2).center().grow().width(p2.getWidth());
            if (gameLogic.getNumOfPlayers() == 4) {
                PlayerBox p3 = new PlayerBox(gameLogic.getPlayers().get(3));
                topRowTable.add(p3).center().grow().width(p3.getWidth());
            }
            topRowTable.padLeft(25).padRight(25);
            add(topRowTable).center().top().grow();
            row();
            add(boardActor).pad(50);

            row();
            add(bottomRowTable).grow();
        }


        final DragAndDrop dragAndDrop = new DragAndDrop();
        setDragAndDrop(dragAndDrop);
    }

    private Cell<?> addPlayerBox(int index) {
        PlayerBox playerBox = new PlayerBox(gameLogic.getPlayers().get(index));
        return add(playerBox).grow().width(playerBox.getWidth()).height(playerBox.getHeight());
    }




    private void setDragAndDrop(final DragAndDrop dragAndDrop) {
        for (final CardActor cardActor : mainPlayerHandActor.getHand()) {
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
                    GameLogic.getInstance().handleAction(new ThrowCardToDiscards(card));
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



}
