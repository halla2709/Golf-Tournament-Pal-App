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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;

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

    public static Intent newIntent(Context packageContext, MatchPlayTournament tournament) {
        Intent intent = new Intent(packageContext, MatchPlayInfoActivity.class);
        intent.putExtra(TOURNAMENT_NAME, tournament.getName().toString());
        intent.putExtra(TOURNAMENT_COURSE, tournament.getCourse().toString());
        intent.putExtra(TOURNAMENT_DATE, tournament.getStartDate().toString());
        intent.putExtra(ARE_BRACKETS, tournament.isAreBrackets());
        return intent;
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

        String mTourName = getIntent().getStringExtra("tournamentName");
        String mCourse = getIntent().getStringExtra("tournamentCourse");
        String mDate = getIntent().getStringExtra("tournamentDate");
        Log.i("TAGG", "Tournament name" + mTourName );

/*
        TextView mTourNameView = (TextView) findViewById(R.id.tourInfoName);
        TextView mCourseView = (TextView) findViewById(R.id.tourInfoCourse);
        TextView mDateView = (TextView) findViewById(R.id.tourInfoDate);

        mTourNameView.setText(mTourName);
        mCourseView.setText(mCourse);
        mDateView.setText(mDate.substring(0,11));
*/
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
}
