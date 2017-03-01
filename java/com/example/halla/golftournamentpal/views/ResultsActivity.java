package com.example.halla.golftournamentpal.views;

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

public class ResultsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
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
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
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
