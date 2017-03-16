package com.example.halla.golftournamentpal.views;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.models.Golfer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by UnnurKristin on 1.3.2017.
 */

public class ParticipantTab1MatchPlayActivity extends Fragment {

    FriendPasser mFriendPasser;
    FriendArrayAdapter mFriendAdapter;
    ListView mFriendListView;
    GolferArrayAdapter mParticipantAdapter;
    ListView mParticipantListView;
    List<Golfer> mFriends = new ArrayList<>();
    List<Golfer> mParticipants = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_participant_tab1_match_play, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFriendAdapter = new FriendArrayAdapter(getActivity().getApplicationContext(), ParticipantTab1MatchPlayActivity.this);
        mFriendListView = (ListView) getView().findViewById(R.id.friend_list);
        mParticipantAdapter = new GolferArrayAdapter(getActivity().getApplicationContext(), ParticipantTab1MatchPlayActivity.this);
        mParticipantListView = (ListView) getView().findViewById(R.id.participants_list);

        mFriends = mFriendPasser.getFriends();
        mFriendAdapter.setData(mFriends);
        mFriendListView.setAdapter(mFriendAdapter);
        setFriendListHeight(mFriendListView);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFriendPasser = (FriendPasser) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()) {
            Log.i("From tab1", "visible");
            mParticipants = mFriendPasser.getParticipants();
            mParticipantAdapter.setData(mParticipants);
            mParticipantListView.setAdapter(mParticipantAdapter);
            setListHeight(mParticipantListView);
        }
    }

    public void setFriendListHeight(ListView list) {
        FriendArrayAdapter adapter = (FriendArrayAdapter) list.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mFriends.size(); i++) {
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight
                + (list.getDividerHeight() * (list.getCount() - 1));
        list.setLayoutParams(params);
    }

    public void setListHeight(ListView list) {
        GolferArrayAdapter adapter = (GolferArrayAdapter) list.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mParticipants.size(); i++) {
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight
                + (list.getDividerHeight() * (list.getCount() - 1));
        list.setLayoutParams(params);
    }


    public interface FriendPasser {
        public void friendClicked(Golfer golfer);

        public List<Golfer> getFriends();

        public List<Golfer> getParticipants();
    }

    public void onFriendClicked(Golfer friend) {
        mFriendPasser.friendClicked(friend);
        mFriends.remove(friend);
        mParticipants = mFriendPasser.getParticipants();
        mFriendAdapter.setData(mFriends);
        mFriendListView.setAdapter(mFriendAdapter);
        mParticipantAdapter.setData(mParticipants);
        mParticipantListView.setAdapter(mParticipantAdapter);
        setFriendListHeight(mFriendListView);
        setListHeight(mParticipantListView);
    }

    private class GolferArrayAdapter extends ArrayAdapter<Golfer> {


        private final LayoutInflater mInflater;
        private ParticipantTab1MatchPlayActivity mFragment;

        public GolferArrayAdapter(Context context, ParticipantTab1MatchPlayActivity myFragment) {
            super(context, R.layout.friend_list_item);
            mFragment = myFragment;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<Golfer> data) {
            clear();
            if(data != null) {
                for( Golfer appEntry : data ) {
                    add(appEntry);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            View view;

            if(convertView == null) {
                view = mInflater.inflate(R.layout.friend_list_item, parent, false);
            }
            else {
                view = convertView;
            }

            final Golfer friend = getItem(position);
            ((TextView)view.findViewById(R.id.friendName)).setText(friend.getName());
            ((TextView)view.findViewById(R.id.friendSocial)).setText(Long.toString(friend.getSocial()));
            ((TextView)view.findViewById(R.id.friendHandicap)).setText(Double.toString(friend.getHandicap()));
            return view;
        }
    }

    private class FriendArrayAdapter extends ArrayAdapter<Golfer> {

        private final LayoutInflater mInflater;
        private ParticipantTab1MatchPlayActivity mFragment;

        public FriendArrayAdapter(Context context, ParticipantTab1MatchPlayActivity myFragment) {
            super(context, R.layout.friend_list_item);
            mFragment = myFragment;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<Golfer> data) {
            clear();
            if(data != null) {
                for( Golfer appEntry : data ) {
                    add(appEntry);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            View view;

            if(convertView == null) {
                view = mInflater.inflate(R.layout.friend_list_item, parent, false);
            }
            else {
                view = convertView;
            }

            final Golfer friend = getItem(position);
            ((TextView)view.findViewById(R.id.friendName)).setText(friend.getName());
            ((TextView)view.findViewById(R.id.friendSocial)).setText(Long.toString(friend.getSocial()));
            ((TextView)view.findViewById(R.id.friendHandicap)).setText(Double.toString(friend.getHandicap()));
            view.findViewById(R.id.friend_list_layout)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mFragment.onFriendClicked(friend);
                            Log.i("logging from list", ((TextView)view.findViewById(R.id.friendName)).getText().toString());
                        }
                    });
            return view;
        }
    }
}


