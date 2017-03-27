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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.TournamentArrayAdapter;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;
import com.example.halla.golftournamentpal.models.Tournament;

import java.util.ArrayList;
import java.util.List;

public class MyTournamentsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TournamentArrayAdapter.CallBacker{

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private List<Tournament> mTournamentList = new ArrayList<>();
    private ListView mListView;
    private TournamentArrayAdapter mAdapter;

    private Long mTournamentID;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MyTournamentsActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournaments);

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

        // Create an adapter to fetch tournaments / Make a task and execute
        mListView = (ListView) findViewById(R.id.my_tournaments_list);
        mAdapter = new TournamentArrayAdapter(getApplicationContext(), MyTournamentsActivity.this);

        // Checking if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(MyTournamentsActivity.this);
            startActivity(i);
        }

        MyTournamentsActivity.FetchMyTournamentsTask task = new MyTournamentsActivity.FetchMyTournamentsTask();
        task.execute();
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
            Intent i = HomeActivity.newIntent(MyTournamentsActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(MyTournamentsActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(MyTournamentsActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(MyTournamentsActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(MyTournamentsActivity.this);
            startActivity(r);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void openTournament(Long id) {
        mTournamentID = id;
        FetchOneTournamentTask task = new FetchOneTournamentTask();
        task.execute();
    }

    private class FetchOneTournamentTask extends AsyncTask<Void, Void, Tournament> {

        @Override
        protected Tournament doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            return new Networker().fetchTournament(mTournamentID);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Tournament tournament) {
            if(tournament instanceof ScoreboardTournament) {
                Intent intent = ScoreboardInfoActivity.newIntent(MyTournamentsActivity.this,
                        (ScoreboardTournament) tournament);
                startActivity(intent);
            }
            else if(tournament instanceof MatchPlayTournament) {
                Intent intent = MatchPlayInfoActivity.newIntent(MyTournamentsActivity.this,
                        (MatchPlayTournament) tournament);
                startActivity(intent);
            }
        }
    }

    // Fetch a list of tournaments from the database.
    private class FetchMyTournamentsTask extends AsyncTask<Void, Void, List<Tournament>> {

        @Override
        protected List<Tournament> doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            return new Networker().fetchMyTournaments(Long.toString(mSessionManager.getSessionUserSocial()));

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(List<Tournament> tournaments) {
            mTournamentList = tournaments;
            mAdapter.setData(mTournamentList);
            mListView.setAdapter(mAdapter);
            Log.i("TAGG", tournaments.get(0).getStartDate().toString());

        }
    }
}