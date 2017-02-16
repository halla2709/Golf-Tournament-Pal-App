package com.example.halla.golftournamentpal.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class TeamScoreboardTournament extends ScoreboardTournament {

    private List<Team> mTeams;

    public TeamScoreboardTournament(String course, String name, List<Golfer> players, Date startDate, int[][] sores, int numberOfRounds, List<Scorecard> scorecards, List<Team> teams) {
        super(course, name, players, startDate, sores, numberOfRounds, scorecards);
        mTeams = teams;
    }

    public List<Team> getTeams() {
        return mTeams;
    }

    public void setTeams(List<Team> teams) {
        mTeams = teams;
    }
}
