package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
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

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;

import java.util.ArrayList;
import java.util.List;


public class ScoreboardInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private TextView mGolferName;
    private TextView mGolferSocial;
    private TextView mGolferHandicap;
    
    private ScoreboardTournament mScoreboardTournament;
    private static final String SCOREBOARD_TOURNAMENT = "scoreboardtournament";

    GolferArrayAdapter mParticipantAdapter;
    ListView mParticipantListView;
    List<Golfer> mParticipants = new ArrayList<>();



    public static Intent newIntent(Context packageContext, ScoreboardTournament tournament) {
        Intent i = new Intent(packageContext, ScoreboardInfoActivity.class);
        Log.i("FROM scoreboardactivity", tournament.getName() +
                " " + Integer.toString(tournament.getNumberOfRounds()));
        i.putExtra(SCOREBOARD_TOURNAMENT, tournament);
        return i;
    }

    public void viewscoreboard (View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.viewscoreboard);

        Intent i = ScoreboardActivity.newIntent(ScoreboardInfoActivity.this);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_info);

        mGolferName = (TextView) findViewById(R.id.friendName);
        mGolferSocial = (TextView) findViewById(R.id.friendSocial);
        mGolferHandicap = (TextView) findViewById(R.id.friendHandicap);

        // Navigation drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mScoreboardTournament = getIntent().getParcelableExtra(SCOREBOARD_TOURNAMENT);

        //Check if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(i);
        }
        displayInfo();
    }

    public void displayInfo() {
        TextView mTourNameView = (TextView) findViewById(R.id.tourInfoName);
        TextView mCourseView = (TextView) findViewById(R.id.tourInfoCourse);
        TextView mDateView = (TextView) findViewById(R.id.tourInfoDate);

        mTourNameView.setText(mScoreboardTournament.getName());
        mCourseView.setText(mScoreboardTournament.getCourse());
        mDateView.setText(mScoreboardTournament.getStartDate().toString().substring(0,11));

        mParticipantAdapter = new GolferArrayAdapter(this.getApplicationContext(), ScoreboardInfoActivity.this);
        mParticipantListView = (ListView) findViewById(R.id.participants_list_scoreboard);

        mParticipants = mScoreboardTournament.getPlayers();
        mParticipantAdapter.setData(mParticipants);
        mParticipantListView.setAdapter(mParticipantAdapter);
        setListHeight(mParticipantListView);
    }

    public void setListHeight (ListView list) {
        GolferArrayAdapter adapter = (GolferArrayAdapter) list.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mParticipants.size(); i++){
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight + (list.getDividerHeight() * (list.getCount() -1));
        list.setLayoutParams(params);
    }

    // Navigation drawer
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
            Intent i = HomeActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(r);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GolferArrayAdapter extends ArrayAdapter<Golfer>{

        private  final LayoutInflater mInflater;
        private ScoreboardInfoActivity mFragment;

        public GolferArrayAdapter(Context context, ScoreboardInfoActivity myFragment){
            super(context, R.layout.friend_list_item_table);
            myFragment = myFragment;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<Golfer> data){
            clear();
            if (data != null){
                for ( Golfer appEntry : data) {
                    add(appEntry);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            View view;

            if(convertView == null) {
                view = mInflater.inflate(R.layout.friend_list_item_table, parent, false);
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
