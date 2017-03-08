package com.example.halla.golftournamentpal;

import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Tournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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

        return golfer;
    }
}
