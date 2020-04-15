/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.loader;

import com.ranking.model.Match;
import com.ranking.model.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class DataLoader {

    static String fileName;
    private static Map<String, Map<String, Map< Integer, Match>>> data; // Map< HomeTeamName, Map<AwayTeamName, Map<Season, Match>>>
//    private static List<String> teamIndex; 
    public DataLoader(String pathfilename) {
        DataLoader.fileName = pathfilename;
        DataLoader.data = new HashMap();
        loadData();
    }

    public static Map<String, Map<String, Map<Integer, Match>>> getData() {
        return data;
    }

    public static void setData(Map<String, Map<String, Map<Integer, Match>>> data) {
        DataLoader.data = data;
    }

    private void loadData() {
        // season	home_team_name	away_team_name	date_string	full_time_result	home_goals	away_goals	half_time_HOME_Team_score	half_time_Away_Team_score
        String k = "";
        try (BufferedReader br = new BufferedReader(new FileReader(DataLoader.fileName))) {
            String line;

            String[] heads = br.readLine().split(",");
            int counter = 0;
            while ((line = br.readLine()) != null) {
                k = line;
                // Map< HomeTeamName, Map<AwayTeamName, Map<Season, Match>>>
                String[] values = line.split(",");
                int season = Integer.parseInt(values[0]);
                String homeTeamName = values[1];
                String awayTeamName = values[2];
//                String date_string = values[3];
                Result full_time_result = convertToResult(values[3]);
                int home_goals = Integer.parseInt(values[4]);
                int away_goals = Integer.parseInt(values[5]);
                int half_time_HOME_Team_score = Integer.parseInt(values[6]);
                int half_time_Away_Team_score = Integer.parseInt(values[7]);

                Match match = new Match(homeTeamName, awayTeamName,  home_goals, away_goals, half_time_HOME_Team_score, half_time_Away_Team_score, season, full_time_result);
                
                System.out.println(" READ COUNT - "+ ++counter + " --------------------- "+ match);
                
                if (data.containsKey(homeTeamName)) {

                    //---
                    Map<String, Map< Integer, Match>> awayMap = data.get(homeTeamName);

                    if (awayMap.containsKey(awayTeamName)) {

                        Map<Integer, Match> get = awayMap.get(awayTeamName);
                        get.put(season, match);

                    } else {
                        Map< Integer, Match> seasonMap = new HashMap();
                        seasonMap.put(season, match);
                        awayMap.put(awayTeamName, seasonMap);
                    }
                    //---

                } else {
                    Map< Integer, Match> seasonMap = new HashMap();
                    seasonMap.put(season, match);
                    Map<String, Map< Integer, Match>> awayMap = new HashMap();
                    awayMap.put(awayTeamName, seasonMap);

                    data.put(homeTeamName, awayMap);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(k);
        }

    }

    private Result convertToResult(String result) {

        if (result.equalsIgnoreCase("h")) {
            return Result.HOME;
        } else {
            return (result.equalsIgnoreCase("a")) ? Result.AWAY : Result.DRAW;
        }
    }

}
