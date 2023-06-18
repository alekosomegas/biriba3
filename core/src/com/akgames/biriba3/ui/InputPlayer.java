package com.akgames.biriba3.ui;

import com.akgames.biriba3.model.GameOptions;
import com.akgames.biriba3.view.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import javax.swing.text.TableView;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InputPlayer extends Actor implements PropertyChangeListener {
    private Table table;
    private int index;
    private boolean isActive;

    //TODO:Use single skin for the whole project. Settings class?
    private Skin skin;
    private Label nameLabel;
    private TextField nameField;
    private CheckBox aiCheckBox;
    private Label teamLabel;
    private Button addButton;
    private GameOptions gameOptions;

    public InputPlayer(GameOptions gameOptions, int index) {
        this.gameOptions = gameOptions;
        this.index = index;
        this.isActive = gameOptions.isActivePlayer(index);
        this.skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));

        table = new Table();
        table.setBackground(skin.getDrawable("default-rect"));

        // Create the UI elements
        nameField = new TextField(gameOptions.getPLayersName(index), skin);
        nameField.setTextFieldListener(new NameChangeListener());

        aiCheckBox = new CheckBox("AI", skin);
        aiCheckBox.setChecked(gameOptions.isAiPlayer(index));
        aiCheckBox.addListener(new AiCheckClickListener());

        teamLabel = new Label("Team " + gameOptions.getTeam(index), skin);

        addButton = new TextButton("Add", skin);
        addButton.addListener(new AddButtonClickListener());

        // Add the UI elements to the table
        table.add(nameField).padRight(20);
        table.add(aiCheckBox).padRight(20);
        table.add(teamLabel).width(50f).padRight(20);
        table.add(addButton).width(50f).padRight(20);

        if(!isActive) {
            toggleActive(false);
        } else {
            toggleActive(true);

            addButton.setVisible(false);
        }

    }

    public Table getTable() {
        return table;
    }

    private class AddButtonClickListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            TextButton textButton = (TextButton) actor;
            // Activate
            if (textButton.getLabel().getText().toString().equals("Add")) {
                toggleActive(true);
                textButton.getLabel().setText("x");
            }
            // Deactivate
            else {
                toggleActive(false);
                textButton.getLabel().setText("Add");
            }
            gameOptions.toggleActivePlayer(index);

        }


    }
    private class AiCheckClickListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {

        }
    }
    private class NameChangeListener implements TextField.TextFieldListener {


        @Override
        public void keyTyped(TextField textField, char c) {
            gameOptions.setPlayersName(index, textField.getText());
        }
    }

    private void toggleActive(boolean active) {
        if (active) {
            isActive = true;
            nameField.setDisabled(false);
            aiCheckBox.setDisabled(false);
            greyInputs(false);
        } else {
            isActive = false;
            nameField.setDisabled(true);
            aiCheckBox.setDisabled(true);


            greyInputs(true);
        }
    }

    private void greyInputs(boolean grey) {
//        SelectBox.SelectBoxStyle fontStyle = teamSelectBox.getStyle();
//        fontStyle.fontColor = grey ? Color.GRAY : Color.WHITE;
//        teamSelectBox.setStyle(fontStyle);

        for (Cell<?> cell: table.getCells()) {
            Color color = cell.getActor().getColor();
            color.set(color.r, color.g, color.b, grey? 0.2f: 1f);
        }
        addButton.setColor(
                addButton.getColor().r,addButton.
                        getColor().g,addButton.
                        getColor().b,index == 3 ? 0.2f : 1f);
        addButton.setDisabled(index == 3);
    }


    //TODO: bug when click add p3 - add p4 - x p3 - add p4 appears as x
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // if p3 is active and this is p4
        if(index == 3) {
            if ((boolean)evt.getNewValue()) {
                addButton.setColor(
                        addButton.getColor().r,addButton.
                                getColor().g,addButton.
                                getColor().b,1f);
                addButton.setDisabled(false);
            } else {
                greyInputs(true);
            }

        }
    }
}
