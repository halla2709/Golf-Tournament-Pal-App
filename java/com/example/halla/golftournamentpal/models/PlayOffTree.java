package com.example.halla.golftournamentpal.models;

import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class PlayOffTree {



    private List<PlayOffRound> mRounds;

    public PlayOffTree(List<PlayOffRound> rounds) {
        mRounds = rounds;
    }

    public List<PlayOffRound> getRounds() {
        return mRounds;
    }

    public void setRounds(List<PlayOffRound> rounds) {
        mRounds = rounds;
    }
}
