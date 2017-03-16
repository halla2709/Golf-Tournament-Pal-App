package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.TournamentArrayAdapter;
import com.example.halla.golftournamentpal.models.Tournament;

import java.util.ArrayList;
import java.util.List;


public class ScoreboardInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private TextView mGolferName;
    private TextView mGolferSocial;
    private TextView mGolferHandicap;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ScoreboardInfoActivity.class);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mGolferName = (TextView) findViewById(R.id.friendName);
        mGolferSocial = (TextView) findViewById(R.id.friendSocial);
        mGolferHandicap = (TextView) findViewById(R.id.friendHandicap);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ScoreboardInfoActivity.this);
            startActivity(i);
        }
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
}
