package com.example.halla.golftournamentpal.models;

import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class PlayOffRound {

    private int mRound;
    private List<Match> mMatches;

    public PlayOffRound(int round, List<Match> matches) {
        mRound = round;
        mMatches = matches;
    }

    public List<Match> getMatches() {
        return mMatches;
    }

    public void setMatches(List<Match> matches) {
        mMatches = matches;
    }

    public int getRound() {
        return mRound;
    }

    public void setRound(int round) {
        mRound = round;
    }
}
