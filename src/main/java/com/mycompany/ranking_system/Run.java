/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ranking_system;

import com.ranking.loader.Constants;
import com.ranking.loader.DataLoader;
import com.ranking.model.Match;
import com.ranking.model.Result;
import com.ranking.rank.ScoreRank;
import com.ranking.model.Season;
import com.ranking.utility.ColleyUtil;

import dnl.utils.text.table.TextTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SortOrder;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class Run {
    
     private static final double currentSeason = 2020;

    public Run(){
       devPrintAllRankingsForAllSeason();
    }
    
    
    public void devPrintAllRankingsForAllSeason(){
        try{
        for(Map.Entry<Integer, Season> entry : DataLoader.getSeasonList().entrySet()){
            Integer year = entry.getKey();
            Season season = entry.getValue();
            if (season.getYear() == Constants.year) {
            System.out.println(" ");	
            System.out.println("*********** Ranking for Season:  "+year + " ***********");
            String[][] ranking = new String[20][2];
            String[] column = new String[] {"Team Name", "Rank"};
            int count = 0;
            for(Map.Entry<String, Double> rankEntry : season.getRanking().entrySet()){
                String teamName = rankEntry.getKey();
                Double rank = rankEntry.getValue(); 
                ranking[count][0] = teamName;
                ranking[count][1] = rank.toString();
                count++;
            }
            TextTable tt = new TextTable(column, ranking);
            
            tt.setSort(1, SortOrder.DESCENDING);
            tt.printTable();
            } 
        }
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   
    public static void main(String args[]){
    	try {
        new DataLoader(".\\LoadData.csv"); //Loads Data File
        DataLoader.loadScheduleData(".\\ScheduleData.csv");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}  
    }
    
}
