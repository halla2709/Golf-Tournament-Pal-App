package com.example.halla.golftournamentpal.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class TeamMatchPlayTournament extends MatchPlayTournament {

    private List<Team> mTeams;

    public TeamMatchPlayTournament(String course, String name, List<Golfer> players, Date startDate, boolean areBrackets, List<Bracket> brackets, PlayOffTree playOffs, List<Team> teams) {
        super(course, name, players, startDate, areBrackets, brackets, playOffs);
        mTeams = teams;
    }

    public List<Team> getTeams() {
        return mTeams;
    }

    public void setTeams(List<Team> teams) {
        mTeams = teams;
    }
}
