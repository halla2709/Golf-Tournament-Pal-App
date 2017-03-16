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
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class PlayOffTreeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, PlayOffTreeActivity.class);
        return i;
    }

    public void goBack(View view) {
        mCreateButton = (Button) findViewById(R.id.backPlayOffTreeButton);

        //Intent i = MatchPlayInfoActivity.newIntent(PlayOffTreeActivity.this);
        //startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_off_tree);
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
            Intent i = LogInActivity.newIntent(PlayOffTreeActivity.this);
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
            Intent i = HomeActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(r);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
