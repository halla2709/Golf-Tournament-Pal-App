package com.example.halla.golftournamentpal.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class ScoreboardTournament extends Tournament {

    private int[][] mScores;
    private int mNumberOfRounds;
    private List<Scorecard> mScorecards;

    public ScoreboardTournament(String course, String name, List<Golfer> players, Date startDate, int[][] scores, int numberOfRounds, List<Scorecard> scorecards) {
        super(course, name, players, startDate);
        mScores = scores;
        mNumberOfRounds = numberOfRounds;
        mScorecards = scorecards;
    }

    public ScoreboardTournament() {
        super();
        mScorecards = new ArrayList<>();
    }

    public int[][] getSores() {
        return mScores;
    }

    public void setSores(int[][] scores) {
        mScores = scores;
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
