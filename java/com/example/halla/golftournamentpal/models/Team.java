package com.example.halla.golftournamentpal.models;

import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Team {

    private List<Golfer> mPlayers;
    private String mTeamName;
    private double mHandicap;

    public Team(List<Golfer> players, String teamName, double handicap) {
        mPlayers = players;
        mTeamName = teamName;
        mHandicap = handicap;
    }

    public List<Golfer> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Golfer> players) {
        mPlayers = players;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public double getHandicap() {
        return mHandicap;
    }

    public void setHandicap(double handicap) {
        mHandicap = handicap;
    }
}
