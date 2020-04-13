/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ranking_system;

import com.ranking.loader.DataLoader;
import com.ranking.model.Match;
import com.ranking.model.Result;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class Run {
    
    private static int currentSeason = 2020;
    public static void matchWinProbability(String homeTeam, String awayTeam){
        String filePath = "";
        Map<String, Map<String, Map<Integer, Match>>> matchMap = new DataLoader(filePath);
        int homeTeamScore = 0, awayTeamScore=0;
        
        //Home vs Away Scoring
        Map<Integer, Match> homeVsAwayMatch = matchMap.get(homeTeam).get(awayTeam);
        for (Map.Entry<Integer,Match> season : homeVsAwayMatch.entrySet()){
            int year = season.getKey();
            Match match = season.getValue();
            
            if (match.getResult() == Result.HOME){
                homeTeamScore += 3 * (1 / (currentSeason - match.getSeason()));
            } else if (match.getResult() == Result.AWAY){
                awayTeamScore += 3 * (1 / (currentSeason - match.getSeason()));
            } else if (match.getResult() == Result.DRAW){
                homeTeamScore += 1 * (1 / (currentSeason - match.getSeason()));
                awayTeamScore += 1 * (1 / (currentSeason - match.getSeason()));
            }
        }
        
        System.out.println("Home vs Away Score");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //AwayVsHome
        Map<Integer, Match> awayVsHomeMatch = matchMap.get(awayTeam).get(homeTeam);
        for(Map.Entry<Integer, Match> season : awayVsHomeMatch.entrySet()){
            int year = season.getKey();
            Match match = season.getValue();
            
            if (match.getResult() == Result.HOME){
                awayTeamScore += 3 * (1 / (currentSeason - match.getSeason()));
            } else if (match.getResult() == Result.AWAY){
                homeTeamScore += 3 * (1 / (currentSeason - match.getSeason()));
            } else if (match.getResult() == Result.DRAW){
                awayTeamScore += 1 * (1 / (currentSeason - match.getSeason()));
                homeTeamScore += 1 * (1 / (currentSeason - match.getSeason()));
            }
        }
        System.out.println("AwayVsHome");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //HomeVsAll
        Map<String, Map<Integer, Match>> homeVsAllMatch = matchMap.get(homeTeam);
        Map<Integer, Integer[]> seasonHomeAwayDraw = new HashMap<>();
        for(Map.Entry<String, Map<Integer, Match>> awayTeams : homeVsAllMatch.entrySet()){
            Map<Integer, Match> matchesWithAllAwayTeams = awayTeams.getValue();
            for(Map.Entry<Integer, Match> season : matchesWithAllAwayTeams.entrySet()){
                int year = season.getKey();
                Match match = season.getValue();
                
                Integer[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, new Integer[3]);
                if (match.getResult() == Result.HOME){
                    homeAwayDraw[0]++;
                } else if (match.getResult() == Result.AWAY){
                    homeAwayDraw[1]++;
                } else if (match.getResult() == Result.DRAW){
                    homeAwayDraw[2]++;
                }
                seasonHomeAwayDraw.put(year, homeAwayDraw);
            }
        }
        
        for (Map.Entry<Integer, Integer[]> entry : seasonHomeAwayDraw.entrySet()){
            int year = entry.getKey();
            Integer[] scores = entry.getValue();
            
            int totalGames = scores[0] + scores[1] + scores[3];
            
            homeTeamScore += ((scores[0]/totalGames) * 3 ) / (currentSeason - year);
            homeTeamScore += ((scores[2]/totalGames) * 1 ) / (currentSeason - year);
            homeTeamScore -= ((scores[1]/totalGames) * 1 ) / (currentSeason - year);
        }
        
        System.out.println("HomeVsAll");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //All vs AwayTeam
        seasonHomeAwayDraw = new HashMap<>();
        for(Map.Entry<String, Map<String, Map<Integer, Match>>> allVsAwayMatch : matchMap.entrySet()){
            Map<Integer, Match> matchesData = allVsAwayMatch.getValue().get(awayTeam);
            
            for(Map.Entry<Integer, Match> season : matchesData.entrySet()){
                int year = season.getKey();
                Match match = season.getValue();
                
                Integer[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, new Integer[3]);
                if (match.getResult() == Result.HOME){
                    homeAwayDraw[0]++;
                } else if (match.getResult() == Result.AWAY){
                    homeAwayDraw[1]++;
                } else if (match.getResult() == Result.DRAW){
                    homeAwayDraw[2]++;
                }
                seasonHomeAwayDraw.put(year, homeAwayDraw);
            }
            
        }
        
        for (Map.Entry<Integer, Integer[]> entry : seasonHomeAwayDraw.entrySet()){
            int year = entry.getKey();
            Integer[] scores = entry.getValue();
            
            int totalGames = scores[0] + scores[1] + scores[3];
            
            awayTeamScore += ((scores[1]/totalGames) * 3 ) / (currentSeason - year);
            awayTeamScore += ((scores[2]/totalGames) * 1 ) / (currentSeason - year);
            awayTeamScore -= ((scores[0]/totalGames) * 1 ) / (currentSeason - year);
        }
        
        System.out.println("AllVsAway");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //AllVsHome
        seasonHomeAwayDraw = new HashMap<>();
        for(Map.Entry<String, Map<String, Map<Integer, Match>>> allVsHomeMatch : matchMap.entrySet()){
            Map<Integer, Match> matchesData = allVsHomeMatch.getValue().get(homeTeam);
            
            for(Map.Entry<Integer, Match> season : matchesData.entrySet()){
                int year = season.getKey();
                Match match = season.getValue();
                
                Integer[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, new Integer[3]);
                if (match.getResult() == Result.HOME){
                    homeAwayDraw[0]++;
                } else if (match.getResult() == Result.AWAY){
                    homeAwayDraw[1]++;
                } else if (match.getResult() == Result.DRAW){
                    homeAwayDraw[2]++;
                }
                seasonHomeAwayDraw.put(year, homeAwayDraw);
            }
            
        }
        
         for (Map.Entry<Integer, Integer[]> entry : seasonHomeAwayDraw.entrySet()){
            int year = entry.getKey();
            Integer[] scores = entry.getValue();
            
            int totalGames = scores[0] + scores[1] + scores[3];
            
            homeTeamScore += ((scores[1]/totalGames) * 3 ) / (currentSeason - year);
            homeTeamScore += ((scores[2]/totalGames) * 1 ) / (currentSeason - year);
            homeTeamScore -= ((scores[0]/totalGames) * 1 ) / (currentSeason - year);
        }
        System.out.println("AllVsHome");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
         
        //AwayVsAll
        seasonHomeAwayDraw = new HashMap<>();
        Map<String, Map<Integer, Match>> awayVsAllMatch = matchMap.get(awayTeam);
        for(Map.Entry<String, Map<Integer, Match>> allTeams : awayVsAllMatch.entrySet()){
            Map<Integer, Match> matchesWithAllAwayTeams = allTeams.getValue();
            for(Map.Entry<Integer, Match> season : matchesWithAllAwayTeams.entrySet()){
                int year = season.getKey();
                Match match = season.getValue();
                
                Integer[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, new Integer[3]);
                if (match.getResult() == Result.HOME){
                    homeAwayDraw[0]++;
                } else if (match.getResult() == Result.AWAY){
                    homeAwayDraw[1]++;
                } else if (match.getResult() == Result.DRAW){
                    homeAwayDraw[2]++;
                }
                seasonHomeAwayDraw.put(year, homeAwayDraw);
            }
        }
        
        for (Map.Entry<Integer, Integer[]> entry : seasonHomeAwayDraw.entrySet()){
            int year = entry.getKey();
            Integer[] scores = entry.getValue();
            
            int totalGames = scores[0] + scores[1] + scores[3];
            
            awayTeamScore += ((scores[0]/totalGames) * 3 ) / (currentSeason - year);
            awayTeamScore += ((scores[2]/totalGames) * 1 ) / (currentSeason - year);
            awayTeamScore -= ((scores[1]/totalGames) * 1 ) / (currentSeason - year);
        }
        System.out.println("AwayVsAll");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        System.out.println("Final Score: ");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
    }
    
    public static void main(String args[]){
    

    
    }
    
}
