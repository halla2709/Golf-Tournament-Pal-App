package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class MatchPlayTournament extends Tournament implements Parcelable {

    private boolean mAreBrackets;
    private List<Bracket> mBrackets;
    private PlayOffTree mPlayOffs;

    public MatchPlayTournament(Long id, String course, String name, List<Golfer> players, Date startDate, boolean areBrackets, List<Bracket> brackets, PlayOffTree playOffs) {
        super(id, course, name, players, startDate);
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

    protected MatchPlayTournament(Parcel in) {
        super(in);
        mAreBrackets = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            mBrackets = new ArrayList<Bracket>();
            in.readList(mBrackets, Bracket.class.getClassLoader());
        } else {
            mBrackets = null;
        }
        mPlayOffs = (PlayOffTree) in.readValue(PlayOffTree.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (mAreBrackets ? 0x01 : 0x00));
        if (mBrackets == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mBrackets);
        }
        dest.writeValue(mPlayOffs);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MatchPlayTournament> CREATOR = new Parcelable.Creator<MatchPlayTournament>() {
        @Override
        public MatchPlayTournament createFromParcel(Parcel in) {
            return new MatchPlayTournament(in);
        }

        @Override
        public MatchPlayTournament[] newArray(int size) {
            return new MatchPlayTournament[size];
        }
    };
}