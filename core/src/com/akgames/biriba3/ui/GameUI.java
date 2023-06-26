package com.akgames.biriba3.ui;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.Utils.Utils;
import com.akgames.biriba3.events.AddCardToTriti;
import com.akgames.biriba3.events.CreateTritiAction;
import com.akgames.biriba3.events.EndTurn;
import com.akgames.biriba3.events.ThrowCardToDiscards;
import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.view.BoardActor;
import com.akgames.biriba3.view.CardActor;
import com.akgames.biriba3.view.PlayerHandActor;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import static com.akgames.biriba3.controller.GameOptions.BG_COLOR;

/**
 * Responsible for creating all the UI elements of the game. And passes user input such as drag and drop and clicks
 * on buttons to the GameController.
 */
public class GameUI extends Table {
	
	private final GameController gameLogic;
	private final BoardActor boardActor;
	private final PlayerHandActor mainPlayerHandActor;
	private final Biriba3 game;
	
	
	public GameUI(final Biriba3 game) {
		this.gameLogic = GameController.getInstance();
		this.game = game;
		this.boardActor = new BoardActor(gameLogic.getBoard());
		this.mainPlayerHandActor = new PlayerHandActor(gameLogic.getMainPlayer());
		gameLogic.setMainPlayerHandActor(mainPlayerHandActor);
		// Place UI elements
		// create table for bottom row (main player and controls)
		BottomRowTable bottomRowTable = new BottomRowTable();
		// create table for top row (AI players)
		Table topRowTable = new Table();
		topRowTable.defaults().padTop(50).padBottom(50).padLeft(20).padRight(20);
		// Set background for each table
		Utils.setBackground(bottomRowTable, BG_COLOR);
		Utils.setBackground(topRowTable, BG_COLOR);
		Utils.setBackground(this, BG_COLOR);
		// Change layout depending on number of players
		if(gameLogic.getNumOfPlayers() == 2) {
			// Create UI elements for player 1
			PlayerBox p1 = new PlayerBox(gameLogic.getPlayers().get(1));
			// Add elements to table
			topRowTable.add(p1);
			add(topRowTable).center().top().grow().height(p1.getHeight() + 100);
			row();
			add(boardActor).pad(50);
			row();
			add(bottomRowTable).grow().height(mainPlayerHandActor.getHeight() + 100);
		} else {
			defaults().pad(10);
			// Create UI elements for player 1
			PlayerBox p1 = new PlayerBox(gameLogic.getPlayers().get(1));
			topRowTable.add(p1).center().width(p1.getWidth()).grow();
			// Create UI elements for player 2
			PlayerBox p2 = new PlayerBox(gameLogic.getPlayers().get(2));
			topRowTable.add(p2).center().grow().width(p2.getWidth());
			// Create UI elements for player 3
			if(gameLogic.getNumOfPlayers() == 4) {
				PlayerBox p3 = new PlayerBox(gameLogic.getPlayers().get(3));
				topRowTable.add(p3).center().grow().width(p3.getWidth());
			}
			// Add elements to table
			topRowTable.padLeft(25).padRight(25);
			add(topRowTable).center().top().grow();
			row();
			add(boardActor).pad(50);
			row();
			add(bottomRowTable).grow();
		}
		
		// Hande drag and drop functionality
		final DragAndDrop dragAndDrop = new DragAndDrop();
		setDragAndDrop(dragAndDrop);
	}
	
	private void setDragAndDrop(final DragAndDrop dragAndDrop) {
		// Drag and drop for each card in main player's hand
		for(final CardActor cardActor : mainPlayerHandActor.getHand()) {
			dragAndDrop.addSource(new DragAndDrop.Source(cardActor) {
				@Override
				public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
					DragAndDrop.Payload payload = new DragAndDrop.Payload();
					// Set the data to be transferred in the payload
					payload.setObject(getActor());
					getStage().addActor(getActor());
					// Set the visual representation of the dragged object
					payload.setDragActor(getActor());
					dragAndDrop.setDragActorPosition(getActor().getWidth() / 2f, -getActor().getHeight() / 2f);
					
					return payload;
				}
				
				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
					// TODO: find a better way to refresh
					gameLogic.refreshUi();
				}
			});
			// Can be dropped in the discards pile
			dragAndDrop.addTarget(new DragAndDrop.Target(boardActor.getDiscardsPileActor()) {
				@Override
				public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
					return true;
				}
				
				@Override
				public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
					CardActor cardActor = (CardActor) payload.getDragActor();
					Card card = cardActor.getCard();
					GameController.getInstance().handleAction(new ThrowCardToDiscards(card));
				}
			});
			// Can be dropped in a Triti that belongs to the players team
			TeamsTrites mainPlayerTeamTrites = boardActor.getGroupTrites().get(gameLogic.getMainPlayer().getTeamNumber());
			for(final TritiActor triti : mainPlayerTeamTrites.getTritiActors()) {
				dragAndDrop.addTarget(new DragAndDrop.Target(triti) {
					@Override
					public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
						return true;
					}
					
					@Override
					public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
						CardActor cardActor = (CardActor) payload.getDragActor();
						Card card = cardActor.getCard();
						gameLogic.handleAction(new AddCardToTriti(card, triti.getTriti()));
					}
				});
			}
		}
	}
	
	private class BottomRowTable extends Table {
		public BottomRowTable() {
			padTop(50).padBottom(50).padLeft(20).padRight(20);
			add(new GameControlsTable()).growY();
			add(mainPlayerHandActor).width(mainPlayerHandActor.getWidth()).grow().center();
			add(new PlayersControlsTable()).growY();
		}
	}
	
	private class GameControlsTable extends Table {
		public GameControlsTable() {
			TextButton saveGameBtn = new TextButton(("Save Game"), GameOptions.SKIN);
			saveGameBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					// TODO: save game
				}
			});
			
			TextButton exitGameBtn = new TextButton("Exit Game", GameOptions.SKIN);
			exitGameBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					// TODO: pass to the controller
					game.createNewGame();
				}
			});
			
			defaults().space(20).width(100).padRight(140);
			add(saveGameBtn).grow();
			row();
			add(exitGameBtn).grow();
		}
	}
	
	private class PlayersControlsTable extends Table {
		public PlayersControlsTable() {
			defaults().pad(20);
			
			LeftControlsTable leftControlsTable = new LeftControlsTable();
			add(leftControlsTable).grow();
			
			RightControlsTable rightControlsTable = new RightControlsTable();
			add(rightControlsTable).grow();
		}
	}
	
	private class RightControlsTable extends Table {
		public RightControlsTable() {
			TextButton endTurn = new TextButton("End round", GameOptions.SKIN);
			endTurn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					gameLogic.handleAction(new EndTurn());
				}
			});
			endTurn.setHeight(500f);
			
			add(endTurn).grow().width(100);
		}
	}
	
	private class LeftControlsTable extends Table {
		public LeftControlsTable() {
			TextButton createNewTriti = new TextButton("Create\nTriti", GameOptions.SKIN);
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
			
			defaults().space(20).width(100);
			add(createNewTriti).grow();
			row();
			add(undoBtn).grow();
		}
	}
	
}
