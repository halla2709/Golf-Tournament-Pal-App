package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Match implements Parcelable {

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

    protected Match(Parcel in) {
        if (in.readByte() == 0x01) {
            mPlayers = new ArrayList<Golfer>();
            in.readList(mPlayers, Golfer.class.getClassLoader());
        } else {
            mPlayers = null;
        }
        mResults = in.readString();
        long tmpMDate = in.readLong();
        mDate = tmpMDate != -1 ? new Date(tmpMDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mPlayers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mPlayers);
        }
        dest.writeString(mResults);
        dest.writeLong(mDate != null ? mDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
}