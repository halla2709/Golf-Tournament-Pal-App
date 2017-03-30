package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ParticipantTab2ScoreboardActivity extends Fragment {

    Button mAddButton;
    GolferPasser mGolferPasser;
    ListView mParticipantListView;
    GolferArrayAdapter mParticipantAdapter;
    List<Golfer> mFriends = new ArrayList<>();
    List<Golfer> mParticipants = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_participant_tab2_scoreboard, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAddButton = (Button) getActivity().findViewById(R.id.addParticipantButton);
        mAddButton.setEnabled(true);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGolferPasser.participantAdded(view);
                mParticipants = mGolferPasser.getParticipants();
                mParticipantAdapter.setData(mParticipants);
                mParticipantListView.setAdapter(mParticipantAdapter);
                setListHeight(mParticipantListView);
            }
        });

        mParticipantAdapter = new GolferArrayAdapter(getActivity().getApplicationContext(), ParticipantTab2ScoreboardActivity.this);
        mParticipantListView = (ListView) getView().findViewById(R.id.participants_list);

        mParticipants = mGolferPasser.getParticipants();
        mParticipantAdapter.setData(mParticipants);
        mParticipantListView.setAdapter(mParticipantAdapter);
        setListHeight(mParticipantListView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGolferPasser = (GolferPasser) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && isResumed()) {
            Log.i("From tab2", "visible");
            mAddButton.setEnabled(true);
            mParticipants = mGolferPasser.getParticipants();
            Log.i("From tab2", ""+mParticipants.size());
            mParticipantAdapter.setData(mParticipants);
            mParticipantListView.setAdapter(mParticipantAdapter);
            setListHeight(mParticipantListView);
        }
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

    public interface GolferPasser {
        public List<Golfer> getParticipants();

        public void participantAdded(View view);
    }

    private class GolferArrayAdapter extends ArrayAdapter<Golfer> {

        private final  LayoutInflater mInflater;
        private ParticipantTab2ScoreboardActivity mFragment;

        public GolferArrayAdapter(Context context, ParticipantTab2ScoreboardActivity myFragment) {
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
}
