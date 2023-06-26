package com.akgames.biriba3.events;

import com.badlogic.gdx.Gdx;

public class GameOverEvent implements GameEvent {
	@Override
	public void execute() {
		Gdx.app.log(getClass().getName(), "GAME OVER");
		
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
