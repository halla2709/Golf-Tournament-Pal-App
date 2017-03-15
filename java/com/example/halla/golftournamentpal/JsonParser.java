package com.example.halla.golftournamentpal;

import android.util.Log;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.Round;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;
import com.example.halla.golftournamentpal.models.Scorecard;
import com.example.halla.golftournamentpal.models.Tournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 08-Mar-17.
 */

public class JsonParser {

    public static Tournament parseTournament(JSONObject jsonObject) throws JSONException {
        Tournament tournament = new Tournament();

        tournament.setCourse(jsonObject.getString("course"));
        tournament.setName(jsonObject.getString("name"));
        tournament.setStartDate(new Date(jsonObject.getLong("startDate")));

        JSONArray playersJsonArray = jsonObject.getJSONArray("players");
        for (int j = 0; j < playersJsonArray.length(); j++) {
            JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
            Golfer golfer = JsonParser.parseGolfer(playerJsonObject);

            tournament.addPlayer(golfer);
        }

        return tournament;
    }

    public static Golfer parseGolfer(JSONObject playerJsonObject) throws JSONException {
        Golfer golfer = new Golfer();

        golfer.setName(playerJsonObject.getString("name"));
        golfer.setEmail(playerJsonObject.getString("email"));
        golfer.setSocial(playerJsonObject.getLong("social"));
        golfer.setHandicap(playerJsonObject.getDouble("handicap"));

        Log.i("PARSEJSON", "parsing golfer" + golfer.getName());
        JSONArray friendsJsonArray = playerJsonObject.getJSONArray("friends");
        for(int i = 0; i < friendsJsonArray.length(); i++) {
            JSONObject friendJsonObject = friendsJsonArray.getJSONObject(i);
            Golfer friend = new Golfer();

            friend.setName(friendJsonObject.getString("name"));
            friend.setEmail(friendJsonObject.getString("email"));
            friend.setSocial(friendJsonObject.getLong("social"));
            friend.setHandicap(friendJsonObject.getDouble("handicap"));

            Log.i("PARSEJSON", "parsing golfer" + friend.getName());
            golfer.addFriend(friend);
        }

        return golfer;
    }

    public static ScoreboardTournament parseScoreboard(JSONObject scoreboardJsonObject)
            throws JSONException {
        ScoreboardTournament scoreboardTournament = new ScoreboardTournament();

        scoreboardTournament.setCourse(scoreboardJsonObject.getString("course"));
        scoreboardTournament.setName(scoreboardJsonObject.getString("name"));
        scoreboardTournament.setStartDate(new Date(scoreboardJsonObject.getLong("startDate")));

        JSONArray playersJsonArray = scoreboardJsonObject.getJSONArray("players");
        for (int j = 0; j < playersJsonArray.length(); j++) {
            JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
            Golfer golfer = JsonParser.parseGolfer(playerJsonObject);

            scoreboardTournament.addPlayer(golfer);
        }

        scoreboardTournament.setSores(JsonParser.parseScores(scoreboardJsonObject.getJSONArray("scores")));
        scoreboardTournament.setNumberOfRounds(scoreboardJsonObject.getInt("numberOfRounds"));
        scoreboardTournament.setScorecards(JsonParser.parseScorecards(scoreboardJsonObject.getJSONArray("scorecards")));
        return scoreboardTournament;
    }

    public static int[][] parseScores(JSONArray scoresJsonObject) throws JSONException{
        int[][] newScores =  new int[scoresJsonObject.length()][];
        for(int i = 0; i < scoresJsonObject.length(); i++) {
            JSONArray individual = scoresJsonObject.getJSONArray(i);
            newScores[i] = new int[individual.length()];
            for(int j = 0; j < individual.length(); j++) {
                newScores[i][j] = individual.getInt(i);
            }
        }

        return newScores;
    }

