/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ranking_system;

import com.ranking.loader.DataLoader;
import com.ranking.model.Match;
import java.util.Map;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class Run {
    
    public static void main(String args[]){
    
        new DataLoader("D:\\RUCHIT\\Assignments\\PSA\\Project\\Ranking_System\\LoadData.csv");
        Map<String, Map<String, Map<Integer, Match>>> data = DataLoader.getData();
        
        int count = 0;
        
        for (Map.Entry<String, Map<String, Map<Integer, Match>>> entry : data.entrySet()) {
            String key = entry.getKey();
            Map<String, Map<Integer, Match>> value = entry.getValue();
            
            for (Map.Entry<String, Map<Integer, Match>> entry1 : value.entrySet()) {
                String k = entry1.getKey();
                Map<Integer, Match> v = entry1.getValue();
                
                for (Map.Entry<Integer, Match> entry2 : v.entrySet()) {
                    int k2 = entry2.getKey();
                    Match v2 = entry2.getValue();
                    
                    System.out.println("ROW : "+ ++count);
                    System.out.println(v2);
                    
                }
                
            }
            
        }
        
          
    }
    
}
