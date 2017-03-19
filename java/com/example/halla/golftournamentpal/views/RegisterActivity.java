package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RegisterActivity extends AppCompatActivity {

    private Button mCreateButton;
    private SessionManager mSessionManager;

    private EditText mNewUserName;
    private EditText mNewUserSSN;
    private EditText mNewUserEmail;
    private EditText mNewUserHandicap;
    private EditText mNewUserPW1;
    private EditText mNewUserPW2;

    private Golfer mNewGolfer;
    private User mNewUser;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, RegisterActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mSessionManager = new SessionManager(getApplicationContext());
    }

    public void register(View view) {
        mCreateButton = (Button) findViewById(R.id.registerbutton);

        mNewUserName   = (EditText) findViewById(R.id.newUserName);
        mNewUserSSN = (EditText) findViewById(R.id.newUserSSN);
        mNewUserEmail = (EditText) findViewById(R.id.newUserEmail);
        mNewUserHandicap = (EditText) findViewById(R.id.newUserHandicap);
        mNewUserPW1 = (EditText) findViewById(R.id.newUserPassword1);
        mNewUserPW2 = (EditText) findViewById(R.id.newUserPassword2);
        List<Golfer> mFriends = new ArrayList<Golfer>();

        mNewGolfer = new Golfer(mNewUserName.getText().toString(),
                                    Long.parseLong(mNewUserSSN.getText().toString()),
                                    Double.parseDouble(mNewUserHandicap.getText().toString()),
                                    mNewUserEmail.getText().toString(), mFriends);

        mNewUser = new User(Long.parseLong(mNewUserSSN.getText().toString()), mNewUserPW1.getText().toString());

        // Make a task and execute
        RegisterTask task = new RegisterTask();
        task.execute();
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent i = LogInActivity.newIntent(RegisterActivity.this);
        startActivity(i);
    }

    // Register a new Golfer
    private class RegisterTask extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            Golfer g = new Networker().registerGolfer(mNewUser, mNewGolfer);
            mSessionManager.startRegisterSession(g);
            return g;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Golfer golfer) {

        }
    }
}
