package com.example.halla.golftournamentpal;

import android.net.Uri;
import android.util.Log;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.Tournament;
import com.example.halla.golftournamentpal.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 01-Mar-17.
 */

public class Networker {

    //String BASE_URL = "http://192.168.0.106:8080";
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

    public String postTournamentBytes(String urlSpec, String tournamentJSON) throws IOException {
        URL url = null;
        String charset = "UTF-8";
        try {
            url = new URL(urlSpec);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST"); // PUT is another valid option
        http.setDoOutput(true);

        http.setRequestProperty("Accept-Charset", charset);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        try {
            OutputStream out = http.getOutputStream();


            out.write(tournamentJSON.getBytes(charset));
            InputStream in = http.getInputStream();

            if(http.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(http.getResponseCode() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toString();
        }   finally {
            http.disconnect();
        }
    }

    public String postTournamentString(String urlSpec, String tournamentString) throws IOException {
        return new String(postTournamentBytes(urlSpec, tournamentString));
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


    public Golfer registerGolfer(User userinfo, Golfer golfer) {

        Golfer newgolfer = new Golfer();
        try {
            String url = Uri.parse(BASE_URL+"/json/registerUser")
                    .buildUpon()
                    .appendQueryParameter("social", Long.toString(userinfo.getSocial()))
                    .appendQueryParameter("name", golfer.getName())
                    .appendQueryParameter("email", golfer.getEmail())
                    .appendQueryParameter("handicap", Double.toString(golfer.getHandicap()))
                    .appendQueryParameter("password", userinfo.getPassword())
                    .build()
                    .toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            newgolfer = JsonParser.parseGolfer(jsonBody);
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newgolfer;
    }

    public MatchPlayTournament sendMatchPlayTournament(MatchPlayTournament tournament) {
        Gson gson = new Gson();
        MatchPlayTournament createdTournament = null;
        String tournamentJsonString = gson.toJson(tournament);
        String url = BASE_URL + "/json/matchplay";

        try {
            Log.i(TAG, "Sending tournament to " + url);
            String createdTournamentString = postTournamentString(url, tournamentJsonString);
            JSONObject tournamentJson = new JSONObject(createdTournamentString);
            createdTournament = JsonParser.parseMatchPlay(tournamentJson);
        } catch (IOException e) {
            Log.e(TAG, "Failed to send item ", e);
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createdTournament;
    }
}
