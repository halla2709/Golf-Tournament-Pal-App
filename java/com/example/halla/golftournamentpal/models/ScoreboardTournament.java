package com.example.halla.golftournamentpal.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class ScoreboardTournament extends Tournament {

    private int[][] mSores;
    private int mNumberOfRounds;
    private List<Scorecard> mScorecards;

    public ScoreboardTournament(String course, String name, List<Golfer> players, Date startDate, int[][] sores, int numberOfRounds, List<Scorecard> scorecards) {
        super(course, name, players, startDate);
        mSores = sores;
        mNumberOfRounds = numberOfRounds;
        mScorecards = scorecards;
    }

    public int[][] getSores() {
        return mSores;
    }

    public void setSores(int[][] sores) {
        mSores = sores;
    }

    public int getNumberOfRounds() {
        return mNumberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        mNumberOfRounds = numberOfRounds;
    }

    public List<Scorecard> getScorecards() {
        return mScorecards;
    }

    public void setScorecards(List<Scorecard> scorecards) {
        mScorecards = scorecards;
    }
}
