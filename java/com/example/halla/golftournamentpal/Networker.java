package com.example.halla.golftournamentpal;

import android.net.Uri;
import android.util.Log;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Tournament;

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
            String url = BASE_URL + "/results";
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

    private void parseItems(List<Tournament> tournaments, JSONArray jsonArray)
            throws  IOException, JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tournamentJsonObject = jsonArray.getJSONObject(i);

            Tournament tournament = new Tournament();

            tournament.setCourse(tournamentJsonObject.getString("course"));
            tournament.setName(tournamentJsonObject.getString("name"));
            tournament.setStartDate(new Date(tournamentJsonObject.getLong("startDate")));

            JSONArray playersJsonArray = tournamentJsonObject.getJSONArray("players");
            for (int j = 0; j < playersJsonArray.length(); j++) {
                JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
                Golfer golfer = new Golfer();

                golfer.setName(playerJsonObject.getString("name"));
                golfer.setEmail(playerJsonObject.getString("email"));
                golfer.setSocial(playerJsonObject.getLong("social"));
                golfer.setHandicap(playerJsonObject.getDouble("handicap"));

                tournament.addPlayer(golfer);
            }

            tournaments.add(tournament);
        }

    }
}
