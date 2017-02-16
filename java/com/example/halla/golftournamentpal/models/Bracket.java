package com.example.halla.golftournamentpal.models;

import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Bracket {

    private List<Match> mMatch;
    private List<Golfer> mPlayers;
    private String mName;

    public Bracket(List<Match> match, List<Golfer> players, String name) {
        mMatch = match;
        mPlayers = players;
        mName = name;
    }

    public List<Match> getMatch() {
        return mMatch;
    }

    public void setMatch(List<Match> match) {
        mMatch = match;
    }

    public List<Golfer> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Golfer> players) {
        mPlayers = players;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
