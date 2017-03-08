package com.example.halla.golftournamentpal;

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

        JSONArray friendsJsonArray = playerJsonObject.getJSONArray("friends");
        for(int i = 0; i < friendsJsonArray.length(); i++) {
            JSONObject friendJsonObject = friendsJsonArray.getJSONObject(i);
            Golfer friend = new Golfer();
            friend.setName(playerJsonObject.getString("name"));
            friend.setEmail(playerJsonObject.getString("email"));
            friend.setSocial(playerJsonObject.getLong("social"));
            friend.setHandicap(playerJsonObject.getDouble("handicap"));

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
        //TODO parsa alla helv**** h inta
        return null;
    }

    public static int[] parseArray(JSONArray totalJsonArray) throws JSONException {
        int[] array = new int[totalJsonArray.length()];
        for(int i = 0; i < totalJsonArray.length(); i++) {
            array[i] = totalJsonArray.getInt(i);
        }

        return array;
    }
}
