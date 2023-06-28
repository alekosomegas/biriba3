package com.akgames.biriba3.events;

import com.akgames.biriba3.controller.Match;
import com.badlogic.gdx.Gdx;

public class GameOverEvent implements GameEvent {
	
	public GameOverEvent() {
	
	}
	
	@Override
	public void execute() {
		Gdx.app.log(getClass().getName(), "GAME OVER");
		Match.endGame();
	}
	
	@Override
	public boolean undo() {
		// No undo
		return false;
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
