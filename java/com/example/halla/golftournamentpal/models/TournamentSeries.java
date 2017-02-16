package com.example.halla.golftournamentpal.models;

import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class TournamentSeries {

    private String mName;
    private List<Tournament> mTournaments;

    public TournamentSeries(String name, List<Tournament> tournaments) {
        mName = name;
        mTournaments = tournaments;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Tournament> getTournaments() {
        return mTournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        mTournaments = tournaments;
    }
}
