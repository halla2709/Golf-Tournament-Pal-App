package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Match;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.PlayOffRound;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Halla on 29-Mar-17.
 */

public class PlayOffRoundFragment extends Fragment {

    private static final String ROUND_NUMBER = "roundNumber";
    private int mRoundNumber;
    private PlayOffRound mRound;
    private RoundGetter mRoundGetter;

    private RoundArrayAdapter mRoundArrayAdapter;
    private ListView mMatchListView;

    public static PlayOffRoundFragment newInstance(int roundNumber) {

        Bundle args = new Bundle();
        args.putInt(ROUND_NUMBER, roundNumber);

        PlayOffRoundFragment fragment = new PlayOffRoundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoundNumber = getArguments().getInt(ROUND_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play_off_round, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRoundGetter = (RoundGetter) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRoundArrayAdapter = new RoundArrayAdapter(getActivity().getApplicationContext());
        mMatchListView = (ListView) getView().findViewById(R.id.matchList);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()) {
            Log.i("From fragment", "I am round " + (mRoundNumber+1));
            mRound = mRoundGetter.getRound(mRoundNumber);
            mRoundArrayAdapter.setData(mRound.getMatches());
            Log.i("From fragment", "I have " + (mRound.getMatches().size()) + " matches");
            mMatchListView.setAdapter(mRoundArrayAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mRound = mRoundGetter.getRound(mRoundNumber);
        Log.i("Matches in round", ""+mRound.getMatches().size());
        mRoundArrayAdapter.setData(mRound.getMatches());
        mMatchListView.setAdapter(mRoundArrayAdapter);
    }

    public interface RoundGetter {
        PlayOffRound getRound(int round);
        MatchPlayTournament getTournament();
    }

    private class RoundArrayAdapter extends ArrayAdapter<Match> {

        private final LayoutInflater mInflater;

        public RoundArrayAdapter(@NonNull Context context) {
            super(context, R.layout.match_list_item);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<Match> data) {
            clear();

            if(data != null) {
                for( Match appEntry : data ) {
                    add(appEntry);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            View view;

            if(convertView == null) {
                view = mInflater.inflate(R.layout.match_list_item, parent, false);
            }
            else {
                view = convertView;
            }

            final Match match = getItem(position);
            Log.i("match in adapter", "" +  match.getPlayers().size());
            ((TextView) view.findViewById(R.id.matchName)).setText("Match " + (position+1));
            if(match.getPlayers().size() > 0) {
                ((TextView) view.findViewById(R.id.matchplayer1))
                        .setText(match.getPlayers().get(0).getName());
                ((TextView) view.findViewById(R.id.matchplayer2))
                        .setText(match.getPlayers().get(1).getName());
                ((TextView) view.findViewById(R.id.matchplayer1hcp))
                        .setText("Handicap: " + match.getPlayers().get(0).getHandicap());
                ((TextView) view.findViewById(R.id.matchplayer2hcp))
                        .setText("Handicap: " + match.getPlayers().get(1).getHandicap());

                view.findViewById(R.id.addResultsButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("button clicked", "Match " + match.getID());
                        Intent intent = AddResultsActivty.newIntent((PlayOffTreeActivity) getActivity(), match, mRoundGetter.getTournament());
                        intent.putExtra("flag", "IAmFromBracket");
                        startActivity(intent);
                    }
                });
            }
            return view;
        }
    }
}

