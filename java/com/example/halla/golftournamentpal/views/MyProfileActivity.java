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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MyProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mCreateButton;
    private TextView mGolferName;
    private TextView mGolferSocial;
    private TextView mGolferEmail;
    private SessionManager mSessionManager;
    private EditText mHandicap;
    private double mUpdatedHandicap;
    private long mSocialLong;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MyProfileActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mGolferName = (TextView) findViewById(R.id.golferName);
        mGolferSocial = (TextView) findViewById(R.id.golferSSN);
        mGolferEmail = (TextView) findViewById(R.id.golferEmail);
        mHandicap = (EditText) findViewById(R.id.myHandicap);

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

        // Checking if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if (mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(MyProfileActivity.this);
            startActivity(i);
        }

        // Displaying user-info
        mGolferName.setText(mSessionManager.getSessionUserName());
        mHandicap.setText(Double.toString(mSessionManager.getSessionUserHandicap()));
        mGolferEmail.setText(mSessionManager.getSessionUserEmail());


        // Displaying "0" in front of users social number if necessary
        String mGolferString = mSessionManager.getSessionUserSocial().toString();
        if (mGolferString.length() != 10) {
            mGolferSocial.setText("0" + Long.toString(mSessionManager.getSessionUserSocial()));
        } else {
            mGolferSocial.setText(Long.toString(mSessionManager.getSessionUserSocial()));
        }
        //Cursor in handicap editText
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mHandicap.setCursorVisible(false);
    }

    public void handicapListener (View view) {
        mHandicap.setCursorVisible(true);
    }

    public void changehandicap (View view){
        mUpdatedHandicap = Double.parseDouble(mHandicap.getText().toString());
        mSocialLong = Long.parseLong(mGolferSocial.getText().toString());

        // Make a task and execute
        MyProfileActivity.updateHandicapTask task = new MyProfileActivity.updateHandicapTask();
        task.execute();
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mSessionManager.setSessionHandicap(mUpdatedHandicap);
        Toast.makeText(MyProfileActivity.this, "Handicap updated", Toast.LENGTH_SHORT).show();

        //Hide SoftInputKeyboard
        View views = this.getCurrentFocus();
        if (views != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            mHandicap.setCursorVisible(false);
        }

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

    private class updateHandicapTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            try {
                new Networker().updateHandicap(mSocialLong, mUpdatedHandicap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        protected void onPostExecute(boolean bool) {
            super.onPostExecute(bool);

            Log.i("TAGG", "Done");

        }
    }


}
