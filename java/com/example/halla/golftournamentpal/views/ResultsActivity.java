package com.example.halla.golftournamentpal.views;

import android.app.SearchManager;
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
import android.widget.ListView;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.TournamentArrayAdapter;
import com.example.halla.golftournamentpal.models.Tournament;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager mSessionManager;
    private List<Tournament> mTournamentList = new ArrayList<>();
    private ListView mListView;
    private TournamentArrayAdapter mAdapter;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ResultsActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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

        // Create a new adapter
        mListView = (ListView) findViewById(R.id.tournaments_list);
        mAdapter = new TournamentArrayAdapter(getApplicationContext());

        // Fetch tournaments from database
        FetchTournamentsTask task = new FetchTournamentsTask();
        task.execute();

        // Check if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ResultsActivity.this);
            startActivity(i);
        }

        //Trying to get Search to work
        //https://developer.android.com/guide/topics/search/search-dialog.html
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // doMySearch will be a local method where the actual search operation is done.
            // doMySearch(query); UNCOMMENT THIS WHEN READY
        }

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
            Intent i = HomeActivity.newIntent(ResultsActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(ResultsActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(ResultsActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(ResultsActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(ResultsActivity.this);
            startActivity(r);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Fetch tournaments from database
    private class FetchTournamentsTask extends AsyncTask<Void, Void, List<Tournament>> {

        @Override
        protected List<Tournament> doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            return new Networker().fetchTournaments();

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
