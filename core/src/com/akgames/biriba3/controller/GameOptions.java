package com.akgames.biriba3.controller;

import com.akgames.biriba3.model.Player;
import com.akgames.biriba3.model.PlayerAI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GameOptions {
    public static final float[] CARD_SIZE = {225f, 337.5f};
    public static final int NUM_CARDS_PER_PLAYER = 11;
    public static final int NUM_CARDS_BIRIBAKI_1 = 11;
    public static final int NUM_CARDS_BIRIBAKI_2 = 11;
    public static Skin SKIN;

    private class PlayerTemp {
        public String name;
        public boolean isAi;
        public int team;
        public boolean active;
        public PlayerTemp(String name, boolean isAi, int team, boolean active) {
            this.name = name;
            this.isAi = isAi;
            this.team = team;
            this.active = active;
        }
    }

    private GameLogic gameLogic;
    public List<PlayerTemp> playersTemp;
    private PropertyChangeSupport support;

    /**
     * Start with default players
     */
    public GameOptions(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        playersTemp = new ArrayList<>();
        playersTemp.add(new PlayerTemp("Player 1", false, 1, true));
        playersTemp.add(new PlayerTemp("Player 2", true, 2, true));
        playersTemp.add(new PlayerTemp("Player 3", true, 0, false));
        playersTemp.add(new PlayerTemp("Player 4", true, 0, false));
        support = new PropertyChangeSupport(this);
    }

    private void assignTeams() {
        // if only p3 is active
        if(playersTemp.get(2).active && !playersTemp.get(3).active) {
            playersTemp.get(2).team = 3;
            playersTemp.get(3).team = 0;
        }
        //if only p4 is active
        else if(!playersTemp.get(2).active && playersTemp.get(3).active) {
            playersTemp.get(2).team = 0;
            playersTemp.get(3).team = 3;
        }
        // both active
        else {
            playersTemp.get(2).team = 1;
            playersTemp.get(3).team = 2;
        }

    }


    public void toggleActivePlayer(int index) {
        playersTemp.get(index).active = !playersTemp.get(index).active;
        assignTeams();

        support.firePropertyChange(
                "p3ActiveChanged", null, playersTemp.get(2).active);

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
        return playersTemp.get(index).team;
    }

    public void setPlayersName(int index, String name) {
        playersTemp.get(index).name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Options:\n");

        for (PlayerTemp player : playersTemp) {
            sb.append("Name: ");
            sb.append(player.name);
            sb.append(" - ");
            sb.append("IsAi?");
            sb.append(player.isAi);
            sb.append(" - ");
            sb.append("Team?");
            sb.append(player.team);
            sb.append(" - ");
            sb.append("Active?");
            sb.append(player.active);
            sb.append('\n');
        }
        return sb.toString();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    //Create players and hand them to gameLogic
    public boolean createPlayers() {
        List<Player> players = new ArrayList<>();
        for (PlayerTemp playerTemp : playersTemp) {
            if(playerTemp.active) {
                Player player = playerTemp.isAi ?
                        new PlayerAI(playerTemp.name, playerTemp.team -1)
                        :
                        new Player(playerTemp.name, playerTemp.team -1);

                players.add(player);
            }
        }
        gameLogic.setUpGame(players);
        return true;
    }


}
