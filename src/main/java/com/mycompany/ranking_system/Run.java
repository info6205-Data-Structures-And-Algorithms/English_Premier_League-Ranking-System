/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ranking_system;

import com.ranking.loader.DataLoader;
import com.ranking.model.Match;
import com.ranking.model.Result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class Run {
    

    public Run(){
        matchWinProbability("Burnley", "Arsenal");
    }
    
    private static double currentSeason = 2020;
    public static void matchWinProbability(String homeTeam, String awayTeam){
        
        Map<String, Map<String, Map<Integer, Match>>> matchMap = DataLoader.getData();
        double homeTeamScore = 0, awayTeamScore=0;
        
        //Home vs Away Scoring
        Map<Integer, Match> homeVsAwayMatch = matchMap.get(homeTeam).get(awayTeam);
        for (Map.Entry<Integer,Match> season : homeVsAwayMatch.entrySet()){
            double year = season.getKey();
            Match match = season.getValue();
            
            if (match.getResult() == Result.HOME){
                homeTeamScore += 3.0 * (1.0 / (currentSeason - year));
            } else if (match.getResult() == Result.AWAY){
                awayTeamScore += 3.0 * (1.0 / (currentSeason - year));
            } else if (match.getResult() == Result.DRAW){
                homeTeamScore +=  1.0 * (1.0 / (currentSeason - year));
                awayTeamScore +=  1.0 * (1.0 / (currentSeason - year));
            }
        }
        
        System.out.println("Home vs Away Score");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //AwayVsHome
        Map<Integer, Match> awayVsHomeMatch = matchMap.get(awayTeam).get(homeTeam);
        for(Map.Entry<Integer, Match> season : awayVsHomeMatch.entrySet()){
            double year = season.getKey();
            Match match = season.getValue();
            
            if (match.getResult() == Result.HOME){
                awayTeamScore += 3.0 * (1.0 / (currentSeason - year));
            } else if (match.getResult() == Result.AWAY){
                homeTeamScore += 3.0 * (1.0 / (currentSeason - year));
            } else if (match.getResult() == Result.DRAW){
                awayTeamScore += 1.0 * (1.0 / (currentSeason - year));
                homeTeamScore += 1.0 * (1.0 / (currentSeason - year));
            }
        }
        System.out.println("AwayVsHome");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        Map<Integer, Double[]> seasonHomeAwayDraw;
        //HomeVsAll
        if (matchMap.containsKey(homeTeam)) {
        Map<String, Map<Integer, Match>> homeVsAllMatch = matchMap.get(homeTeam);
        seasonHomeAwayDraw = new HashMap<>();
        for(Map.Entry<String, Map<Integer, Match>> awayTeams : homeVsAllMatch.entrySet()){
            Map<Integer, Match> matchesWithAllAwayTeams = awayTeams.getValue();
            if (matchesWithAllAwayTeams != null) { 
            for(Map.Entry<Integer, Match> season : matchesWithAllAwayTeams.entrySet()){
            	if (season != null) {
                int year = season.getKey();
                Match match = season.getValue();
                
                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
                
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
        }  
        }
             
        
        
        for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
            double year = entry.getKey();
            Double[] scores = entry.getValue();
            
            double totalGames = scores[0] + scores[1] + scores[2];
            
            homeTeamScore += ((scores[0]/totalGames) * 3.0 ) / (currentSeason - year);
            homeTeamScore += ((scores[2]/totalGames) * 1.0 ) / (currentSeason - year);
            homeTeamScore -= ((scores[1]/totalGames) * 1.0 ) / (currentSeason - year);
        }
        }
        
        System.out.println("HomeVsAll");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //All vs AwayTeam
        seasonHomeAwayDraw = new HashMap<>();
        for(Map.Entry<String, Map<String, Map<Integer, Match>>> allVsAwayMatch : matchMap.entrySet()){

            if (allVsAwayMatch.getValue().containsKey(awayTeam)) {
        	Map<Integer, Match> matchesData = allVsAwayMatch.getValue().get(awayTeam);
           
            for(Map.Entry<Integer, Match> season : matchesData.entrySet()){
                int year = season.getKey();
                Match match = season.getValue();
                
                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
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
        }
        
        for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
            double year = entry.getKey();
            Double[] scores = entry.getValue();
            
            double totalGames = scores[0] + scores[1] + scores[2];
            
            awayTeamScore += ((scores[1]/totalGames) * 3.0 ) / (currentSeason - year);
            awayTeamScore += ((scores[2]/totalGames) * 1.0 ) / (currentSeason - year);
            awayTeamScore -= ((scores[0]/totalGames) * 1.0 ) / (currentSeason - year);
        }
        
        System.out.println("AllVsAway");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        //AllVsHome
        seasonHomeAwayDraw = new HashMap<>();
        for(Map.Entry<String, Map<String, Map<Integer, Match>>> allVsHomeMatch : matchMap.entrySet()){
        	if  (allVsHomeMatch.getValue().containsKey(homeTeam)) {
            Map<Integer, Match> matchesData = allVsHomeMatch.getValue().get(homeTeam);
            
            for(Map.Entry<Integer, Match> season : matchesData.entrySet()){
                int year = season.getKey();
                Match match = season.getValue();
                
                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
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
        }
        
         for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
            double year = entry.getKey();
            Double[] scores = entry.getValue();
            
            double totalGames = scores[0] + scores[1] + scores[2];
            
            homeTeamScore += ((scores[1]/totalGames) * 3.0 ) / (currentSeason - year);
            homeTeamScore += ((scores[2]/totalGames) * 1.0 ) / (currentSeason - year);
            homeTeamScore -= ((scores[0]/totalGames) * 1.0 ) / (currentSeason - year);
        }
        System.out.println("AllVsHome");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
         
        //AwayVsAll
        seasonHomeAwayDraw = new HashMap<>();
        if (matchMap.containsKey(awayTeam)) {
        Map<String, Map<Integer, Match>> awayVsAllMatch = matchMap.get(awayTeam);
        for(Map.Entry<String, Map<Integer, Match>> allTeams : awayVsAllMatch.entrySet()){
            Map<Integer, Match> matchesWithAllAwayTeams = allTeams.getValue();
            if (matchesWithAllAwayTeams != null) {
            for(Map.Entry<Integer, Match> season : matchesWithAllAwayTeams.entrySet()){
            	if (season != null) {
                int year = season.getKey();
                Match match = season.getValue();
                
                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
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
        }
        }
        
        for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
            double year = entry.getKey();
            Double[] scores = entry.getValue();
            
            double totalGames = scores[0] + scores[1] + scores[2];
            
            awayTeamScore += ((scores[0]/totalGames) * 3 ) / (currentSeason - year);
            awayTeamScore += ((scores[2]/totalGames) * 1 ) / (currentSeason - year);
            awayTeamScore -= ((scores[1]/totalGames) * 1 ) / (currentSeason - year);
        }
        }
        System.out.println("AwayVsAll");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
        
        System.out.println("Final Score: ");
        System.out.println("Home Team Score: " + homeTeamScore);
        System.out.println("Away Team Score: " + awayTeamScore);
    }
    
    public static void main(String args[]){
    
        new DataLoader("D:\\INFO 6205 Prof. Robin Hillyard\\Final Project\\English_Premier_League-Ranking-System\\LoadData.csv");
        Map<String, Map<String, Map<Integer, Match>>> data = DataLoader.getData();
        
        Run run = new Run();
//        int count = 0;
//        
//        for (Map.Entry<String, Map<String, Map<Integer, Match>>> entry : data.entrySet()) {
//            String key = entry.getKey();
//            Map<String, Map<Integer, Match>> value = entry.getValue();
//            
//            for (Map.Entry<String, Map<Integer, Match>> entry1 : value.entrySet()) {
//                String k = entry1.getKey();
//                Map<Integer, Match> v = entry1.getValue();
//                
//                for (Map.Entry<Integer, Match> entry2 : v.entrySet()) {
//                    int k2 = entry2.getKey();
//                    Match v2 = entry2.getValue();
//                    
//                    System.out.println("ROW : "+ ++count);
//                    System.out.println(v2);
//                    
//                }
//                
//            }
//            
//        }
        
    
    }
    
}
