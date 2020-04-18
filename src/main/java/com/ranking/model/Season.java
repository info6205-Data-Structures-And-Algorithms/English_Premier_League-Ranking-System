/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class Season {

    private Integer year;
    private Map<String, Double> ranking;   // Map <TeamName, Rank>
    private Set<Match> SeasonMatchList;
    private RealMatrix ColleyMatrix;
    private RealMatrix bMatrix;
    private Map<String, Integer> TeamIndexList; // Map < TeamName, Index [0-20] >

    public Season(Integer year) {
        this.year = year;
        this.ranking = new HashMap<>();
        this.SeasonMatchList = new HashSet<>();
        this.ColleyMatrix = MatrixUtils.createRealMatrix(new double[20][20]);
        this.bMatrix = MatrixUtils.createColumnRealMatrix(new double[20]);
        this.TeamIndexList = new HashMap<>();
    }

    public void loadTeamIndex() {
        SortedSet<String> a = new TreeSet();
        for (Match match : SeasonMatchList) {

            a.add(match.getHomeTeam());
            a.add(match.getAwayTeam());

        }

        Integer index = -1;
        
        for(String team : a){
        TeamIndexList.put(team, ++index);
        }
        
       // printMap();

    }
    
    public void printMap() {
    	for(Map.Entry<String, Integer> entry : TeamIndexList.entrySet()) {
    		System.out.println(year+","+entry.getKey() + "," + entry.getValue() );
    	}
    }
    
    public void resetMatrix(){
    
        this.ColleyMatrix = MatrixUtils.createRealMatrix(new double[20][20]);
        this.bMatrix = MatrixUtils.createColumnRealMatrix(new double[20]);
    
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Map<String, Double> getRanking() {
        return ranking;
    }

    public void setRanking(Map<String, Double> ranking) {
        this.ranking = ranking;
    }

    public Set<Match> getSeasonMatchList() {
        return SeasonMatchList;
    }

    public void setSeasonMatchList(Set<Match> SeasonMatchList) {
        this.SeasonMatchList = SeasonMatchList;
    }

    public RealMatrix getColleyMatrix() {
        return ColleyMatrix;
    }

    public void setColleyMatrix(RealMatrix ColleyMatrix) {
        this.ColleyMatrix = ColleyMatrix;
    }

    public RealMatrix getbMatrix() {
        return bMatrix;
    }

    public void setbMatrix(RealMatrix bMatrix) {
        this.bMatrix = bMatrix;
    }

    public Map<String, Integer> getTeamIndexList() {
        return TeamIndexList;
    }

    public void setTeamIndexList(Map<String, Integer> TeamIndexList) {
        this.TeamIndexList = TeamIndexList;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.year); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Season other = (Season) obj;
        return Objects.equals(this.year, other.year);
    }

    @Override
    public String toString() {
        return String.valueOf(this.year); //To change body of generated methods, choose Tools | Templates.
    }

}
