package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.models.PlayOffRound;

import org.w3c.dom.Text;

/**
 * Created by Halla on 29-Mar-17.
 */

public class PlayOffRoundFragment extends Fragment {

    private int mRoundNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play_off_round, container, false);
        TextView testText = (TextView) rootView.findViewById(R.id.roundnumber);
        testText.setText("Round nr " + (mRoundNumber+1));
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RoundGetter roundGetter = (RoundGetter) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void setRoundNumber(int roundNumber) {
        mRoundNumber = roundNumber;
    }

    public interface RoundGetter {
        public PlayOffRound getRound(int round);
    }

}

