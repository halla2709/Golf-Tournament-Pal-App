package com.example.halla.golftournamentpal;

import android.util.Log;

import com.example.halla.golftournamentpal.models.Bracket;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Match;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;
import com.example.halla.golftournamentpal.models.PlayOffRound;
import com.example.halla.golftournamentpal.models.PlayOffTree;
import com.example.halla.golftournamentpal.models.Round;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;
import com.example.halla.golftournamentpal.models.Scorecard;
import com.example.halla.golftournamentpal.models.Tournament;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Halla on 08-Mar-17.
 * This class has static methods to parse the JsonObjects to POJOS and also to
 * parse tournaments to Json.
 */

public class JsonParser {

    public static Tournament parseTournament(JSONObject jsonObject) throws JSONException {
        Tournament tournament = new Tournament();

        tournament.setCourse(jsonObject.getString("course"));
        tournament.setName(jsonObject.getString("name"));
        if(jsonObject.has("id")) {
            tournament.setId(jsonObject.getLong("id"));
        }

        if(!jsonObject.isNull("startDate"))
            tournament.setStartDate(new Date(jsonObject.getLong("startDate")));

        JSONArray playersJsonArray = jsonObject.getJSONArray("players");
        for (int j = 0; j < playersJsonArray.length(); j++) {
            JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
            Golfer golfer = JsonParser.parseGolfer(playerJsonObject);

            tournament.addPlayer(golfer);
        }

        return tournament;
    }

