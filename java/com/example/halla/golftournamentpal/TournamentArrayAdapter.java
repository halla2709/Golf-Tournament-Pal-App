package com.example.halla.golftournamentpal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.halla.golftournamentpal.models.Tournament;
import com.example.halla.golftournamentpal.views.ResultsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 02-Mar-17.
 */

public class TournamentArrayAdapter extends ArrayAdapter<Tournament> {

    private final LayoutInflater mInflater;
    private TournamentFilter mTournamentFilter;
    private List<Tournament> mTournamentList;
    private List<Tournament> mData;
    private CallBacker mCallBacker;

    public TournamentArrayAdapter(Context context, AppCompatActivity activity) {
        super(context, R.layout.tournament_list_item);
        mCallBacker = (CallBacker) activity;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Tournament> data) {
        clear();
        mTournamentList = data;
        mData = data;
        if(data != null) {
            for( Tournament appEntry : data ) {
                add(appEntry);
            }
        }
    }

    @Nullable
    @Override
    public Tournament getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.tournament_list_item, parent, false);
        }
        else {
            view = convertView;
        }

        final Tournament tournament = getItem(position);
        ((TextView)view.findViewById(R.id.tournament_name)).setText(tournament.getName());
        ((TextView)view.findViewById(R.id.tournament_course)).setText(tournament.getCourse());
        ((TextView)view.findViewById(R.id.tournament_date)).setText(tournament.getStartDate().toString());
        view.findViewById(R.id.tournament_list_layout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("TournamentAdapter", tournament.getName());
                        mCallBacker.openTournament(tournament.getId());
                    }
                });
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(mTournamentFilter == null) {
            mTournamentFilter = new TournamentFilter();
        }
        return mTournamentFilter;
    }

    private class TournamentFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence != null && charSequence.length() > 0) {
                Log.d("FILTERING", charSequence.toString());
                List<Tournament> filterList = new ArrayList<>();
                for(int i = 0; i < mTournamentList.size(); i++) {
                    if(mTournamentList.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filterList.add(mTournamentList.get(i));
                }
                results.count = filterList.size();
                results.values = filterList;
            }
            else {
                results.count = mTournamentList.size();
                results.values = mTournamentList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData = (List) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public interface CallBacker {
        void openTournament(Long id);
    }
}
