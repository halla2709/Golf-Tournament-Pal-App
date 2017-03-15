package com.example.halla.golftournamentpal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Tournament;

import java.util.List;

/**
 * Created by Halla on 15-Mar-17.
 */

public class FriendArrayAdapter extends ArrayAdapter<Golfer> {


    private final LayoutInflater mInflater;

    public FriendArrayAdapter(Context context) {
        super(context, R.layout.friend_list_item);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.friend_list_item, parent, false);
        }
        else {
            view = convertView;
        }

        Golfer friend = getItem(position);
        ((TextView)view.findViewById(R.id.friendName)).setText(friend.getName());
        ((TextView)view.findViewById(R.id.friendSocial)).setText(Long.toString(friend.getSocial()));
        ((TextView)view.findViewById(R.id.friendHandicap)).setText(Double.toString(friend.getHandicap()));
        view.findViewById(R.id.friend_list_layout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("logging from list", ((TextView)view.findViewById(R.id.friendName)).getText().toString());
                    }
                });
        return view;
    }
}

