package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by UnnurKristin on 1.3.2017.
 */

public class ParticipantTab1ScoreboardActivity extends Fragment {

    FriendPasser mFriendPasser;
    FriendArrayAdapter mFriendAdapter;
    ListView mFriendListView;
    GolferArrayAdapter mParticipantAdapter;
    ListView mParticipantListView;
    CheckBox mHostCheckBox;
    List<Golfer> mFriends = new ArrayList<>();
    List<Golfer> mParticipants = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_participant_tab1_scoreboard, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        mFriendAdapter = new FriendArrayAdapter(getActivity().getApplicationContext(),ParticipantTab1ScoreboardActivity.this);
        mFriendListView = (ListView) getView().findViewById(R.id.friend_list);
        mParticipantAdapter = new GolferArrayAdapter(getActivity().getApplicationContext(), ParticipantTab1ScoreboardActivity.this);
        mParticipantListView = (ListView) getView().findViewById(R.id.participants_list);

        mFriends = mFriendPasser.getFriends();
        mFriendAdapter.setData(mFriends);
        mFriendListView.setAdapter(mFriendAdapter);
        setFriendListHeight(mFriendListView);

        mHostCheckBox = (CheckBox) getView().findViewById(R.id.hostCheckBox);
        mHostCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParticipants = mFriendPasser.hostAdder(mHostCheckBox.isChecked());
                mParticipantAdapter.setData(mParticipants);
                mParticipantListView.setAdapter(mParticipantAdapter);
                setListHeight(mParticipantListView);
            }
        });
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mFriendPasser = (FriendPasser) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()){
            mParticipants = mFriendPasser.getParticipants();
            mParticipantAdapter.setData(mParticipants);
            mParticipantListView.setAdapter(mParticipantAdapter);
            setListHeight(mParticipantListView);
        }
    }

    public void setFriendListHeight(ListView list) {
        FriendArrayAdapter adapter = (FriendArrayAdapter) list.getAdapter();
        int totalHeight = 0;
        for(int i = 0; i < mFriends.size(); i++){
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight + (list.getDividerHeight() * (list.getCount() - 1));
        list.setLayoutParams(params);
    }

    public void setListHeight(ListView list){
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

        public List<Golfer> hostAdder(boolean added);

        public List<Golfer> removeFromTournament(Golfer participant);
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

    private void removeGolferFromTournament(Golfer friend) {
        mParticipants = mFriendPasser.removeFromTournament(friend);
        mFriends.add(friend);
        mFriendAdapter.setData(mFriends);
        mFriendListView.setAdapter(mFriendAdapter);
        mParticipantAdapter.setData(mParticipants);
        mParticipantListView.setAdapter(mParticipantAdapter);
        setFriendListHeight(mFriendListView);
        setListHeight(mParticipantListView);
    }

    private class  GolferArrayAdapter extends ArrayAdapter<Golfer>{

        private final LayoutInflater mInflater;
        private ParticipantTab1ScoreboardActivity mFragment;
        public GolferArrayAdapter(Context context, ParticipantTab1ScoreboardActivity myFragment){
            super(context, R.layout.friend_list_item);
            mFragment = myFragment;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void  setData(List<Golfer> data ){
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
                            if(new SessionManager(getContext()).getSessionUserSocial() != friend.getSocial())
                                mFragment.removeGolferFromTournament(friend);
                            else
                                Toast.makeText(getContext(),
                                        "To remove yourself, uncheck the checkbox",
                                        Toast.LENGTH_SHORT).show();
                        }
                    });
            return view;
        }
    }

    private class FriendArrayAdapter extends  ArrayAdapter<Golfer> {

        private final LayoutInflater mInflater;
        private ParticipantTab1ScoreboardActivity mFragment;

        public  FriendArrayAdapter(Context context, ParticipantTab1ScoreboardActivity myFragment){
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
                        }
                    });
            return view;
        }
    }
}
