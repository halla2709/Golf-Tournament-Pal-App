package com.example.halla.golftournamentpal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.halla.golftournamentpal.models.Tournament;

import java.util.List;

/**
 * Created by Halla on 02-Mar-17.
 */

public class TournamentArrayAdapter extends ArrayAdapter<Tournament> {

    private final LayoutInflater mInflater;

    public TournamentArrayAdapter(Context context) {
        super(context, R.layout.tournament_list_item);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Tournament> data) {
        clear();
        if(data != null) {
            for( Tournament appEntry : data ) {
                add(appEntry);
            }
        }
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

        Tournament tournament = getItem(position);
        ((TextView)view.findViewById(R.id.tournament_name)).setText(tournament.getName());
        ((TextView)view.findViewById(R.id.tournament_course)).setText(tournament.getCourse());
        ((TextView)view.findViewById(R.id.tournament_date)).setText(tournament.getStartDate().toString());
        view.findViewById(R.id.tournament_list_layout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("logging from list", ((TextView)view.findViewById(R.id.tournament_name)).getText().toString());
                    }
                });
        return view;
    }
}
