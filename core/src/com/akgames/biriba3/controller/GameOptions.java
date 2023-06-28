package com.akgames.biriba3.controller;

import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.model.PlayerAI;
import com.akgames.biriba3.model.Team;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GameOptions {
	
	//    // TODO change card size according to screen size
	//    float sw = Gdx.graphics.getWidth();
	//    float sh = Gdx.graphics.getHeight();
	
	public static final float[] CARD_SIZE = { 180f, 270f };
	public static final float[] CARD_SIZE_SM = { CARD_SIZE[0] * 0.8f, CARD_SIZE[1] * 0.8f };
	public static final float[] CARD_SIZE_LG = { CARD_SIZE[0] * 1.2f, CARD_SIZE[1] * 1.2f };
	public static final int NUM_CARDS_PER_PLAYER = 11;
	public static final int NUM_CARDS_BIRIBAKI_1 = 11;
	public static final int NUM_CARDS_BIRIBAKI_2 = 11;
	public static final Color BG_COLOR = new Color(0.1f, 0.1f, 0.1f, 0.5f);
	public static Skin SKIN;
	public static int pointsToWin;
	//	private final GameController gameController;
	private final PropertyChangeSupport support;
	private List<PlayerTemp> playersTemp;
	private List<Player> players;
	
	/**
	 * Start with default players
	 */
	public GameOptions() {
		//		this.gameController = gameController;
		playersTemp = new ArrayList<>();
		playersTemp.add(new PlayerTemp("Player 1", false, 1, true));
		playersTemp.add(new PlayerTemp("Player 2", true, 2, true));
		playersTemp.add(new PlayerTemp("Player 3", true, 0, false));
		playersTemp.add(new PlayerTemp("Player 4", true, 0, false));
		players = new ArrayList<>();
		support = new PropertyChangeSupport(this);
	}
	
	public void toggleActivePlayer(int index) {
		playersTemp.get(index).active = !playersTemp.get(index).active;
		assignTeams();
		
		support.firePropertyChange("p3ActiveChanged", null, playersTemp.get(2).active);
		
	}
	
	private void assignTeams() {
		// if only p3 is active
		if(playersTemp.get(2).active && !playersTemp.get(3).active) {
			playersTemp.get(2).teamNum = 3;
			playersTemp.get(3).teamNum = 0;
		}
		//if only p4 is active
		else if(!playersTemp.get(2).active && playersTemp.get(3).active) {
			playersTemp.get(2).teamNum = 0;
			playersTemp.get(3).teamNum = 3;
		}
		// both active
		else {
			playersTemp.get(2).teamNum = 1;
			playersTemp.get(3).teamNum = 2;
		}
	}
	
	public boolean isActivePlayer(int index) {
		return playersTemp.get(index).active;
	}
	
	public boolean isAiPlayer(int index) {
		return playersTemp.get(index).isAi;
	}
	
	public String getPLayersName(int index) {
		return playersTemp.get(index).name;
	}
	
	public int getTeam(int index) {
		return playersTemp.get(index).teamNum;
	}
	
	public void setPlayersName(int index, String name) {
		playersTemp.get(index).name = name;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public List<Team> createTeamsAndPlayers() {
		createPlayers();
		List<Team> teams = new ArrayList<>(getNumOfTeams());
		for(int i = 0; i < getNumOfTeams(); i++) {
			teams.add(i, new Team(i, playersInTeam(i)));
		}
		// set team to each player
		int j = 0;
		for(Player player : players) {
			player.setTeam(teams.get(j));
			j++;
		}
		
		return teams;
	}
	
	private void createPlayers() {
		for(PlayerTemp playerTemp : playersTemp) {
			if(playerTemp.active) {
				Player player = playerTemp.isAi ? new PlayerAI(playerTemp.name, playerTemp.teamNum - 1) : new Player(playerTemp.name, playerTemp.teamNum - 1);
				players.add(player);
			}
		}
	}
	
	public int getNumOfTeams() {
		int numOfActivePLayers = 0;
		for(PlayerTemp playerTemp : playersTemp) {
			if(playerTemp.active) numOfActivePLayers++;
		}
		return numOfActivePLayers == 3 ? 3 : 2;
	}
	
	private List<Player> playersInTeam(int index) {
		List<Player> playersInTeam = new ArrayList<>();
		for(Player player : players) {
			if(player.getTeamNumber() == index) {
				playersInTeam.add(player);
			}
		}
		return playersInTeam;
	}
	
	private static class PlayerTemp {
		public String name;
		public boolean isAi;
		public int teamNum;
		public boolean active;
		
		public PlayerTemp(String name, boolean isAi, int teamNum, boolean active) {
			this.name = name;
			this.isAi = isAi;
			this.teamNum = teamNum;
			this.active = active;
		}
	}
	
	
}
