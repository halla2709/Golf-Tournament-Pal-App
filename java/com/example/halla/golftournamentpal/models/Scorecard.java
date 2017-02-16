package com.example.halla.golftournamentpal.models;

import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Scorecard {

    private Golfer mPlayer;
    private String mCourse;
    private Team mTeam;
    private int mNumberOfRounds;
    private List<Round> mRounds;

    public Scorecard(Golfer player, String course, Team team, int numberOfRounds, List<Round> rounds) {
        mPlayer = player;
        mCourse = course;
        mTeam = team;
        mNumberOfRounds = numberOfRounds;
        mRounds = rounds;
    }

    public Golfer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Golfer player) {
        mPlayer = player;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public Team getTeam() {
        return mTeam;
    }

    public void setTeam(Team team) {
        mTeam = team;
    }

    public int getNumberOfRounds() {
        return mNumberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        mNumberOfRounds = numberOfRounds;
    }

    public List<Round> getRounds() {
        return mRounds;
    }

    public void setRounds(List<Round> rounds) {
        mRounds = rounds;
    }
}
