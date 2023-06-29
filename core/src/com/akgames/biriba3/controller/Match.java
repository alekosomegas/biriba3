package com.akgames.biriba3.controller;


import com.akgames.biriba3.Biriba3;
import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.model.Team;

import java.util.List;

public class Match {
	private static GameController controller;
	private static GameOptions gameOptions;
	private static Biriba3 game;
	private static List<Team> teams;
	private static List<Player> players;
	private static int numRounds;
	
	public Match(Biriba3 game, GameOptions options) {
		Match.game = game;
		createNewMatch(options);
	}
	
	private void createNewMatch(GameOptions options) {
		// new options
		Match.gameOptions = options;
		Match.numRounds = -1;
		teams = gameOptions.createTeamsAndPlayers();
		players = gameOptions.getPlayers();
		
		createNewGame();
	}
	
	public static boolean createNewGame() {
		Match.controller = new GameController(game);
		controller.setUpGame(players);
		numRounds++;
		return true;
	}
	
	public static GameController getController() {
		return controller;
	}
	
	public static GameOptions getGameOptions() {
		return gameOptions;
	}
	
	public static int getNumRounds() {
		return numRounds;
	}
	
	public static int getNumTeams() {
		return teams.size();
	}
	public static int getScoreForTeam(int teamIndex, int round) {
		return teams.get(teamIndex).getRoundsScores(round);
	}
	public static int getTotalScore(int teamIndex) {
		return teams.get(teamIndex).getTotalScore();
	}
	public static void endGame() {
		for(Team team : teams) {
			team.saveRoundsScore();
		}
		for(Player player : players) {
			player.getHand().clear();
		}
	}
	
}