    public static List<Scorecard> parseScorecards(JSONArray scorecardsJsonArray) throws JSONException {
        List<Scorecard> newScorecards = new ArrayList<>();

        for(int i = 0; i < scorecardsJsonArray.length(); i++) {
            JSONObject scorecardJsonObject = scorecardsJsonArray.getJSONObject(i);
            JSONArray roundsJsonArray = scorecardJsonObject.getJSONArray("rounds");
            List<Round> newRounds = new ArrayList<>();
            for(int j = 0; j < roundsJsonArray.length(); j++) {
                newRounds.add(parseRound(roundsJsonArray.getJSONObject(i)));
            }
            String scorecardCourse = scorecardJsonObject.getString("course");
            Golfer golfer = JsonParser.parseGolfer(scorecardJsonObject.getJSONObject("player"));
            int numberOfRounds = roundsJsonArray.length();
            int[] totalforRounds = JsonParser.parseArray(scorecardJsonObject.getJSONArray("totalForRounds"));

            Scorecard newScorecard = new Scorecard(golfer, scorecardCourse, null, numberOfRounds, newRounds);
            newScorecards.add(newScorecard);
        }

        return newScorecards;
    }

    public static Round parseRound(JSONObject roundsJsonObject) throws JSONException {
        int h1 = roundsJsonObject.getInt("h1");
        int h2 = roundsJsonObject.getInt("h2");
        int h3 = roundsJsonObject.getInt("h3");
        int h4 = roundsJsonObject.getInt("h4");
        int h5 = roundsJsonObject.getInt("h5");
        int h6 = roundsJsonObject.getInt("h6");
        int h7 = roundsJsonObject.getInt("h7");
        int h8 = roundsJsonObject.getInt("h8");
        int h9 = roundsJsonObject.getInt("h9");
        int h10 = roundsJsonObject.getInt("h10");
        int h11 = roundsJsonObject.getInt("h11");
        int h12 = roundsJsonObject.getInt("h12");
        int h13 = roundsJsonObject.getInt("h13");
        int h14 = roundsJsonObject.getInt("h14");
        int h15 = roundsJsonObject.getInt("h15");
        int h16 = roundsJsonObject.getInt("h16");
        int h17 = roundsJsonObject.getInt("h17");
        int h18 = roundsJsonObject.getInt("h18");
        int total = roundsJsonObject.getInt("total");
        int[] myscores = {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11,
                h12, h13, h14, h15, h16, h17, h18};
        return new Round(myscores, total, h18, h17, h16, h15, h14, h13,
                h12, h11, h10, h9, h8, h7, h6, h5, h4, h3, h2, h1);
    }

    public static int[] parseArray(JSONArray totalJsonArray) throws JSONException {
        int[] array = new int[totalJsonArray.length()];
        for(int i = 0; i < totalJsonArray.length(); i++) {
            array[i] = totalJsonArray.getInt(i);
        }

        return array;
    }

    public static double doubleRounder(double toRound) {
        return (double) Math.round(toRound*10)/10;
    }

    public static MatchPlayTournament parseMatchPlay(JSONObject matchplayJsonObject)
            throws JSONException {
        MatchPlayTournament matchPlayTournament = new MatchPlayTournament();

        matchPlayTournament.setCourse(matchplayJsonObject.getString("course"));
        matchPlayTournament.setName(matchplayJsonObject.getString("name"));
        matchPlayTournament.setStartDate(new Date(matchplayJsonObject.getLong("startDate")));

        JSONArray playersJsonArray = matchplayJsonObject.getJSONArray("players");
        for (int j = 0; j < playersJsonArray.length(); j++) {
            JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
            Golfer golfer = JsonParser.parseGolfer(playerJsonObject);

            matchPlayTournament.addPlayer(golfer);
        }

        matchPlayTournament.setAreBrackets(JsonParser.parseAreBrackets(matchplayJsonObject.getJSONArray("areBrackets")));
        matchPlayTournament.setBrackets(matchplayJsonObject.parseBrackets("brackets"));
        matchPlayTournament.setPlayOffs(JsonParser.parsePlayOffs(matchplayJsonObject.getJSONArray("playOffs")));
        return matchPlayTournament;
    }

    public static parseBrackets()
}
