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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
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
//import static com.example.halla.golftournamentpal.R.id.brackets_list_view2;

public class BracketsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private ListView mBracketListView;
    private TextView mNoBrackets;

    ListView mParticipantListView;
    private static final String BRACKETS = "brackets";
    private static final String PARTICIPANTS = "participants";
    private static final String TOURNAMENT_ID = "id";

    private List<Bracket> mBrackets = new ArrayList<>();
    private List<Golfer> mParticipants = new ArrayList<>();
    private Long mTournamentid;

    BracketArrayAdapter mBracketsAdapter;

    private HashMap<Long, Integer> bracketResults;
    private String[][] resultTable;


    public static Intent newIntent(Context packageContext, MatchPlayTournament tournament) {
        Intent i = new Intent(packageContext, BracketsActivity.class);
        i.putExtra(TOURNAMENT_ID, tournament.getId());
        i.putParcelableArrayListExtra(BRACKETS, (ArrayList) tournament.getBrackets());
        i.putParcelableArrayListExtra(PARTICIPANTS, (ArrayList) tournament.getPlayers());
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

        mBracketsAdapter = new BracketArrayAdapter(this.getApplicationContext(), BracketsActivity.this);
        mBracketListView = (ListView) findViewById(R.id.brackets_list_view);
        mNoBrackets = (TextView) findViewById(R.id.noBrackets);

        mSessionManager = new SessionManager(getApplicationContext());
        if (mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(BracketsActivity.this);
            startActivity(i);
        }

        mBrackets = getIntent().getParcelableArrayListExtra(BRACKETS);
        mParticipants = getIntent().getParcelableArrayListExtra(PARTICIPANTS);
        mTournamentid = getIntent().getLongExtra(TOURNAMENT_ID, 0);

        if(mBrackets != null) {
            GetBracketInfoTask task = new GetBracketInfoTask();
            task.execute();
        }

        else displayNoBrackets();

    }


    public void displayInfo () {
        mBracketListView.setVisibility(View.VISIBLE);
        mNoBrackets.setVisibility(View.GONE);
        mBracketsAdapter.setData(mBrackets);
        mBracketListView.setAdapter(mBracketsAdapter);
        setListHeight(mBracketListView);

    }

    public void displayNoBrackets() {
        mBracketListView.setVisibility(View.GONE);
        mNoBrackets.setVisibility(View.VISIBLE);
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
        tableLayout.setStretchAllColumns(true);

        Log.i("players in b", Integer.toString(bracket.getPlayers().size()));
        Log.i("bracket name", bracket.getName());
        Log.i("bracket results", bracketResults.toString());
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        //HEADER
        //column bracketname
        TextView bracketTextView = new TextView(this);
        bracketTextView.setText(bracket.getName());
        headerRow.addView(bracketTextView);
        bracketTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        bracketTextView.setTextColor(getResources().getColor(R.color.buttonTextColor));

        //column for all players
        for (int x=0; x<bracket.getPlayers().size(); x++) {
            TextView playerTextView = new TextView(this);
            playerTextView.setText(bracket.getPlayers().get(x).getName());
            headerRow.addView(playerTextView);
            playerTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            playerTextView.setTextColor(getResources().getColor(R.color.buttonTextColor));

        }

        //column points
        TextView pointsTextView = new TextView(this);
        pointsTextView.setText("Points");
        headerRow.addView(pointsTextView);

        pointsTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        pointsTextView.setTextColor(getResources().getColor(R.color.buttonTextColor));

        //create the NEW ROW
        tableLayout.addView(headerRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        //TABLE
        //column playername
        for (int x=0; x < bracket.getPlayers().size(); x++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT

            ));

            TextView playerTextViewTable = new TextView(this);
            playerTextViewTable.setText(bracket.getPlayers().get(x).getName());
            tableRow.addView(playerTextViewTable);
            playerTextViewTable.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            playerTextViewTable.setTextColor(getResources().getColor(R.color.buttonTextColor));

            for(int i = 0; i < bracket.getPlayers().size(); i++) {
                String result = resultTable[x][i];
                TextView resultTextView = new TextView(this);
                if(result == null) resultTextView.setText("-");
                else if(result.equals("np")) {
                    final String names = bracket.getPlayers().get(x).getName() + " vs " + bracket.getPlayers().get(i).getName();
                    resultTextView.setText("Add results");
                    resultTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("from a bracket", names);
                        }
                    });
                }
                else resultTextView.setText(result);
                tableRow.addView(resultTextView);
            }

            TextView playerPointsTextView = new TextView(this);
            //Log.i("player in bracket", bracketResults.get(2709942619L).toString());
            playerPointsTextView.setText(bracketResults
                    .get(bracket.getPlayers().get(x).getSocial()).toString());

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(new LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )));

        }

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

    private class GetBracketInfoTask extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            HashMap<String, Object> bracketInfo = new Networker().getBracketInfo(mTournamentid);
            Log.i("TAGG", "Done fetching");
            bracketResults = (HashMap) bracketInfo.get("bracketResults");
            resultTable = (String[][]) bracketInfo.get("resultTable");
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
            displayInfo();
            Log.i("TAGG", "Done");
        }
    }
}
