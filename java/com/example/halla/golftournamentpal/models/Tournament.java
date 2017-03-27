package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Tournament implements Parcelable {

    private Long mId;
    private String mCourse;
    private String mName;
    private List<Golfer> mPlayers;
    private Date mStartDate;

    public Tournament() {
        mPlayers = new ArrayList<>();
        mStartDate = new Date();
    }

    public Tournament(Long id, String course, String name, List<Golfer> players, Date startDate) {
        mCourse = course;
        mName = name;
        mId = id;

        if(players == null) {
            mPlayers = new ArrayList<>();
        }
        else {
            mPlayers = players;
        }
        mStartDate = startDate;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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

    public void addPlayer(Golfer golfer) {
        mPlayers.add(golfer);
    }

    protected Tournament(Parcel in) {
        mId = in.readByte() == 0x00 ? null : in.readLong();
        mCourse = in.readString();
        mName = in.readString();
        if (in.readByte() == 0x01) {
            mPlayers = new ArrayList<Golfer>();
            in.readList(mPlayers, Golfer.class.getClassLoader());
        } else {
            mPlayers = null;
        }
        long tmpMStartDate = in.readLong();
        mStartDate = tmpMStartDate != -1 ? new Date(tmpMStartDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mId);
        }
        dest.writeString(mCourse);
        dest.writeString(mName);
        if (mPlayers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mPlayers);
        }
        dest.writeLong(mStartDate != null ? mStartDate.getTime() : -1L);
    }
}