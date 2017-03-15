package com.example.halla.golftournamentpal.views;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.halla.golftournamentpal.FriendArrayAdapter;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.models.Golfer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by UnnurKristin on 1.3.2017.
 */

public class ParticipantTab1MatchPlayActivity extends Fragment {

    FriendPasser mFriendPasser;
    FriendArrayAdapter mAdapter;
    ListView mListView;
    List<Golfer> mFriends = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_participant_tab1_match_play, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new FriendArrayAdapter(getActivity().getApplicationContext());
        mListView = (ListView) getView().findViewById(R.id.friend_list);
        mFriends = mFriendPasser.getFriends();
        mAdapter.setData(mFriends);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFriendPasser = (FriendPasser) context;
    }

    public interface FriendPasser {
        public void friendClicked(Golfer golfer);

        public List<Golfer> getFriends();
    }


}
