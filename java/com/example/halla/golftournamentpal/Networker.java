package com.example.halla.golftournamentpal;

import android.net.Uri;
import android.util.Log;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.Tournament;
import com.example.halla.golftournamentpal.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 01-Mar-17.
 */

public class Networker {
    //String BASE_URL ="http://192.168.0.109:8080"; //Heima hjá Höllu
    //String BASE_URL ="http://192.168.1.43:8080"; //Heima hjá Hafrúnu
    String BASE_URL = "http://10.0.2.2:8080"; //To use with the emulator
    String TAG = "networker";


    /**
     * Opens a connection to GET json objects from.
     */
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

    /**
     * Opens a connection to post JsonData to
     */
    public String postTournament(String urlSpec, String tournamentJson)
            throws MalformedURLException {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = null;

        try {
            URLEncoder.encode(tournamentJson, "UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", "" +
                    Integer.toString(tournamentJson.getBytes().length));
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            conn.setRequestProperty("Accept", "*/*");

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            out.writeBytes(tournamentJson);
            out.flush();
            out.close();

            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseCode() + ": with " + urlSpec);
            }

            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * Gets all tournaments that are in the database on the server
     */
    public List<Tournament> fetchTournaments(String searchName) {
        List<Tournament> tournaments = new ArrayList<>();
        try {
            String url = Uri.parse(BASE_URL+"/json/search")
                    .buildUpon()
                    .appendQueryParameter("searchName", searchName)
                    .build()
                    .toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONArray jsonBody = new JSONArray(jsonString);
            Log.i(TAG, "Recieved tournaments: " + jsonBody.length());
            for (int i = 0; i < jsonBody.length(); i++) {

                JSONObject tournamentJsonObject = jsonBody.getJSONObject(i);

                Tournament tournament = JsonParser.parseTournament(tournamentJsonObject);

                tournaments.add(tournament);
            }
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return tournaments;
    }

    /**
     * Gets the golfer from the server who corresponds to the social number.
     */
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

    /**
     * Checks if the password matches the social number and returns the corresponding golfer.
     */
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


    /**
     * Creates a golfer and user on the server with the information
     */
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

    public Tournament fetchTournament(Long id) {
        Tournament toReturn = null;
        String url = Uri.parse(BASE_URL + "/json/getTournament")
                .buildUpon()
                .appendQueryParameter("id", id.toString())
                .build()
                .toString();


        try {
            String jsonString = getUrlString(url);
            JSONObject jsonObject = new JSONObject(jsonString);
            toReturn = JsonParser.parseCompleteTournament(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toReturn;

    }

    /**
     * Posts MatchPlayTournament info on the server where the tournament is created.
     * The tournament that is created is returned.
     */
    public MatchPlayTournament sendMatchPlayTournament(MatchPlayTournament tournament,
                                                       Long hostSocial,
                                                       int numberInBrackets,
                                                       int numberOutOfBrackets) {
        String tournamentJsonString;
        MatchPlayTournament createdTournament = null;

        String url = Uri.parse(BASE_URL + "/json/matchplay")
                .buildUpon()
                .appendQueryParameter("hostSocial", hostSocial.toString())
                .appendQueryParameter("nIBrackets", Integer.toString(numberInBrackets))
                .appendQueryParameter("nOOBrackets", Integer.toString(numberOutOfBrackets))
                .build()
                .toString();

            try {
                tournamentJsonString = JsonParser.matchPlayTournamentToString(tournament);
                Log.i(TAG, "Sending this tournament: " + tournamentJsonString);
                Log.i(TAG, "Sending tournament to " + url);
                String createdTournamentString = postTournament(url, tournamentJsonString);
                Log.i(TAG, createdTournamentString);
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

    public void updateHandicap(long social, double handicap) throws IOException {

        String url = Uri.parse(BASE_URL + "/json/updateHandicap")
                .buildUpon()
                .appendQueryParameter("social", Long.toString(social))
                .appendQueryParameter("handicap", Double.toString(handicap))
                .build()
                .toString();

        try {
            String jsonString = getUrlString(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Tournament> fetchMyTournaments(String golferSocial) {
        List<Tournament> tournaments = new ArrayList<>();
        try {
            String url = Uri.parse(BASE_URL+"/json/getTournamentByGolfer")
                    .buildUpon()
                    .appendQueryParameter("golferSocial", golferSocial)
                    .build()
                    .toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONArray jsonBody = new JSONArray(jsonString);
            Log.i(TAG, "Recieved tournaments: " + jsonBody.length());
            for (int i = 0; i < jsonBody.length(); i++) {

                JSONObject tournamentJsonObject = jsonBody.getJSONObject(i);

                Tournament tournament = JsonParser.parseTournament(tournamentJsonObject);

                tournaments.add(tournament);
            }
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return tournaments;
    }
}
