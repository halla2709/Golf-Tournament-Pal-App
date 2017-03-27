package com.example.halla.golftournamentpal.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Bracket;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v7.appcompat.R.attr.color;
import static android.support.v7.appcompat.R.attr.colorPrimary;
import static android.support.v7.appcompat.R.attr.colorPrimaryDark;
import static com.example.halla.golftournamentpal.R.id.brackets_list_view;
//import static com.example.halla.golftournamentpal.R.id.brackets_list_view2;

public class BracketsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private static MatchPlayTournament sMatchPlayTournament;
    ListView mBracketListView;
    ListView mBracketListView2;

    ListView mParticipantListView;
    private static List<Bracket> mBrackets = new ArrayList<>();
    private static List<Golfer> mParticipants = new ArrayList<>();
    BracketArrayAdapter mBracketsAdapter;

    private HashMap<Long, Integer> bracketResults;
    private String[][] resultTable;


    public static Intent newIntent(Context packageContext, MatchPlayTournament tournament) {
        Intent i = new Intent(packageContext, BracketsActivity.class);
        mBrackets = tournament.getBrackets();
        mParticipants = tournament.getPlayers();
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brackets);
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
            Intent i = LogInActivity.newIntent(BracketsActivity.this);
            startActivity(i);
        }

        displayInfo();
    }


    public void displayInfo () {

        mBracketsAdapter = new BracketArrayAdapter(this.getApplicationContext(), BracketsActivity.this);
        mBracketListView = (ListView) findViewById(brackets_list_view);
        //mBracketListView2 = (ListView) findViewById(brackets_list_view2);

        mBracketsAdapter.setData(mBrackets);
        mBracketListView.setAdapter(mBracketsAdapter);
        //mBracketListView2.setAdapter(mBracketsAdapter);
        setListHeight(mBracketListView);
        //setListHeight(mBracketListView2);

    }

    public void setListHeight(ListView list) {
        BracketsActivity.BracketArrayAdapter adapter = (BracketsActivity.BracketArrayAdapter) list.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mBrackets.size(); i++) {
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight
                + (list.getDividerHeight() * (list.getCount() - 1));
        list.setLayoutParams(params);
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
            Intent i = HomeActivity.newIntent(BracketsActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent s = MyProfileActivity.newIntent(BracketsActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_mytournaments) {
            Intent n = MyTournamentsActivity.newIntent(BracketsActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_myfriends) {
            Intent m = MyFriendsActivity.newIntent(BracketsActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(BracketsActivity.this);
            startActivity(r);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public TableLayout fillTable(Bracket bracket, TableLayout tableLayout)
    {
        TableRow newRow = new TableRow(this);
        TableRow tableRow = new TableRow(this);
        //HEADER
        //column bracketname
        TextView bracketTextView = new TextView(this);
        bracketTextView.setText(bracket.getName());
        newRow.addView(bracketTextView);

        //column for all players
        for (int x=0; x<bracket.getPlayers().size(); x++) {
            TextView playerTextView = new TextView(this);
            playerTextView.setText(bracket.getPlayers().get(x).getName());
            newRow.addView(playerTextView);
        }
        //column points
        TextView pointsTextView = new TextView(this);
        pointsTextView.setText("Points");
        newRow.addView(pointsTextView);

        //create the NEW ROW
        tableLayout.addView(newRow, new TableLayout.LayoutParams());


        //TABLE
        //column playername
        for (int x=0; x<bracket.getPlayers().size(); x++) {
            TextView playerTextViewTable = new TextView(this);
            playerTextViewTable.setText(bracket.getPlayers().get(x).getName());
            newRow.addView(playerTextViewTable);

            //Bannað dno why
            //tableLayout.addView(tableRow, new TableLayout.LayoutParams());

        }

        tableLayout.addView(tableRow, new TableLayout.LayoutParams());

        return tableLayout;
    }



    private class BracketArrayAdapter extends ArrayAdapter<Bracket> {


        private final LayoutInflater mInflater;
        private BracketsActivity mFragment;

        public BracketArrayAdapter(Context context, BracketsActivity myFragment) {
            super(context, R.layout.bracket_table);
            mFragment = myFragment;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<Bracket> data) {
            clear();
            if(data != null) {
                for( Bracket appEntry : data ) {
                    add(appEntry);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            View view;

            if(convertView == null) {
                view = mInflater.inflate(R.layout.bracket_table, parent, false);
            }
            else {
                view = convertView;
            }

            final Bracket bracket = getItem(position);
            ((TextView)view.findViewById(R.id.friendName)).setText(bracket.getName());
            fillTable(bracket, (TableLayout) view.findViewById(R.id.bracketLayout));
            return view;

        }
    }

   /* private class GetBracketInfoTask extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            //HashMap<String, Object> bracketInfo = new Networker().getBracketInfo(tournametnid, players, brackets);
            Long tournamentId;
            int players;
            int brackets;
            HashMap<String, Object> bracketInfo = new Networker().getBracketInfo(tournamentId, players, brackets);
            Log.i("TAGG", "Done fetching");
            bracketResults = bracketInfo.get("bracketResults");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Golfer golfer) {
            super.onPostExecute(golfer);
            Log.i("TAGG", "Done");
        }
    }
    */
}
