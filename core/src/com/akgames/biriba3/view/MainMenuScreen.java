package com.akgames.biriba3.view;

import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.model.GameOptions;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.ui.GroupInputPlayers;
import com.akgames.biriba3.ui.InputPlayer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

public class MainMenuScreen extends ScreenAdapter {
    private final Biriba3 game;
    private GameOptions gameOptions;
    private final Skin skin;
    private Stage stage;
    private Table table;

    private Label nameLabel;
    private Label typeLabel;
    private Label teamLabel;
    private Label errorLabel;

    private List<Player> playersList;
    private ScrollPane playersScrollPane;
    private Array<Player> players;

    private TextField nameField;
    private CheckBox aiCheckBox;
    private SelectBox<String> teamSelectBox;

    private Button startButton;


    public MainMenuScreen(final Biriba3 game) {
        this.game = game;
        this.gameOptions = game.getGameLogic().gameOptions;
        this.skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
    }

    @Override
    public void show() {
        // Create the stage
        stage = new Stage(new FitViewport(900, 900));
        Gdx.input.setInputProcessor(stage);

        // Create the layout of the UI elements
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

//        // Create the UI elements
//        nameLabel = new Label("Name", skin);
//        typeLabel = new Label("Type", skin);
//        teamLabel = new Label("Team", skin);
//        errorLabel = new Label("", skin);
//
//        players = new Array<Player>();
//        playersList = new List<Player>(skin);
//        playersScrollPane = new ScrollPane(playersList, skin);
//        playersScrollPane.setFadeScrollBars(false);
//
//        nameField = new TextField("", skin);
//        aiCheckBox = new CheckBox("AI", skin);
//        teamSelectBox = new SelectBox<String>(skin);
//        teamSelectBox.setItems("Team 1", "Team 2");
//
//        addButton = new TextButton("Add", skin);
//        addButton.addListener(new AddButtonClickListener());
//        removeButton = new TextButton("Remove", skin);
//        removeButton.addListener(new RemoveButtonClickListener());
        startButton = new TextButton("Start Game", skin);
        startButton.addListener(new StartButtonClickListener());
//
//        // Add the UI elements to the table
//        table.add(playersScrollPane).colspan(4).padTop(20);
//        table.row();
//        table.add(new Label("Name", skin)).padRight(20);
//        table.add(new Label("AI", skin)).padRight(20);
//        table.add(new Label("Team", skin)).padRight(20);
//        table.add(addButton).padRight(20);
//        table.row();
//        table.add(teamLabel).padRight(20);
//
//        // Add the input fields for the first player
//        addPlayerInputFields();
//        //TODO: refactor
//        InputPlayer p1 = new InputPlayer(gameOptions, 0);
//        InputPlayer p2 = new InputPlayer(gameOptions, 1);
//        InputPlayer p3 = new InputPlayer(gameOptions, 2);
//        InputPlayer p4 = new InputPlayer(gameOptions, 3);
        GroupInputPlayers groupInputPlayers = new GroupInputPlayers(gameOptions);


//        table.add(p1.getTable()).padBottom(6);
//        table.row();
//        table.add(p2.getTable()).padBottom(6);
//        table.row();
//        table.add(p3.getTable()).padBottom(6);
//        table.row();
//        table.add(p4.getTable()).padBottom(6);
//        table.row();
        table.add(groupInputPlayers.getTable());
        table.row();
        table.add(startButton);
//
////        TextButton playButton = new TextButton("Play Game", skin);
////
////        playButton.setPosition(
////                Gdx.graphics.getWidth() / 2f - playButton.getWidth() / 2f,
////                Gdx.graphics.getHeight() / 2f - playButton.getHeight() / 2f);
////
////        playButton.addListener(new ChangeListener() {
////            @Override
////            public void changed(ChangeEvent event, Actor actor) {
////                game.setScreen(game.getGameScreen());
////            }
////        });
////
////        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // Draw the UI elements
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    private class AddButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            // Validate the player name
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                errorLabel.setText("Please enter a name for the player");
            } else {
                // Add the player to the list
                boolean isAI = aiCheckBox.isChecked();
                int teamNumber = teamSelectBox.getSelected().equals("Team 1") ? 1 : 2;
                Player player =new Player(name, isAI, teamNumber);
                players.add(player);
                playersList.setItems(players);

                // Clear the input fields and error message
                nameField.setText("");
                aiCheckBox.setChecked(false);
                teamSelectBox.setSelectedIndex(0);
                errorLabel.setText("");
            }
        }
    }

    private class RemoveButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            // Remove the selected player from the list
            Player selectedPlayer = playersList.getSelected();
            if (selectedPlayer != null) {
                players.removeValue(selectedPlayer, true);
                playersList.setItems(players);
            }
        }
    }

    private class StartButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.log("start", gameOptions.toString());
            // Start the game with the selected players
//                game.setScreen(game.getGameScreen());
//            if (selectedPlayers.size < 2) {
//                errorLabel.setText("Please select at least 2 players to start the game");
//            } else {
//            }
        }
    }

    private void addPlayerInputFields() {
        // Create a new set of input fields for the next player in the list
        TextField nameField = new TextField("", skin);
        CheckBox aiCheckBox = new CheckBox("AI", skin);
        SelectBox<String> teamSelectBox = new SelectBox<String>(skin);
        teamSelectBox.setItems("Team 1", "Team 2");

        // Add the input fields to the table
        table.add(nameField).padRight(20);
        table.add(aiCheckBox).padRight(20);
        table.add(teamSelectBox).padRight(20);
        table.row();

        // Add a new player to the list with the default values
        Player player = new Player("", false, 1);
        players.add(player);
        playersList.setItems(players);

//        // Update the player's details when the input fields change
//        nameField.addListener(new PlayerNameChangeListener(player));
//        aiCheckBox.addListener(new PlayerAIChangeListener(player));
//        teamSelectBox.addListener(new PlayerTeamChangeListener(player));
    }

    private void removePlayerInputFields() {
        // Remove the input fields for the last player in the list
        int lastPlayerIndex = players.size - 1;
//        table.removeActorAt(lastPlayerIndex * 3 + 2);
//        table.removeActorAt(lastPlayerIndex * 3 + 1);
//        table.removeActorAt(lastPlayerIndex * 3);

        // Remove the last player from the list
        players.removeIndex(lastPlayerIndex);
        playersList.setItems(players);
    }
}
