/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.utility;

import com.ranking.model.Match;
import com.sun.javafx.geom.Matrix3f;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
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

    private Map<String, Map<String, Map< Integer, Match>>> data;
    private Map<String, Double> ranking;

    public ColleyUtil(Map<String, Map<String, Map< Integer, Match>>> data) {
        this.data = data;
        this.ranking = new HashMap();
        System.out.println(Arrays.toString(solveForMat().toArray()));
    }

    public Map<String, Double> getRanking() {
        return ranking;
    }

    public void setRanking(Map<String, Double> ranking) {
        this.ranking = ranking;
    }

    public RealVector solveForMat() {
        

        RealMatrix coefficients = MatrixUtils.createRealMatrix(new double[][]{{2, 3, -2}, {-1, 7, 6}, {4, -3, -5}});
        
//        MatrixUtils.createRealMatrix(new double[][]{{2, 3, -2}, {-1, 7, 6}, {4, -3, -5}});

        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(new double[]{1, -2, 1}, false);
        RealVector solution = solver.solve(constants);

        return solution;
    }

    private void loadRankings() {
        
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

    }

}
