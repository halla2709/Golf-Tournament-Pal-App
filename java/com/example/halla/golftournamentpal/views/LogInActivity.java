package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.User;
/*
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;*/

public class LogInActivity extends AppCompatActivity {

    private Button mCreateButton;
    private EditText mUserSocial;
    private EditText mUserPassword;
    private SessionManager mSessionManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, LogInActivity.class);
        return i;
    }


    public void login(View view) {
        mCreateButton = (Button) findViewById(R.id.loginbutton);
        mUserSocial = (EditText) findViewById(R.id.loginSSN);
        mUserPassword = (EditText) findViewById(R.id.loginPW);


        if (mUserSocial.getText().length() == 0 || mUserPassword.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT)
                    .show();
        } else if (mSessionManager.startSession(Long.parseLong(mUserSocial.getText().toString()),
                mUserPassword.getText().toString())) {

            if (mSessionManager.getSessionUserSocial() != 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect credientials, try again", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void register(View view) {
        mCreateButton = (Button) findViewById(R.id.registerbutton);

        Intent i = RegisterActivity.newIntent(LogInActivity.this);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mSessionManager = new SessionManager(getApplicationContext());
        if (mSessionManager.getSessionUserSocial() != 0) {
            Intent i = MainActivity.newIntent(LogInActivity.this);
            startActivity(i);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     *
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("LogIn Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/
}
