package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.PlayOffRound;
import com.example.halla.golftournamentpal.models.PlayOffTree;

public class PlayOffTreeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PlayOffRoundFragment.RoundGetter {


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
    private SessionManager mSessionManager;

    private static final String PLAYOFF_TREE = "playofftree";
    private PlayOffTree mPlayOffTree;

    public static Intent newIntent(Context packageContext, MatchPlayTournament tournament) {
        Intent i = new Intent(packageContext, PlayOffTreeActivity.class);
        Log.i("log from intent", Integer.toString(tournament.getPlayOffs().getRounds().size()));
        i.putExtra(PLAYOFF_TREE, tournament.getPlayOffs());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_off_tree);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPlayOffTree = getIntent().getParcelableExtra(PLAYOFF_TREE);
        Log.i("Tag", Integer.toString(mPlayOffTree.getRounds().size()));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mPlayOffTree.getRounds().size());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.playoffcontainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.playofftabs);
        tabLayout.setupWithViewPager(mViewPager);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(i);
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
            Intent i = HomeActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(PlayOffTreeActivity.this);
            startActivity(r);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public PlayOffRound getRound(int round) {
        return null;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int mNumberOfRounds;

        public SectionsPagerAdapter(FragmentManager fm, int numberOfRounds) {
            super(fm);
            mNumberOfRounds = numberOfRounds;
        }


        @Override
        public Fragment getItem(int position) {
            if(position < mNumberOfRounds) {
                PlayOffRoundFragment playOffRoundFragment = new PlayOffRoundFragment();
                playOffRoundFragment.setRoundNumber(position);
                return playOffRoundFragment;
            }

            return null;

        }

        @Override
        public int getCount() {
            return mNumberOfRounds;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < mNumberOfRounds) return "Round " + (position+1);
            return null;
        }
    }
}
