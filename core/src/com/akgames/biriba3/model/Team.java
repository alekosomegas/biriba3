package com.akgames.biriba3.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private int index;
	private List<Player> playerList;
	private List<Triti> tritiList;
	private int totalScore;
	private boolean hasTakenBiribaki;
	private int roundsScore;
	private List<Integer> roundsScores;
	
	public Team(int index, List<Player> playerList) {
		this.index = index;
		this.playerList = playerList;
		this.totalScore = 0;
		this.hasTakenBiribaki = false;
		this.roundsScores = new ArrayList<>();
		this.tritiList = new ArrayList<>();
		calculateScore();
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public int getRoundsScores(int roundIndex) {
		return roundsScores.get(roundIndex);
	}
	
	public void calculateScore() {
		roundsScore = 0;
		for(Triti triti : tritiList) {
			roundsScore += triti.getScore();
		}
		for(Player player : playerList) {
			roundsScore -= player.getPenaltyPoints();
		}
		roundsScore += this.hasTakenBiribaki ? 100 : 0;
	}
	
	public void saveRoundsScore() {
		roundsScores.add(roundsScore);
		totalScore += roundsScore;
		roundsScore = 0;
	}
	
	public int getRoundsScore() {
		return roundsScore;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setHasTakenBiribaki() {
		this.hasTakenBiribaki = true;
	}
	
	public boolean hasTakenBiribaki() {
		return hasTakenBiribaki;
	}
	
	public void addTriti(Triti triti) {
		tritiList.add(triti);
	}
}
