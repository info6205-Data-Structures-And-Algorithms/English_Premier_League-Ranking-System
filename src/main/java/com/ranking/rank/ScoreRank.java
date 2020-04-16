package com.ranking.rank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ranking.loader.DataLoader;
import com.ranking.model.Match;
import com.ranking.model.Result;
import com.ranking.utility.ColleyUtil;

public class ScoreRank {
	
		private static double currentSeason = 2020;
	    public static Result matchWinProbability(String homeTeam, String awayTeam){
	        
	        Map<String, Map<String, Map<Integer, Match>>> matchMap = DataLoader.getData();
	        double homeTeamScore = 0, awayTeamScore=0;
	        double homeTeamRankedScore = 0, awayTeamRankedScore = 0;
	        
	        //Home vs Away Scoring
	        if (matchMap.containsKey(homeTeam) && matchMap.get(homeTeam).containsKey(awayTeam)) {
	        Map<Integer, Match> homeVsAwayMatch = matchMap.get(homeTeam).get(awayTeam);
	        for (Map.Entry<Integer,Match> season : homeVsAwayMatch.entrySet()){
	            double year = season.getKey();
	            Match match = season.getValue();
	            
	            if (match.getResult() == Result.HOME){
	                homeTeamScore += 3.0 * (1.0 / (currentSeason - year));
	                homeTeamRankedScore += ColleyUtil.getRank((int) year, awayTeam)  * 3.0 * (1.0 / (currentSeason - year));
	            } else if (match.getResult() == Result.AWAY){
	                awayTeamScore += 3.0 * (1.0 / (currentSeason - year));
	                awayTeamRankedScore += ColleyUtil.getRank((int) year, homeTeam) * 3.0 * (1.0 / (currentSeason - year));
	            } else if (match.getResult() == Result.DRAW){
	                homeTeamScore +=  1.0 * (1.0 / (currentSeason - year));
	                homeTeamRankedScore += ColleyUtil.getRank((int) year, awayTeam)  * 1.0 * (1.0 / (currentSeason - year));
	                awayTeamScore +=  1.0 * (1.0 / (currentSeason - year));
	                awayTeamRankedScore += ColleyUtil.getRank((int) year, homeTeam) * 1.0 * (1.0 / (currentSeason - year));
	            }
	        }
	        }
	        System.out.println("Home vs Away Score");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	        
	        //AwayVsHome
	        if (matchMap.containsKey(awayTeam) && matchMap.get(awayTeam).containsKey(homeTeam)) {
	        Map<Integer, Match> awayVsHomeMatch = matchMap.get(awayTeam).get(homeTeam);
	        for(Map.Entry<Integer, Match> season : awayVsHomeMatch.entrySet()){
	            double year = season.getKey();
	            Match match = season.getValue();
	            
	            if (match.getResult() == Result.HOME){
	                awayTeamScore += 3.0 * (1.0 / (currentSeason - year));
	                awayTeamRankedScore += ColleyUtil.getRank((int) year, homeTeam)  * 3.0 * (1.0 / (currentSeason - year));
	            } else if (match.getResult() == Result.AWAY){
	                homeTeamScore += 3.0 * (1.0 / (currentSeason - year));
	                homeTeamRankedScore += ColleyUtil.getRank((int) year, awayTeam) * 3.0 * (1.0 / (currentSeason - year));
	            } else if (match.getResult() == Result.DRAW){
	                awayTeamScore += 1.0 * (1.0 / (currentSeason - year));
	                awayTeamRankedScore += ColleyUtil.getRank((int) year, homeTeam) * 1.0 * (1.0 / (currentSeason - year));
	                homeTeamScore += 1.0 * (1.0 / (currentSeason - year));
	                homeTeamRankedScore += ColleyUtil.getRank((int) year, awayTeam) * 1.0 * (1.0 / (currentSeason - year));
	            }
	        }
	        }
	        System.out.println("AwayVsHome");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	        
	        Map<Integer, Double[]> seasonHomeAwayDraw;
	        Map<Integer, Double[]> seasonRankedHomeAwayDraw;
	        
	        //HomeVsAll
	        if (matchMap.containsKey(homeTeam)) {
	        Map<String, Map<Integer, Match>> homeVsAllMatch = matchMap.get(homeTeam);
	        seasonHomeAwayDraw = new HashMap<>();
	        seasonRankedHomeAwayDraw = new HashMap<>();
	        for(Map.Entry<String, Map<Integer, Match>> awayTeams : homeVsAllMatch.entrySet()){
	        	if (homeVsAllMatch.containsKey(awayTeams.getKey())) {
	            Map<Integer, Match> matchesWithAllAwayTeams = awayTeams.getValue();
	            //if (matchesWithAllAwayTeams != null) { 
	            for(Map.Entry<Integer, Match> season : matchesWithAllAwayTeams.entrySet()){
	            	if (season != null  && matchMap.get(homeTeam).containsKey(awayTeam) && matchMap.get(homeTeam).get(awayTeam).containsKey(season.getKey())) {
	                int year = season.getKey();
	                Match match = season.getValue();
	                
	                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                Double[] rankedHomeAwayDraw = seasonRankedHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                
	                if (match.getResult() == Result.HOME){
	                    homeAwayDraw[0]++;
	                    rankedHomeAwayDraw[0] += (1.0) * ColleyUtil.getRank((int) year, match.getAwayTeam());
	                } else if (match.getResult() == Result.AWAY){
	                    homeAwayDraw[1]++;
	                    rankedHomeAwayDraw[1] += (1.0) * ColleyUtil.getRank((int) year, match.getAwayTeam());
	                } else if (match.getResult() == Result.DRAW){
	                    homeAwayDraw[2]++;
	                    rankedHomeAwayDraw[2] += (1.0) * ColleyUtil.getRank((int) year, match.getAwayTeam());
	                }
	                seasonHomeAwayDraw.put(year, homeAwayDraw);
	                seasonRankedHomeAwayDraw.put(year, rankedHomeAwayDraw);
	            } 
	            }
	        }  
	        }
	             
	        
	        
	        for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
	            double year = entry.getKey();
	            Double[] scores = entry.getValue();
	            Double[] rankedScores = seasonRankedHomeAwayDraw.get(entry.getKey());
	            
	            double totalGames = scores[0] + scores[1] + scores[2];
	            
	            homeTeamScore += ((scores[0]/totalGames) * 3.0 ) / (currentSeason - year);
	            homeTeamScore += ((scores[2]/totalGames) * 1.0 ) / (currentSeason - year);
	            homeTeamScore -= ((scores[1]/totalGames) * 1.0 ) / (currentSeason - year);
	            
	            double rankedTotalGames = rankedScores[0] + rankedScores[1] + rankedScores[2];
	            
	            homeTeamRankedScore += ((rankedScores[0]/rankedTotalGames) * 3.0 ) / (currentSeason - year);
	            homeTeamRankedScore += ((rankedScores[2]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	            homeTeamRankedScore -= ((rankedScores[1]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	        }
	        }
	        
	        System.out.println("HomeVsAll");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	        
	        //All vs AwayTeam
	        seasonHomeAwayDraw = new HashMap<>();
	        seasonRankedHomeAwayDraw = new HashMap<>();
	        for(Map.Entry<String, Map<String, Map<Integer, Match>>> allVsAwayMatch : matchMap.entrySet()){

	            if (allVsAwayMatch.getValue().containsKey(awayTeam)) {
	        	Map<Integer, Match> matchesData = allVsAwayMatch.getValue().get(awayTeam);
	           
	            for(Map.Entry<Integer, Match> season : matchesData.entrySet()){
	            	if (season != null && matchMap.get(homeTeam).containsKey(awayTeam) && matchMap.get(homeTeam).get(awayTeam).containsKey(season.getKey())) {
	                int year = season.getKey();
	                Match match = season.getValue();
	                
	                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                Double[] rankedHomeAwayDraw = seasonRankedHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                if (match.getResult() == Result.HOME){
	                    homeAwayDraw[0]++;
	                    rankedHomeAwayDraw[0] += (1.0) * ColleyUtil.getRank((int) year, match.getHomeTeam());
	                } else if (match.getResult() == Result.AWAY){
	                    homeAwayDraw[1]++;
	                    rankedHomeAwayDraw[1] += (1.0) * ColleyUtil.getRank((int) year, match.getHomeTeam());
	                } else if (match.getResult() == Result.DRAW){
	                    homeAwayDraw[2]++;
	                    rankedHomeAwayDraw[2] += (1.0) * ColleyUtil.getRank((int) year, match.getHomeTeam());
	                }
	                seasonHomeAwayDraw.put(year, homeAwayDraw);
	                seasonRankedHomeAwayDraw.put(year, rankedHomeAwayDraw);
	            	}
	            }
	        }  
	        }
	        
	        for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
	            double year = entry.getKey();
	            Double[] scores = entry.getValue();
	            Double[] rankedScores = seasonRankedHomeAwayDraw.get(entry.getKey());
	            
	            double totalGames = scores[0] + scores[1] + scores[2];
	            
	            awayTeamScore += ((scores[1]/totalGames) * 3.0 ) / (currentSeason - year);
	            awayTeamScore += ((scores[2]/totalGames) * 1.0 ) / (currentSeason - year);
	            awayTeamScore -= ((scores[0]/totalGames) * 1.0 ) / (currentSeason - year);
	            
	            double rankedTotalGames = rankedScores[0] + rankedScores[1] + rankedScores[2];
	            
	            awayTeamRankedScore += ((rankedScores[1]/rankedTotalGames) * 3.0 ) / (currentSeason - year);
	            awayTeamRankedScore += ((rankedScores[2]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	            awayTeamRankedScore -= ((rankedScores[0]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	        }
	        
	        System.out.println("AllVsAway");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	        
	        //AllVsHome
	        seasonHomeAwayDraw = new HashMap<>();
	        seasonRankedHomeAwayDraw = new HashMap<>();
	        for(Map.Entry<String, Map<String, Map<Integer, Match>>> allVsHomeMatch : matchMap.entrySet()){
	        	if  (allVsHomeMatch.getValue().containsKey(homeTeam)) {
	            Map<Integer, Match> matchesData = allVsHomeMatch.getValue().get(homeTeam);
	            
	            for(Map.Entry<Integer, Match> season : matchesData.entrySet()){
	            	if (season != null  && matchMap.get(homeTeam).containsKey(awayTeam) && matchMap.get(homeTeam).get(awayTeam).containsKey(season.getKey())) {
	                int year = season.getKey();
	                Match match = season.getValue();
	                
	                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                Double[] rankedHomeAwayDraw = seasonRankedHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                
	                if (match.getResult() == Result.HOME){
	                    homeAwayDraw[0]++;
	                    rankedHomeAwayDraw[0] += (1.0) * ColleyUtil.getRank((int) year, match.getHomeTeam());
	                } else if (match.getResult() == Result.AWAY){
	                    homeAwayDraw[1]++;
	                    rankedHomeAwayDraw[1] += (1.0) * ColleyUtil.getRank((int) year, match.getHomeTeam());
	                } else if (match.getResult() == Result.DRAW){
	                    homeAwayDraw[2]++;
	                    rankedHomeAwayDraw[2] += (1.0) * ColleyUtil.getRank((int) year, match.getHomeTeam());
	                }
	                seasonHomeAwayDraw.put(year, homeAwayDraw);
	                seasonRankedHomeAwayDraw.put(year, rankedHomeAwayDraw);
	            }
	            }
	        } 
	        }
	        
	         for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
	            double year = entry.getKey();
	            Double[] scores = entry.getValue();
	            Double[] rankedScores = seasonRankedHomeAwayDraw.get(entry.getKey());
	            
	            double totalGames = scores[0] + scores[1] + scores[2];
	            
	            homeTeamScore += ((scores[1]/totalGames) * 3.0 ) / (currentSeason - year);
	            homeTeamScore += ((scores[2]/totalGames) * 1.0 ) / (currentSeason - year);
	            homeTeamScore -= ((scores[0]/totalGames) * 1.0 ) / (currentSeason - year);
	            
	            double rankedTotalGames = rankedScores[0] + rankedScores[1] + rankedScores[2];
	            
	            homeTeamRankedScore += ((rankedScores[1]/rankedTotalGames) * 3.0 ) / (currentSeason - year);
	            homeTeamRankedScore += ((rankedScores[2]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	            homeTeamRankedScore -= ((rankedScores[0]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	        }
	        System.out.println("AllVsHome");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	         
	        //AwayVsAll
	        seasonHomeAwayDraw = new HashMap<>();
	        seasonRankedHomeAwayDraw = new HashMap<>();
	        if (matchMap.containsKey(awayTeam)) {
	        Map<String, Map<Integer, Match>> awayVsAllMatch = matchMap.get(awayTeam);
	        for(Map.Entry<String, Map<Integer, Match>> allTeams : awayVsAllMatch.entrySet()){
	        	if (awayVsAllMatch.containsKey(allTeams.getKey())) {
	            Map<Integer, Match> matchesWithAllAwayTeams = allTeams.getValue();
	            //if (matchesWithAllAwayTeams != null) {
	            for(Map.Entry<Integer, Match> season : matchesWithAllAwayTeams.entrySet()){
	            	if (season != null && matchMap.get(homeTeam).containsKey(awayTeam) && matchMap.get(homeTeam).get(awayTeam).containsKey(season.getKey())) {
	                int year = season.getKey();
	                Match match = season.getValue();
	                
	                Double[] homeAwayDraw = seasonHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                Double[] rankedHomeAwayDraw = seasonRankedHomeAwayDraw.getOrDefault(year, Arrays.stream(new double[3]).boxed().toArray(Double[]::new));
	                
	                if (match.getResult() == Result.HOME){
	                    homeAwayDraw[0]++;
	                    rankedHomeAwayDraw[0] += (1.0) * ColleyUtil.getRank((int) year, match.getAwayTeam());
	                } else if (match.getResult() == Result.AWAY){
	                    homeAwayDraw[1]++;
	                    rankedHomeAwayDraw[1] += (1.0) * ColleyUtil.getRank((int) year, match.getAwayTeam());
	                } else if (match.getResult() == Result.DRAW){
	                    homeAwayDraw[2]++;
	                    rankedHomeAwayDraw[2] += (1.0) * ColleyUtil.getRank((int) year, match.getAwayTeam());
	                }
	                seasonHomeAwayDraw.put(year, homeAwayDraw);
	                seasonRankedHomeAwayDraw.put(year, rankedHomeAwayDraw);
	            	}
	            }
	        }
	        }
	        
	        for (Map.Entry<Integer, Double[]> entry : seasonHomeAwayDraw.entrySet()){
	            double year = entry.getKey();
	            Double[] scores = entry.getValue();
	            Double[] rankedScores = seasonRankedHomeAwayDraw.get(entry.getKey());
	            
	            double totalGames = scores[0] + scores[1] + scores[2];
	            
	            awayTeamScore += ((scores[0]/totalGames) * 3 ) / (currentSeason - year);
	            awayTeamScore += ((scores[2]/totalGames) * 1 ) / (currentSeason - year);
	            awayTeamScore -= ((scores[1]/totalGames) * 1 ) / (currentSeason - year);
	            
	            double rankedTotalGames = rankedScores[0] + rankedScores[1] + rankedScores[2];
	            
	            awayTeamRankedScore += ((rankedScores[0]/rankedTotalGames) * 3.0 ) / (currentSeason - year);
	            awayTeamRankedScore += ((rankedScores[2]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	            awayTeamRankedScore -= ((rankedScores[1]/rankedTotalGames) * 1.0 ) / (currentSeason - year);
	        }
	        }
	        System.out.println("AwayVsAll");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	        
	        System.out.println("Final Score: ");
	        System.out.println("Home Team Score: " + homeTeamScore);
	        System.out.println("Away Team Score: " + awayTeamScore);
	        System.out.println("Prediction: " + ScoreRank.scoreApproximation(homeTeamScore, awayTeamScore));
	        return ScoreRank.scoreApproximation(homeTeamScore, awayTeamScore);
	    }
	    
	
	public static Result scoreApproximation(double homeTeamScore, double awayTeamScore) {
		double boundPercentage = 15.0;
		double highestScorer = Math.max(homeTeamScore, awayTeamScore);
		double upperBound = (1 + (boundPercentage/100)) * highestScorer;
		double lowerBound = (1 - (boundPercentage/100)) * highestScorer;
		if (homeTeamScore >= lowerBound && homeTeamScore <= upperBound && awayTeamScore >= lowerBound && awayTeamScore <= upperBound) {
			return Result.DRAW;
		} else {
			if (homeTeamScore > awayTeamScore) {
				return Result.HOME;
			} else {
				return Result.AWAY;
			}
		}
		
	} 
}
