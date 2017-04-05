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
import com.example.halla.golftournamentpal.models.Scorecard;
import com.example.halla.golftournamentpal.models.Tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScoreboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager mSessionManager;
    private static final int FONT_SIZE = 18;
    private static final String TOURNAMENT_ID = "id";
    private Long mTournamentId;
    private static ScoreboardTournament mTournament;
    private List<Scorecard> mScorecards = new ArrayList<>();

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

    public HashMap<Long, Integer> sortHashMap(ScoreboardTournament tournament){
        HashMap<Long, Integer> hashMap = new HashMap<Long, Integer>() ;
        for (int i=0; i<tournament.getPlayers().size(); i++){
            long social = tournament.getScorecards().get(i).getPlayer().getSocial();
            int total = tournament.getScorecards().get(i).getTotalForScorecard();
            hashMap.put(social,total);
        }

        HashMap<Long, Integer> sortedMap = new HashMap<Long, Integer>() ;
        sortedMap = sortByValues(hashMap);
        Log.i("Hash Map", sortByValues(hashMap).toString());
        return sortedMap;
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public TableLayout fillTable(final ScoreboardTournament tournament, TableLayout tableLayout)
    {
        HashMap<Long, Integer> hashMap = sortHashMap(tournament);
        List<Scorecard> mSortedCards = new ArrayList<Scorecard>();
        mScorecards = tournament.getScorecards();

        Set set = hashMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            long social = Long.parseLong(me.getKey().toString());
            for (int j = 0; j<mScorecards.size(); j++) {
                long socialCheck = mScorecards.get(j).getPlayer().getSocial();
                if (socialCheck == social) {
                    mSortedCards.add(mScorecards.get(j));
                    break;
                }
            }
        }

        for (int x = 0; x < mSortedCards.size(); x++) {
            Log.i("Sorted:", mSortedCards.get(x).getPlayer().getName().toString() + " " + mSortedCards.get(x).getTotalForScorecard()) ;
        }

        tableLayout.setStretchAllColumns(true);

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
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView playerTextViewTable = new TextView(this);
            playerTextViewTable.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
            playerTextViewTable.setText(mSortedCards.get(x).getPlayer().getName());


            String firstName = tournament.getPlayers().get(x).getName();
            if(tournament.getPlayers().get(x).getName().indexOf(" ") > 0)
                firstName = tournament.getPlayers().get(x).getName()
                        .substring(0,tournament.getPlayers().get(x).getName().indexOf(" "));
            playerTextViewTable.setText(firstName);

            tableRow.addView(playerTextViewTable);
            playerTextViewTable.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            playerTextViewTable.setTextColor(getResources().getColor(R.color.buttonTextColor));



            final Scorecard scorecard = mSortedCards.get(x);
            for(int i = 0; i < tournament.getNumberOfRounds(); i++) {
                final int round = i;
                TextView resultTextView = new TextView(this);
                resultTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
                resultTextView.setText(""+scorecard.getRounds().get(i).getTotal());
                resultTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("from rounds ", scorecard.getPlayer().getName().toString());
                        Intent intent = ScoreInputViewActivity.newIntent(getApplicationContext(), tournament, scorecard, scorecard.getRounds().get(round), round+1);
                        startActivity(intent);
                    }
                });
                tableRow.addView(resultTextView);
            }
            TextView pointsTextViewTable = new TextView(this);
            pointsTextViewTable.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
            pointsTextViewTable.setText(Integer.toString(scorecard.getTotalForScorecard()));
            tableRow.addView(pointsTextViewTable);


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
