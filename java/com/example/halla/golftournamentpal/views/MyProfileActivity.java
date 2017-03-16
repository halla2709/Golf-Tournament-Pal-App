package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Tournament;

import java.util.List;

public class MyProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private TextView mGolferName;
    private TextView mGolferSocial;
    private TextView mGolferEmail;
    private SessionManager mSessionManager;
    private EditText mHandicap;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MyProfileActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGolferName = (TextView) findViewById(R.id.golferName);
        mGolferSocial = (TextView) findViewById(R.id.golferSSN);
        mGolferEmail = (TextView) findViewById(R.id.golferEmail);
        mHandicap = (EditText) findViewById(R.id.myHandicap);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSessionManager = new SessionManager(getApplicationContext());
        if (mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(MyProfileActivity.this);
            startActivity(i);
        }

        mGolferName.setText(mSessionManager.getSessionUserName());
        mHandicap.setText(Double.toString(mSessionManager.getSessionUserHandicap()));
        mGolferEmail.setText(mSessionManager.getSessionUserEmail());

        String mGolferString = mSessionManager.getSessionUserSocial().toString();
        if (mGolferString.length() != 10) {
            mGolferSocial.setText("0" + Long.toString(mSessionManager.getSessionUserSocial()));
        } else {
            mGolferSocial.setText(Long.toString(mSessionManager.getSessionUserSocial()));
        }


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mHandicap.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    // Here we have to insert updated hadicap into database
                    Log.v("EditText", mHandicap.getText().toString());
                    Toast.makeText(MyProfileActivity.this, "Handicap updated", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }


    public void seefriends (View view){
        mCreateButton = (Button) findViewById(R.id.seefriendsbutton);

        Intent i = MyFriendsActivity.newIntent(MyProfileActivity.this);
        startActivity(i);
    }

    public void seetournaments (View view){
        mCreateButton = (Button) findViewById(R.id.seetournamentsbutton);

        Intent i = MyTournamentsActivity.newIntent(MyProfileActivity.this);
        startActivity(i);
    }

    public void logOut(View view) {
        mSessionManager = new SessionManager(getApplicationContext());
        mSessionManager.endSession();

        Intent i = LogInActivity.newIntent(MyProfileActivity.this);
        startActivity(i);
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
            Intent i = HomeActivity.newIntent(MyProfileActivity.this);
            startActivity(i);

        } else if (id == R.id.nav_myprofile) {
            Intent n = MyProfileActivity.newIntent(MyProfileActivity.this);
            startActivity(n);

        } else if (id == R.id.nav_mytournaments) {
            Intent m = MyTournamentsActivity.newIntent(MyProfileActivity.this);
            startActivity(m);

        } else if (id == R.id.nav_myfriends) {
            Intent s = MyFriendsActivity.newIntent(MyProfileActivity.this);
            startActivity(s);

        } else if (id == R.id.nav_search) {
            Intent r = ResultsActivity.newIntent(MyProfileActivity.this);
            startActivity(r);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
