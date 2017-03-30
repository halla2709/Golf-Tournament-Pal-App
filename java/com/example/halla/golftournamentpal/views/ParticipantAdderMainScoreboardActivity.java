package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.halla.golftournamentpal.JsonParser;
import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This activity has tabbed view, one tab is to add friends of the golfer to the tournament
 * the other one is to add other users.
 */
public class ParticipantAdderMainScoreboardActivity extends AppCompatActivity
        implements ParticipantTab1ScoreboardActivity.FriendPasser,
        ParticipantTab2ScoreboardActivity.GolferPasser{


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Button mCreateButton;

    private static final String TOURNAMENT_NAME = "tournamentName";
    private static final String TOURNAMENT_COURSE = "tournamentCourse";
    private static final String TOURNAMENT_DATE = "tournamentDate";
    private static final String TOURNAMENT_ROUNDS = "tournamentRounds";
    private final String STORED_TOURNAMENT = "tournamentStored";

    private Long mUserSocial;
    private List<Golfer> friends;
    private Golfer host;
    ScoreboardTournament newTournament;

    DateFormat df = new SimpleDateFormat("dd MM yyyy");

    public static Intent newIntent(Context packageContext, ScoreboardTournament tournament, String dateString, int numberOfRounds) {
        Intent i = new Intent(packageContext, ParticipantAdderMainScoreboardActivity.class);
        i.putExtra(TOURNAMENT_NAME, tournament.getName());
        i.putExtra(TOURNAMENT_COURSE, tournament.getCourse());
        i.putExtra(TOURNAMENT_DATE, dateString);
        i.putExtra(TOURNAMENT_ROUNDS, numberOfRounds);

        return i;
    }

    public void gotonext (View view){
        SaveTournamentTask task = new SaveTournamentTask();
        task.execute();
    }

    public void addparticipant (View view){
        mCreateButton = (Button) findViewById(R.id.addParticipantButton);

        //Hér kemur virkni fyrir að bæta við participant
    }

    private SessionManager mSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = LogInActivity.newIntent(ParticipantAdderMainScoreboardActivity.this);
            startActivity(intent);
        }
        else{
            mUserSocial = mSessionManager.getSessionUserSocial();
        }

        setContentView(R.layout.activity_participant_adder_main_fragment_scoreboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        String tname = getIntent().getStringExtra(TOURNAMENT_NAME);
        String tcourse = getIntent().getStringExtra(TOURNAMENT_COURSE);
        Date tdate = new Date();
        try {
            tdate = df.parse(getIntent().getStringExtra(TOURNAMENT_DATE));
        } catch (ParseException e){
            e.printStackTrace();
        }

        GetFriendsTask task = new GetFriendsTask();
        task.execute();
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        newTournament = new ScoreboardTournament(0L, tcourse, tname, null, tdate, null, 0, null);

        if (savedInstanceState != null) {
            Log.i("SAVED", "yes");
            if(savedInstanceState.getBoolean(STORED_TOURNAMENT)){
                Log.i("Tournament", "yes");
                try{
                    JSONObject tournamentJson = new JSONObject(mSessionManager.getStoredTournament());
                    newTournament = JsonParser.parseScoreboard(tournamentJson);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void  onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String jsonStringTournament = "";
        try {
            jsonStringTournament = JsonParser.scoreboardTournamentToString(newTournament);
        } catch (JSONException e){
            e.printStackTrace();
        }
        new SessionManager(getApplicationContext()).storeTournament(jsonStringTournament);
        outState.putBoolean(STORED_TOURNAMENT,true);
    }


    public void participantAdded (View view){

        String golfername = ((EditText) findViewById(R.id.participantNameInput)).getText().toString();
        String golfermail = ((EditText) findViewById(R.id.participantMail)).getText().toString();
        Long golferSocial = (Long.parseLong(((EditText) findViewById(R.id.participantSocialNumber)).getText().toString()));
        double golferhandicap = (Double.parseDouble(((EditText) findViewById(R.id.participantHandicap)).getText().toString()));
        if(golfername != null && golfermail != null && golferSocial != 0)
            newTournament.addPlayer(new Golfer(golfername, golferSocial, golferhandicap, golfermail, null));

        ((EditText) findViewById(R.id.participantNameInput)).setText("");
        ((EditText) findViewById(R.id.participantMail)).setText("");
        ((EditText) findViewById(R.id.participantSocialNumber)).setText("");
        ((EditText) findViewById(R.id.participantHandicap)).setText("");

    }

    @Override
    public void friendClicked(Golfer golfer) {

        newTournament.addPlayer(golfer);

    }

    @Override
    public List<Golfer> getFriends() {
        if(friends == null) {
            GetFriendsTask task = new GetFriendsTask();
            task.execute();
            try {
                task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        if(newTournament.getPlayers().size() > 0) {
            List<Golfer> found = new ArrayList<>();
            for(Golfer friend : friends){
                for(Golfer participant : newTournament.getPlayers()) {
                    if(friend.getSocial() == participant.getSocial()) {
                        Log.i("Friend in tournament", friend.getName());
                        found.add(friend);
                        break;
                    }
                }
            }
            friends.removeAll(found);
        }
        return friends;
    }

    @Override
    public List<Golfer> getParticipants() {
        return newTournament.getPlayers();
    }

    @Override
    public List<Golfer> hostAdder(boolean added) {
        if(added) {
            newTournament.addPlayer(host);
        }
        else {
            newTournament.getPlayers().remove(host);
        }
        return newTournament.getPlayers();
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ParticipantTab1ScoreboardActivity part1 = new ParticipantTab1ScoreboardActivity();
                    return part1;
                case 1:
                    ParticipantTab2ScoreboardActivity part2 = new ParticipantTab2ScoreboardActivity();
                    return part2;
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add Friends";
                case 1:
                    return "Add New";
            }
            return null;
        }
    }

    private class GetFriendsTask extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            Golfer golfer = new Networker().fetchGolfer(mUserSocial);
            host = golfer;
            friends = golfer.getFriends();
            Log.i("TAGG", "Done fetching");
            return golfer;
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

    private class SaveTournamentTask extends  AsyncTask<Void, Void, ScoreboardTournament>{

        @Override
        protected ScoreboardTournament doInBackground(Void... params){
            Log.i("TAGG", "Fetching...");
            return  new Networker().sendScoreboardTournament(newTournament, mSessionManager.getSessionUserSocial());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(ScoreboardTournament tournament){
            super.onPostExecute(tournament);
            Intent intent = ScoreboardInfoActivity.newIntent(ParticipantAdderMainScoreboardActivity.this, tournament);
            startActivity(intent);
            Log.i("TAGG", "Done");
        }

    }
}
