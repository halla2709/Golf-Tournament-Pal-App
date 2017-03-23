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
import com.example.halla.golftournamentpal.models.MatchPlayTournament;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity is where the information about the matchplaytournaments
 */
public class MatchPlayInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;

    private static final String TOURNAMENT_NAME = "tournamentName";
    private static final String TOURNAMENT_COURSE = "tournamentCourse";
    private static final String TOURNAMENT_DATE = "tournamentDate";
    private static final String ARE_BRACKETS = "areBrackets";
    private static final String TOURNAMENT_JSON_PLAYERS = "jsonPlayer";

    private static MatchPlayTournament sMatchPlayTournament;

    GolferArrayAdapter mParticipantAdapter;
    ListView mParticipantListView;
    List<Golfer> mParticipants = new ArrayList<>();


    public static Intent newIntent(Context packageContext, MatchPlayTournament tournament) {
        Intent intent = new Intent(packageContext, MatchPlayInfoActivity.class);
        /*intent.putExtra(TOURNAMENT_NAME, tournament.getName().toString());
        intent.putExtra(TOURNAMENT_COURSE, tournament.getCourse().toString());
        intent.putExtra(TOURNAMENT_DATE, tournament.getStartDate().toString());
        intent.putExtra(ARE_BRACKETS, tournament.isAreBrackets());
        intent.putExtra(TOURNAMENT_JSON_PLAYERS);*/
        sMatchPlayTournament = tournament;
        return intent;
    }

    public interface FriendPasser {
        public void friendClicked(Golfer golfer);

        public List<Golfer> getFriends();

        public List<Golfer> getParticipants();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play_info);
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
            Intent intent = LogInActivity.newIntent(MatchPlayInfoActivity.this);
            startActivity(intent);
        }

        displayInfo();
    }

    public void displayInfo(){

        /*String mTourName = getIntent().getStringExtra(TOURNAMENT_NAME);
        String mCourse = getIntent().getStringExtra(TOURNAMENT_COURSE);
        String mDate = getIntent().getStringExtra(TOURNAMENT_DATE);
        Log.i("TAGG", "Tournament name" + mTourName );


        TextView mTourNameView = (TextView) findViewById(R.id.tourInfoName);
        TextView mCourseView = (TextView) findViewById(R.id.tourInfoCourse);
        TextView mDateView = (TextView) findViewById(R.id.tourInfoDate);

        mTourNameView.setText(mTourName);
        mCourseView.setText(mCourse);
        mDateView.setText(mDate.substring(0,11));*/

        TextView mTourNameView = (TextView) findViewById(R.id.tourInfoName);
        TextView mCourseView = (TextView) findViewById(R.id.tourInfoCourse);
        TextView mDateView = (TextView) findViewById(R.id.tourInfoDate);

        mTourNameView.setText(sMatchPlayTournament.getName());
        mCourseView.setText(sMatchPlayTournament.getCourse());
        mDateView.setText(sMatchPlayTournament.getStartDate().toString().substring(0,11));

        mParticipantAdapter = new GolferArrayAdapter(this.getApplicationContext(), MatchPlayInfoActivity.this);
        mParticipantListView = (ListView) findViewById(R.id.participants_list_matchPlay);

        mParticipants = sMatchPlayTournament.getPlayers();
        mParticipantAdapter.setData(mParticipants);
        mParticipantListView.setAdapter(mParticipantAdapter);
        setListHeight(mParticipantListView);



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



    public void goToPlayOfftree (View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.viewbPlayOffTree);

        Intent intent = PlayOffTreeActivity.newIntent(MatchPlayInfoActivity.this);
        startActivity(intent);
    }

    public void goToBracket(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.viewBracket);

        Intent intent = BracketsActivity.newIntent(MatchPlayInfoActivity.this);
        startActivity(intent);
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
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_myprofile) {
            Intent intent = new Intent(this, MyProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_mytournaments) {
            Intent intent = new Intent(this, MyTournamentsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_myfriends) {
            Intent intent = new Intent(this, MyFriendsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, ResultsActivity.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GolferArrayAdapter extends ArrayAdapter<Golfer> {


        private final LayoutInflater mInflater;
        private MatchPlayInfoActivity mFragment;

        public GolferArrayAdapter(Context context, MatchPlayInfoActivity myFragment) {
            super(context, R.layout.friend_list_item_table);
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
