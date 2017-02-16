package com.example.halla.golftournamentpal.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Tournament {

    private String mCourse;
    private String mName;
    private List<Golfer> mPlayers;
    private Date mStartDate;

    public Tournament(String course, String name, List<Golfer> players, Date startDate) {
        mCourse = course;
        mName = name;
        mPlayers = players;
        mStartDate = startDate;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Golfer> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Golfer> players) {
        mPlayers = players;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }
}
