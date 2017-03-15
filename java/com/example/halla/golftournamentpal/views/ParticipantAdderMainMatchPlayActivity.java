package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParticipantAdderMainMatchPlayActivity extends AppCompatActivity
        implements ParticipantTab1MatchPlayActivity.FriendPasser{

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

    private static final String TOURNAMENT_NAME = "tournamentName";
    private static final String TOURNAMENT_COURSE = "tournamentCourse";
    private static final String TOURNAMENT_DATE = "tournamentDate";
    private static final String ARE_BRACKETS = "areBrackets";
    private static final String TOURNAMENT_B_PARTICIPANTS = "bracketParticipants";
    private static final String TOURNAMENT_B_EXITS = "bracketExits";

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private Long mUserSocial;

    private List<Golfer> friends;
    MatchPlayTournament newTournament;

    DateFormat df = new SimpleDateFormat("dd MM yyyy");


    public static Intent newIntent(Context packageContext, MatchPlayTournament tournament,
                                   int bracketPart, int bracketExits, String dateString) {
        Intent intent = new Intent(packageContext, ParticipantAdderMainMatchPlayActivity.class);
        intent.putExtra(TOURNAMENT_NAME, tournament.getName());
        intent.putExtra(TOURNAMENT_COURSE, tournament.getCourse());
        intent.putExtra(TOURNAMENT_DATE, dateString);
        intent.putExtra(ARE_BRACKETS, tournament.isAreBrackets());
        intent.putExtra(TOURNAMENT_B_PARTICIPANTS, bracketPart);
        intent.putExtra(TOURNAMENT_B_EXITS, bracketExits);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
        else {
            mUserSocial = mSessionManager.getSessionUserSocial();
        }


        setContentView(R.layout.activity_participant_adder_main_fragment_matchplay);

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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean tarebrackets = getIntent().getBooleanExtra(ARE_BRACKETS, false);

        newTournament = new MatchPlayTournament(tcourse, tname, null, tdate, tarebrackets, null, null);

    }

    public void gotonext (View view){
        mCreateButton = (Button) findViewById(R.id.nextStepParticipant);

        Intent intent = new Intent(this, MatchPlayInfoActivity.class);
        startActivity(intent);
    }

    public void addParticipant (Golfer golfer){
        //Hér kemur virkni fyrir að bæta við participant
        return;
    }

    @Override
    public void friendClicked(Golfer golfer) {
        addParticipant(golfer);
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
            Log.d("VINARGL", ""+friends.size());
            return friends;
        }
        return friends;
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
                    ParticipantTab1MatchPlayActivity part1 = new ParticipantTab1MatchPlayActivity();
                    return part1;
                case 1:
                    ParticipantTab2MatchPlayActivity part2 = new ParticipantTab2MatchPlayActivity();
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
}
