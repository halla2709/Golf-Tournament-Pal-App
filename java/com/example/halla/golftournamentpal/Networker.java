package com.example.halla.golftournamentpal;

import android.net.Uri;
import android.util.Log;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Tournament;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 01-Mar-17.
 */

public class Networker {

    String BASE_URL = "http://10.0.2.2:8080";
    String TAG = "networker";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseCode() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }   finally {
            conn.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Tournament> fetchTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        try {
            String url = BASE_URL + "/json/results";
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONArray jsonBody = new JSONArray(jsonString);
            parseItems(tournaments, jsonBody);
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return tournaments;
    }

    public Golfer fetchGolfer(Long social) {
        Golfer golfer = new Golfer();
        try{
            String url = Uri.parse(BASE_URL+"/json/golfer")
                    .buildUpon()
                    .appendQueryParameter("social", social.toString())
                    .build()
                    .toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            golfer = JsonParser.parseGolfer(jsonBody);
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return golfer;
    }

    private void parseItems(List<Tournament> tournaments, JSONArray jsonArray)
            throws  IOException, JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tournamentJsonObject = jsonArray.getJSONObject(i);

            Tournament tournament = JsonParser.parseTournament(tournamentJsonObject);

            tournaments.add(tournament);
        }
    }

    public Golfer logInGolfer(Long social, String password) {
        Golfer golfer = new Golfer();
        try{

            String url = Uri.parse(BASE_URL+"/json/loginuser")
                    .buildUpon()
                    .appendQueryParameter("social", social.toString())
                    .appendQueryParameter("password", password)
                    .build()
                    .toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            golfer = JsonParser.parseGolfer(jsonBody);
            Log.i(TAG, "Done parsing JSON");

        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return golfer;


    }


    public void sendUserDetails() {
        Tournament testtourn = new Tournament("GRABBI", "HALLA", null, new Date());
        Gson gson = new Gson();
        String jsontestString = gson.toJson(testtourn);

        Log.e("logloglog", jsontestString);


        String testurl = Uri.parse(BASE_URL+"/json/tournament/2")
                .buildUpon()
                .appendQueryParameter("nafn", "Halla")
                .build()
                .toString();
        Log.e("logloglog", testurl);

        try {
            String url = Uri.parse(BASE_URL+"/json/sendayfir")
                    .buildUpon()
                    .appendQueryParameter("nafn", "Halla")
                    .build()
                    .toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        }
    }
}
