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
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;
import com.example.halla.golftournamentpal.models.Tournament;

public class ScoreboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager mSessionManager;
    private static final int FONT_SIZE = 18;
    private static final String TOURNAMENT_ID = "id";
    private Long mTournamentId;
    private ScoreboardTournament mTournament;

    public static Intent newIntent(Context packageContext, ScoreboardTournament tournament) {
        Intent i = new Intent(packageContext, ScoreboardActivity.class);
        i.putExtra(TOURNAMENT_ID, tournament.getId());
        return i;
    }

    //When Inputting scores: go to ScoreInputViewActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

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

        // Check if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if (mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ScoreboardActivity.this);
            startActivity(i);
        }

        mTournamentId = getIntent().getLongExtra(TOURNAMENT_ID, 0);
        ScoreboardInfoTask task = new ScoreboardInfoTask();
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

    /*public View getView(int position, View convertView, final ViewGroup parent) {
        View view;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.bracket_table, parent, false);
        }
        else {
            view = convertView;
        }

        final Bracket bracket = getItem(position);
        ((TextView)view.findViewById(R.id.friendName)).setText(bracket.getName());
        fillTable(mTournament, (TableLayout) view.findViewById(R.id.bracketLayout));
        return view;

    }*/

    public TableLayout fillTable(ScoreboardTournament tournament, TableLayout tableLayout)
    {
        tableLayout.setStretchAllColumns(true);

        /*Log.i("players in b", Integer.toString(bracket.getPlayers().size()));
        Log.i("bracket name", bracket.getName());
        Log.i("bracket results", bracketResults.toString()); */
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        //HEADER
        //column player
        TextView playerTextView = new TextView(this);
        playerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
        playerTextView.setText("Player");
        headerRow.addView(playerTextView);
        playerTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        playerTextView.setTextColor(getResources().getColor(R.color.buttonTextColor));

        //column for all rounds
        for (int x=1; x<tournament.getNumberOfRounds()+1; x++) {
            TextView roundTextView = new TextView(this);
            roundTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
            roundTextView.setText(" Round " + x + " ");
            headerRow.addView(roundTextView);
            roundTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            roundTextView.setTextColor(getResources().getColor(R.color.buttonTextColor));

        }

        //column points
        TextView pointsTextView = new TextView(this);
        pointsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
        pointsTextView.setText(" Points ");
        headerRow.addView(pointsTextView);

        pointsTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        pointsTextView.setTextColor(getResources().getColor(R.color.buttonTextColor));

        //create the NEW ROW
        tableLayout.addView(headerRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        //TABLE
        //column playername
        for (int x=0; x < tournament.getPlayers().size(); x++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT

            ));

            TextView playerTextViewTable = new TextView(this);
            playerTextViewTable.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
            playerTextViewTable.setText(tournament.getPlayers().get(x).getName());
            tableRow.addView(playerTextViewTable);
            playerTextViewTable.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            playerTextViewTable.setTextColor(getResources().getColor(R.color.buttonTextColor));

            for(int i = 0; i < tournament.getNumberOfRounds(); i++) {
                TextView resultTextView = new TextView(this);
                resultTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
                final String name = tournament.getPlayers().get(x).getName();
                resultTextView.setText(" 0 ");
                resultTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("from rounds ", name);
                    }
                });
                tableRow.addView(resultTextView);
            }

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )));
        }

        return tableLayout;
    }

    private class ScoreboardInfoTask extends AsyncTask<Void, Void, ScoreboardTournament> {

        @Override
        protected ScoreboardTournament doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            Tournament tournament = new Networker().fetchTournament(mTournamentId);
            Log.i("TAGG", "Done fetching");
            mTournament = (ScoreboardTournament) tournament;
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(ScoreboardTournament tournament) {
            super.onPostExecute(tournament);
            fillTable(mTournament, (TableLayout) findViewById(R.id.scoreboardLayout));
            Log.i("TAGG", "Done");
        }
    }

}
