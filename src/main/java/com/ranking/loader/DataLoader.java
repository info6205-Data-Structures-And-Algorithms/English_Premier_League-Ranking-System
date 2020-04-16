/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.loader;

import com.ranking.model.Match;
import com.ranking.model.Result;
import com.ranking.model.Season;
import com.ranking.utility.ColleyUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class DataLoader {

    static String fileName;
    private static Map<String, Map<String, Map< Integer, Match>>> data; // Map< HomeTeamName, Map<AwayTeamName, Map<Season, Match>>>
    private static Map<Integer, Season> seasonList;

    public DataLoader(String pathfilename) {
        DataLoader.fileName = pathfilename;
        DataLoader.data = new HashMap();
        DataLoader.seasonList = new HashMap<>();
        loadData();
    }

    public static Map<String, Map<String, Map<Integer, Match>>> getData() {
        return data;
    }

    public static void setData(Map<String, Map<String, Map<Integer, Match>>> data) {
        DataLoader.data = data;
    }

    public static Map<Integer, Season> getSeasonList() {
        return seasonList;
    }

    public static void setSeasonList(Map<Integer, Season> seasonList) {
        DataLoader.seasonList = seasonList;
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
                if (season < 2017 ) continue;
                Match match = new Match(homeTeamName, awayTeamName,  home_goals, away_goals, half_time_HOME_Team_score, half_time_Away_Team_score, season, full_time_result);
                
//                Season s = seasonList.getOrDefault(season, new Season(season));
//                seasonList.putIfAbsent(season, s);
//                s.getSeasonMatchList().add(match);
                
                InsertMatch(match, season);
                
                
                System.out.println(" READ COUNT - "+ ++counter + " --------------------- "+ match);
     

            }
            
            // Run this only when all 
            ColleyUtil.calculateAllSeasons();
            
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
    
    public void InsertMatch(Match match, Integer season){
        
       Season s = seasonList.getOrDefault(season, new Season(season));
                seasonList.putIfAbsent(season, s);
                s.getSeasonMatchList().add(match);
                
                
                
                
                
                           
                if (data.containsKey(match.getHomeTeam())) {

                    //---
                    Map<String, Map< Integer, Match>> awayMap = data.get(match.getHomeTeam());

                    if (awayMap.containsKey(match.getAwayTeam())) {

                        Map<Integer, Match> get = awayMap.get(match.getAwayTeam());
                        get.put(season, match);

                    } else {
                        Map< Integer, Match> seasonMap = new HashMap();
                        seasonMap.put(season, match);
                        awayMap.put(match.getAwayTeam(), seasonMap);
                    }
                    //---

                } else {
                    Map< Integer, Match> seasonMap = new HashMap();
                    seasonMap.put(season, match);
                    Map<String, Map< Integer, Match>> awayMap = new HashMap();
                    awayMap.put(match.getAwayTeam(), seasonMap);

                    data.put(match.getHomeTeam(), awayMap);
                }
        
    }

}
