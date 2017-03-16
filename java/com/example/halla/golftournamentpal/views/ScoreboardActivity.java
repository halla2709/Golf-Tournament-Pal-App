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
import android.view.MenuItem;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class ScoreboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager mSessionManager;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ScoreboardActivity.class);
        return i;
    }

    //When Inputting scores: go to ScoreInputViewActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
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
        if (mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ScoreboardActivity.this);
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

        if(id == R.id.nav_createTournament) {
            Intent i = HomeActivity.newIntent(ScoreboardActivity.this);
            startActivity(i);


        } else if(id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(ScoreboardActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(ScoreboardActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(ScoreboardActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent a = ResultsActivity.newIntent(ScoreboardActivity.this);
            startActivity(a);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
