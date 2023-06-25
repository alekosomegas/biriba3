package com.akgames.biriba3.actions;

import com.badlogic.gdx.Gdx;

import java.util.List;

public class GameOver implements PlayerAction {
	@Override
	public void execute() {
		Gdx.app.log(getClass().getName(), "GAME OVER");
		
	}
	
	@Override
	public void execute(List<?> params) {
	
	}
	
	@Override
	public void undo() {
	
	}
	
	@Override
	public boolean allowed() {
		return true;
	}
}
