package com.example.halla.golftournamentpal.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class MatchPlayTournament extends Tournament {

    private boolean mAreBrackets;
    private List<Bracket> mBrackets;
    private PlayOffTree mPlayOffs;

    public MatchPlayTournament(String course, String name, List<Golfer> players, Date startDate, boolean areBrackets, List<Bracket> brackets, PlayOffTree playOffs) {
        super(course, name, players, startDate);
        mAreBrackets = areBrackets;
        mBrackets = brackets;
        mPlayOffs = playOffs;
    }

    public MatchPlayTournament() {
    }

    public boolean isAreBrackets() {
        return mAreBrackets;
    }

    public void setAreBrackets(boolean areBrackets) {
        mAreBrackets = areBrackets;
    }

    public List<Bracket> getBrackets() {
        return mBrackets;
    }

    public void setBrackets(List<Bracket> brackets) {
        mBrackets = brackets;
    }

    public PlayOffTree getPlayOffs() {
        return mPlayOffs;
    }

    public void setPlayOffs(PlayOffTree playOffs) {
        mPlayOffs = playOffs;
    }
}
