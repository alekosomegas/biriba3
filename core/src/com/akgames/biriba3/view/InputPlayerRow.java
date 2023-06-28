package com.akgames.biriba3.view;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.controller.Match;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class InputPlayerRow extends Table implements PropertyChangeListener {
	private final GameOptions gameOptions = Match.getGameOptions();
	private final Skin skin = GameOptions.SKIN;
	private final int index;
	private boolean isActive;
	private TextField nameField;
	private CheckBox aiCheckBox;
	private Label teamLabel;
	private Button addButton;
	
	public InputPlayerRow(int index) {
		this.index = index;
		this.isActive = gameOptions.isActivePlayer(index);
		
		setBackground(skin.getDrawable("default-rect"));
		
		// Create and add the UI elements
		addUIElements();
		
		// Activates/Deactivates this input
		if(!isActive) {
			toggleActive(false);
		} else {
			toggleActive(true);
			addButton.setVisible(false);
		}
	}
	
	private void addUIElements() {
		// Players name text field
		nameField = new TextField(gameOptions.getPLayersName(index), skin);
		nameField.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				gameOptions.setPlayersName(index, textField.getText());
			}
		});
		
		// AI check box. Leave on, only 1 human player
		aiCheckBox = new CheckBox("AI", skin);
		aiCheckBox.setChecked(gameOptions.isAiPlayer(index));
		aiCheckBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			}
		});
		
		// Displays the team the player belongs to
		teamLabel = new Label("Team " + gameOptions.getTeam(index), skin);
		
		// Add new player. For players 3 and 4
		addButton = new TextButton("Add", skin);
		addButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TextButton textButton = (TextButton) actor;
				// Activate
				if(textButton.getLabel().getText().toString().equals("Add")) {
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
		});
		
		// Add the UI elements to the table
		defaults().padRight(20).prefWidth(50);
		add(nameField);
		add(aiCheckBox);
		add(teamLabel);
		add(addButton);
		
	}
	
	private void toggleActive(boolean active) {
		if(active) {
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
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// if p3 is active and this is p4
		if(index == 3) {
			if(Objects.equals(evt.getNewValue(), true)) {
				addButton.setColor(addButton.getColor().r, addButton.getColor().g, addButton.getColor().b, 1f);
				addButton.setDisabled(false);
			} else {
				greyInputs(true);
				TextButton textButton = (TextButton) addButton;
				textButton.getLabel().setText("Add");
			}
		}
		teamLabel.setText("Team " + gameOptions.getTeam(index));
	}
	
	// Makes the content of this grey
	private void greyInputs(boolean grey) {
		for(Cell<?> cell : this.getCells()) {
			Color color = cell.getActor().getColor();
			color.set(color.r, color.g, color.b, grey ? 0.2f : 1f);
		}
		addButton.setColor(addButton.getColor().r, addButton.getColor().g, addButton.getColor().b, index == 3 ? 0.2f : 1f);
		addButton.setDisabled(index == 3);
	}
}
