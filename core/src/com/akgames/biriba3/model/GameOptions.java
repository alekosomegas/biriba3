package com.akgames.biriba3.model;

import com.badlogic.gdx.Gdx;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameOptions {
    private class Player {
        public String name;
        public boolean isAi;
        public int team;
        public boolean active;
        public Player(String name, boolean isAi, int team, boolean active) {
            this.name = name;
            this.isAi = isAi;
            this.team = team;
            this.active = active;
        }
    }

    public List<Player> players;
    private PropertyChangeSupport support;

    /**
     * Start with default players
     */
    public GameOptions() {
        players = new ArrayList<>();
        players.add(new Player("Player 1", false, 1, true));
        players.add(new Player("Player 2", true, 2, true));
        players.add(new Player("Player 3", true, 0, false));
        players.add(new Player("Player 4", true, 0, false));
        support = new PropertyChangeSupport(this);
    }

    private void assignTeams() {
        // if only p3 is active
        if(players.get(2).active && !players.get(3).active) {
            players.get(2).team = 3;
            players.get(3).team = 0;
        }
        //if only p4 is active
        else if(!players.get(2).active && players.get(3).active) {
            players.get(2).team = 0;
            players.get(3).team = 3;
        }
        // both active
        else {
            players.get(2).team = 1;
            players.get(3).team = 2;
        }

    }

    public List<Player> getPlayers() {
        return players;
    }

    public void toggleActivePlayer(int index) {
        players.get(index).active = !players.get(index).active;
        assignTeams();

        support.firePropertyChange(
                "p3ActiveChanged", null, players.get(2).active);

    }
    public boolean isActivePlayer(int index) {
        return players.get(index).active;
    }
    public boolean isAiPlayer(int index) {
        return players.get(index).isAi;
    }
    public String getPLayersName(int index) {
        Gdx.app.log("MyTag", players.get(index).name);
        return players.get(index).name;
    }
    public int getTeam(int index) {
        return players.get(index).team;
    }

    public void setPlayersName(int index, String name) {
        players.get(index).name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Options:\n");

        for (Player player : players) {
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
}
