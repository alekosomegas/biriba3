package com.akgames.biriba3.ui;

import com.akgames.biriba3.controller.GameController;
import com.akgames.biriba3.controller.Match;
import com.akgames.biriba3.model.Triti;
import com.akgames.biriba3.view.TritiActor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

import static com.akgames.biriba3.controller.GameOptions.CARD_SIZE_SM;

/**
 * Displays the trites, One for each team
 */
public class TeamsTrites extends Table {
	private final List<TritiActor> tritiActors;
	
	public TeamsTrites(int team) {
		// List of all the trites belonging to the team
		List<Triti> trites = Match.getController().getBoard().getTrites(team);
		this.tritiActors = new ArrayList<>();
		// Create actors and arrange them in a row. Keep track of their number add another row if necessary.
		int i = 0;
		for(Triti triti : trites) {
			TritiActor tritiActor = new TritiActor(triti);
			tritiActor.setSize(CARD_SIZE_SM[0], CARD_SIZE_SM[1]);
			tritiActors.add(tritiActor);
			add(tritiActor).top().left().grow().width(CARD_SIZE_SM[0]).height(CARD_SIZE_SM[1]);
			i++;
			if(i % 9 == 0) add().row();
		}
	}
	
	public List<TritiActor> getTritiActors() {
		return tritiActors;
	}
}