    public static Tournament parseCompleteTournament(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("brackets"))
            return parseMatchPlay(jsonObject);
        else
            return parseScoreboard(jsonObject);
    }

    public static Golfer parseGolfer(JSONObject playerJsonObject) throws JSONException {
        Golfer golfer = new Golfer();

        golfer.setName(playerJsonObject.getString("name"));
        golfer.setEmail(playerJsonObject.getString("email"));
        golfer.setSocial(playerJsonObject.getLong("social"));
        golfer.setHandicap(playerJsonObject.getDouble("handicap"));

        Log.i("PARSEJSON", "parsing golfer" + golfer.getName());

        if(!playerJsonObject.isNull("friends")) {
            JSONArray friendsJsonArray = playerJsonObject.getJSONArray("friends");
            for (int i = 0; i < friendsJsonArray.length(); i++) {
                JSONObject friendJsonObject = friendsJsonArray.getJSONObject(i);
                Golfer friend = new Golfer();

                friend.setName(friendJsonObject.getString("name"));
                friend.setEmail(friendJsonObject.getString("email"));
                friend.setSocial(friendJsonObject.getLong("social"));
                friend.setHandicap(friendJsonObject.getDouble("handicap"));

                Log.i("PARSEJSON", "parsing golfer" + friend.getName());
                golfer.addFriend(friend);
            }
        }
        return golfer;
    }

    public static ScoreboardTournament parseScoreboard(JSONObject scoreboardJsonObject)
    throws JSONException {
        ScoreboardTournament scoreboardTournament = new ScoreboardTournament();

        if(scoreboardJsonObject.has("id")) {
            scoreboardTournament.setId(scoreboardJsonObject.getLong("id"));
        }
        scoreboardTournament.setCourse(scoreboardJsonObject.getString("course"));
        scoreboardTournament.setName(scoreboardJsonObject.getString("name"));
        scoreboardTournament.setStartDate(new Date(scoreboardJsonObject.getLong("startDate")));

        JSONArray playersJsonArray = scoreboardJsonObject.getJSONArray("players");
        for (int j = 0; j < playersJsonArray.length(); j++) {
            JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
            Golfer golfer = JsonParser.parseGolfer(playerJsonObject);

            scoreboardTournament.addPlayer(golfer);
        }

        scoreboardTournament.setScores(JsonParser.parseScores(scoreboardJsonObject.getJSONArray("scores")));
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
                newScores[i][j] = individual.getInt(j);
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
                newRounds.add(parseRound(roundsJsonArray.getJSONObject(j)));
            }
            String scorecardCourse = scorecardJsonObject.getString("course");
            Golfer golfer = JsonParser.parseGolfer(scorecardJsonObject.getJSONObject("player"));
            int numberOfRounds = roundsJsonArray.length();
            int[] totalforRounds = JsonParser.parseArray(scorecardJsonObject.getJSONArray("totalForRounds"));

            Scorecard newScorecard = new Scorecard(golfer, scorecardCourse, numberOfRounds, newRounds);
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

            if(matchplayJsonObject.has("id")) {
                matchPlayTournament.setId(matchplayJsonObject.getLong("id"));
            }
            matchPlayTournament.setCourse(matchplayJsonObject.getString("course"));
            matchPlayTournament.setName(matchplayJsonObject.getString("name"));
            matchPlayTournament.setStartDate(new Date(matchplayJsonObject.getLong("startDate")));

            JSONArray playersJsonArray = matchplayJsonObject.getJSONArray("players");
            for (int j = 0; j < playersJsonArray.length(); j++) {
                JSONObject playerJsonObject = playersJsonArray.getJSONObject(j);
                Golfer golfer = JsonParser.parseGolfer(playerJsonObject);

                matchPlayTournament.addPlayer(golfer);
            }

            matchPlayTournament.setAreBrackets(matchplayJsonObject.getBoolean("areBrackets"));
            if(matchplayJsonObject.has("brackets") && matchPlayTournament.isAreBrackets()) {
                JSONArray bracketsJsonArray = matchplayJsonObject.getJSONArray("brackets");
                List<Bracket> brackets = new ArrayList<>();
                if(matchPlayTournament.isAreBrackets()) {

                    for(int i = 0; i < bracketsJsonArray.length(); i++) {
                        JSONObject bracketJsonObject = bracketsJsonArray.getJSONObject(i);
                        Bracket bracket = parseBracket(bracketJsonObject);
                        brackets.add(bracket);
                    }
                }
                matchPlayTournament.setBrackets(brackets);
            }

            if(matchplayJsonObject.has("playOffs")){
                matchPlayTournament.setPlayOffs(JsonParser.parsePlayOffs(matchplayJsonObject.getJSONObject("playOffs")));
            }

            return matchPlayTournament;
        }

        public static Bracket parseBracket(JSONObject bracketJsonObject) throws JSONException {
            Bracket bracket = new Bracket(null,null,"");

            bracket.setName(bracketJsonObject.getString("name"));

            Log.i("PARSEJSON", "parsing bracket" + bracket.getName());
            JSONArray playersJsonArray = bracketJsonObject.getJSONArray("players");
            List<Golfer> players = new ArrayList<>();
            for(int i = 0; i < playersJsonArray.length(); i++) {
                JSONObject playersJsonObject = playersJsonArray.getJSONObject(i);
                Golfer player = parseGolfer(playersJsonObject);
                players.add(player);
                Log.i("PARSEJSON", "parsing player" + player.getName());
            }
            bracket.setPlayers(players);
            Log.i("PARSEJSON", "parsing bracket" + bracket.getName());
            JSONArray matchesJsonArray = bracketJsonObject.getJSONArray("match");
            List<Match> matches = new ArrayList<>();
            for(int i = 0; i < matchesJsonArray.length(); i++) {
                JSONObject matchJsonObject = matchesJsonArray.getJSONObject(i);
                Match match = parseMatch(matchJsonObject);
                Log.i("PARSEJSON", "parsing match" + match.getDate());
                matches.add(match);
            }
            bracket.setMatch(matches);

            return bracket;
        }

        public static Match parseMatch(JSONObject matchJsonObject) throws JSONException {
            Match match = new Match(0L, null, "", null);

            if(!matchJsonObject.isNull("id"))
                match.setID(matchJsonObject.getLong("id"));
            if(!matchJsonObject.isNull("results"))
                match.setResults(matchJsonObject.getString("results"));
            if(!matchJsonObject.isNull("date"))
                match.setDate(new Date(matchJsonObject.getLong("date")));

            if(!matchJsonObject.isNull("players")) {
                Log.i("PARSEJSON", "parsing player in bracket");
                JSONArray playersJsonArray = matchJsonObject.getJSONArray("players");
                List<Golfer> players = new ArrayList<>();
                for(int i = 0; i < playersJsonArray.length(); i++) {
                    JSONObject playerJsonObject = playersJsonArray.getJSONObject(i);
                    Golfer player = parseGolfer(playerJsonObject);
                    players.add(player);
                }

                match.setPlayers(players);
            }

            return match;
        }

        public static PlayOffTree parsePlayOffs(JSONObject playofftreeJsonObject) throws JSONException {

            PlayOffTree tree = new PlayOffTree(null);
            JSONArray roundsJsonArray = playofftreeJsonObject.getJSONArray("rounds");
            List<PlayOffRound> rounds = new ArrayList<>();
            for(int i = 0; i < roundsJsonArray.length(); i++) {
                JSONObject roundJsonObject = roundsJsonArray.getJSONObject(i);
                PlayOffRound round = new PlayOffRound(0, null);
                JSONArray matchesJsonArray = roundJsonObject.getJSONArray("matches");
                List<Match> matches = new ArrayList<>();
                for(int j = 0; j < matchesJsonArray.length(); j++) {
                    Match match = parseMatch(matchesJsonArray.getJSONObject(j));
                    matches.add(match);
                }
                round.setMatches(matches);
                round.setRound(roundJsonObject.getInt("round"));
                rounds.add(round);
            }

            tree.setRounds(rounds);
            return tree;
        }

        public static String matchPlayTournamentToString(MatchPlayTournament matchPlayTournament) throws JSONException {
            JSONObject tournamentObject = new JSONObject();
            tournamentObject.put("course", matchPlayTournament.getCourse());
            tournamentObject.put("name", matchPlayTournament.getName());
            tournamentObject.put("startDate", matchPlayTournament.getStartDate().getTime());
            tournamentObject.put("areBrackets", matchPlayTournament.isAreBrackets());

            JSONArray playersJsonArray = new JSONArray();
            for(int i = 0; i < matchPlayTournament.getPlayers().size(); i++) {
                playersJsonArray.put(golferToString(matchPlayTournament.getPlayers().get(i)));
            }
            tournamentObject.put("players", playersJsonArray);

            return tournamentObject.toString();
        }

        public static JSONObject golferToString(Golfer golfer) throws JSONException {
            JSONObject golferObject = new JSONObject();
            golferObject.put("name", golfer.getName());
            golferObject.put("social", golfer.getSocial());
            golferObject.put("handicap", golfer.getHandicap());
            golferObject.put("email", golfer.getEmail());
            return golferObject;
        }

        public static HashMap<Long, Integer> getBracketResults(JSONObject fullObject) throws JSONException {
            HashMap<Long, Integer> bracketResults;

            Gson gson = new Gson();
            JsonElement element = gson.fromJson(fullObject.get("bracketResults").toString(), JsonElement.class);
            bracketResults = gson.fromJson(element, new TypeToken<HashMap<Long, Integer>>(){}.getType());

            return bracketResults;
        }

        public static String[][] getResultTable(JSONObject fullObject) throws JSONException {
            String[][] resultTable;

            Gson gson = new Gson();
            JsonElement element = gson.fromJson(fullObject.get("resultTable").toString(), JsonElement.class);
            resultTable = gson.fromJson(element, String[][].class);

            return resultTable;
        }

        public static String scoreboardTournamentToString(ScoreboardTournament scoreboardTournament) throws JSONException {
            JSONObject tournamentObject = new JSONObject();
            tournamentObject.put("course", scoreboardTournament.getCourse());
            tournamentObject.put("name", scoreboardTournament.getName());
            tournamentObject.put("startDate", scoreboardTournament.getStartDate().getTime());
            tournamentObject.put("numberOfRounds", scoreboardTournament.getNumberOfRounds());

            JSONArray playersJsonArray = new JSONArray();
            for(int i = 0; i < scoreboardTournament.getPlayers().size(); i++) {
                playersJsonArray.put(golferToString(scoreboardTournament.getPlayers().get(i)));
            }
            tournamentObject.put("players", playersJsonArray);

            return tournamentObject.toString();
        }
    }


