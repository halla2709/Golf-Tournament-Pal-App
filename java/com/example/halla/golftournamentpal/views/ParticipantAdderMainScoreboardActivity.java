package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class ParticipantAdderMainScoreboardActivity extends AppCompatActivity {

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

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ParticipantAdderMainScoreboardActivity.class);
        return i;
    }

    public void gotonext (View view){
        mCreateButton = (Button) findViewById(R.id.nextStepParticipant);

        Intent i = ScoreboardInfoActivity.newIntent(ParticipantAdderMainScoreboardActivity.this);
        startActivity(i);
    }

    public void addparticipant (View view){
        mCreateButton = (Button) findViewById(R.id.addParticipantButton);

        //Hér kemur virkni fyrir að bæta við participant
    }

    private SessionManager mSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ParticipantAdderMainScoreboardActivity.this);
            startActivity(i);
        }

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
}
