/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.utility;

import com.ranking.exception.DevException;
import com.ranking.loader.DataLoader;
import com.ranking.model.Match;
import java.util.Arrays;
import java.util.HashMap;
import com.ranking.model.Result;
import com.ranking.model.Season;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class ColleyUtil {


    private static Map<Integer, Season> seasonList = DataLoader.getSeasonList();

    public static Double getRank(int season, String teamName) {
        
        if(seasonList==null||seasonList.isEmpty()) throw new DevException(" seasonList Empty/Null for ->   ColleyUtil.getRank() ");
        Season s = ColleyUtil.seasonList.get(season);
        if(s==null) return Double.NaN;
        Map<String, Double> ranking = s.getRanking();
        if(ranking==null||ranking.isEmpty()) throw new DevException(" ranking Empty/Null for ->   ColleyUtil.getRank() || Ranking not calculated for season - "+season);

        Double rank =   ranking.get(teamName);
      
        if (null == rank) {
            return Double.NaN;
        }

        return rank;

    }
    
    public static void calculateAllSeasons(){
        try{
            if(seasonList==null||seasonList.isEmpty()) throw new DevException(" seasonList Empty/Null for ->   ColleyUtil.calculateAllSeasons() ");
            
            seasonList.keySet().forEach((season) -> {
                reCalculateSeason(season);
            });
        }catch(DevException e){
        e.printStackTrace();
        }
        
    }
    
    

    public static void reCalculateSeason(Integer season) {

        try {
            Season s = seasonList.get(season);
            if (s == null) {
                throw new DevException("Invalid Season Number OR Season does not exist in DataLoader.seasonList -> ColleyUtil.recalculateSeason(Integer season)");
            }

            Set<Match> seasonMatchList = s.getSeasonMatchList();
            if (seasonMatchList == null || seasonMatchList.isEmpty()) {
                throw new DevException("seasonMatchList is null/empty in the recalculateSeason(Integer season)");
            }

            RealMatrix colleyMatrix = s.getColleyMatrix();
            RealMatrix bMatrix = s.getbMatrix();
            Map<String, Double> ranking = s.getRanking();
            Map<String, Integer> teamIndexList = s.getTeamIndexList();

            s.loadTeamIndex();
            s.resetMatrix();

            seasonMatchList.stream().filter((match) -> !(!match.isIsComplete())).forEachOrdered((match) -> {
                //if result are not available, skip it
                double weight = 0.9;
                int TeamAIndex = teamIndexList.get(match.getHomeTeam());
                int TeamBIndex = teamIndexList.get(match.getAwayTeam());
                System.out.println(TeamAIndex + "  " + TeamBIndex );
                colleyMatrix.setEntry(TeamAIndex, TeamAIndex, (colleyMatrix.getEntry(TeamAIndex, TeamAIndex) + 1 * weight));
                colleyMatrix.setEntry(TeamBIndex, TeamBIndex, (colleyMatrix.getEntry(TeamBIndex, TeamBIndex) + 1 * weight));
                colleyMatrix.setEntry(TeamAIndex, TeamBIndex, (colleyMatrix.getEntry(TeamAIndex, TeamBIndex) - 1 * weight));
                colleyMatrix.setEntry(TeamBIndex, TeamAIndex, (colleyMatrix.getEntry(TeamBIndex, TeamAIndex) - 1 * weight));
                if (match.getResult().equals(Result.HOME)) {
                    bMatrix.setEntry(TeamAIndex, 0, (bMatrix.getEntry(TeamAIndex, 0) + 1 * weight));
                    bMatrix.setEntry(TeamBIndex, 0, (bMatrix.getEntry(TeamBIndex, 0) - 1 * weight));
                } else if (match.getResult().equals(Result.AWAY)) {
                    bMatrix.setEntry(TeamAIndex, 0, (bMatrix.getEntry(TeamAIndex, 0) - 1 * weight));
                    bMatrix.setEntry(TeamBIndex, 0, (bMatrix.getEntry(TeamBIndex, 0) + 1 * weight));
                }
            });

            // Adding 2 to diagonal of colley matrix ( Total number of games )
            for (int i = 0; i < 20; i++) {
                colleyMatrix.addToEntry(i, i, 2);
            }

            // Dividing by 2 and adding one to vector b
            for (int i = 0; i < 20; i++) {
                bMatrix.setEntry(i, 0, ((bMatrix.getEntry(i, 0) / 2) + 1));
            }

            DecompositionSolver solver = new LUDecomposition(colleyMatrix).getSolver();
            RealVector constants = bMatrix.getColumnVector(0);
            RealVector solution = solver.solve(constants);

            //add solved value to ranking
            teamIndexList.entrySet().forEach((entry) -> {
                String teamName = entry.getKey();
                Integer val = entry.getValue();

                double rank = solution.getEntry(val);

                ranking.put(teamName, rank);
            });

        } catch (DevException | OutOfRangeException e) {
            e.printStackTrace();
        }

    }

}
