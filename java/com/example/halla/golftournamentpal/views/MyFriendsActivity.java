package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyFriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private Golfer mGolfer;
    private Long mUserSocial;
    GolferArrayAdapter mParticipantAdapter;
    ListView mParticipantListView;
    List<Golfer> mParticipants = new ArrayList<>();

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MyFriendsActivity.class);
        return i;

    }

    public void viewFriend(Golfer friend){
        Intent i = FriendProfileActivity.newIntent(MyFriendsActivity.this, friend);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(MyFriendsActivity.this);
            startActivity(i);
        }

        mUserSocial = mSessionManager.getSessionUserSocial();

        GetFriendsTask task = new GetFriendsTask();
        task.execute();
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        displayFriends();
    }

    public void displayFriends() {

        mParticipantAdapter = new GolferArrayAdapter(this.getApplicationContext(), MyFriendsActivity.this);
        mParticipantListView = (ListView) findViewById(R.id.myFriendsList);

        mParticipants = mGolfer.getFriends();

        mParticipantAdapter.setData(mParticipants);
        mParticipantListView.setAdapter(mParticipantAdapter);
        setListHeight(mParticipantListView);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_createTournament) {
            Intent i = HomeActivity.newIntent(MyFriendsActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(MyFriendsActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(MyFriendsActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(MyFriendsActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(MyFriendsActivity.this);
            startActivity(r);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void setListHeight(ListView list) {
        MyFriendsActivity.GolferArrayAdapter adapter = (MyFriendsActivity.GolferArrayAdapter) list.getAdapter();
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

    private class GolferArrayAdapter extends ArrayAdapter<Golfer> {


        private final LayoutInflater mInflater;
        private MyFriendsActivity mFragment;

        public GolferArrayAdapter(Context context, MyFriendsActivity myFragment) {
            super(context, R.layout.friend_list_item_table);
            mFragment = myFragment;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<Golfer> data) {
            clear();
            if (data != null) {
                for (Golfer appEntry : data) {
                    add(appEntry);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            View view;

            if (convertView == null) {
                view = mInflater.inflate(R.layout.friend_list_item_table, parent, false);
            } else {
                view = convertView;
            }

            final Golfer friend = getItem(position);
            ((TextView) view.findViewById(R.id.friendName)).setText(friend.getName());
            ((TextView) view.findViewById(R.id.friendSocial)).setText(Long.toString(friend.getSocial()));
            ((TextView) view.findViewById(R.id.friendHandicap)).setText(Double.toString(friend.getHandicap()));
            view.findViewById(R.id.friend_list_layout)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mFragment.viewFriend(friend);
                        }
                    });
            return view;
        }
    }

    private class GetFriendsTask extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            mGolfer = new Networker().fetchGolfer(mUserSocial);
            Log.i("TAGG", "Done fetching");
            return mGolfer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Golfer golfer) {
            super.onPostExecute(golfer);
            Log.i("TAGG", "Done");
        }
    }
}