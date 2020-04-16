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
import java.util.Map;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class ColleyUtil {

    private Map<String, Map<String, Map< Integer, Match>>> data = DataLoader.getData();
    private static Map<Integer, Map<String, Double>> ranking;

    public static Double getRank(int season, String teamName) {

        Map<String, Double> teamMap = ColleyUtil.ranking.get(season);
        if (teamMap == null || teamMap.isEmpty()) {
            return Double.NaN;
        }

        Double rank = teamMap.get(teamName);

        if (rank == null || rank.isNaN()) {
            return Double.NaN;
        }

        return rank;

    }

    public RealVector solveForMat() {

        RealMatrix coefficients = MatrixUtils.createRealMatrix(new double[][]{{2, 3, -2}, {-1, 7, 6}, {4, -3, -5}});
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(new double[]{1, -2, 1}, false);
        RealVector solution = solver.solve(constants);

        return solution;
    }

    private void loadRankings() {

        try {

            if (data == null || data.isEmpty()) {
                throw new DevException("Map DataLoader.data is null or Empty");
            }
            
            this.ranking = new HashMap();

            int numberOfTeams = data.size();
            RealMatrix colleyMatrix = MatrixUtils.createRealMatrix(new double[numberOfTeams][numberOfTeams]);
            RealMatrix bMatrix = MatrixUtils.createColumnRealMatrix(new double[numberOfTeams]);

            for (Map.Entry<String, Map<String, Map<Integer, Match>>> homeMap : data.entrySet()) {
                String homeTeam = homeMap.getKey();
                Map<String, Map<Integer, Match>> awayMap = homeMap.getValue();

                for (Map.Entry<String, Map<Integer, Match>> a : awayMap.entrySet()) {
                    String awayTeam = a.getKey();
                    Map<Integer, Match> seasonMap = a.getValue();

                    for (Map.Entry<Integer, Match> seasonData : seasonMap.entrySet()) {
                        Integer season = seasonData.getKey();
                        Match match = seasonData.getValue();

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
