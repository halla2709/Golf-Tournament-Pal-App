package com.example.halla.golftournamentpal.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Match {

    private List<Golfer> mPlayers;
    private String mResults;
    private Date mDate;

    public Match(List<Golfer> players, String results, Date date) {
        mPlayers = players;
        mResults = results;
        mDate = date;
    }

    public List<Golfer> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Golfer> players) {
        mPlayers = players;
    }

    public String getResults() {
        return mResults;
    }

    public void setResults(String results) {
        mResults = results;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}