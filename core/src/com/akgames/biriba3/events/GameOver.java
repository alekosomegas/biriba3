package com.akgames.biriba3.events;

import com.badlogic.gdx.Gdx;

public class GameOver implements GameEvent {
	@Override
	public void execute() {
		Gdx.app.log(getClass().getName(), "GAME OVER");
		
	}
	
	@Override
	public void undo() {
		// No undo
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
