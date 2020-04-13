/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.loader;

import com.ranking.model.Match;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class DataLoader {
    
    static String fileName ;
    private static Map<String, Map<String, Map< Integer, Match>>> data;

        public DataLoader(String filename) {
        this.fileName = filename;
        this.data = new HashMap();
    }
    
    
    public static Map<String, Map<String, Map<Integer, Match>>> getData() {
        return data;
    }

    public static void setData(Map<String, Map<String, Map<Integer, Match>>> data) {
        DataLoader.data = data;
    }
    
    public int[][] getMat(){
    
        int[][] res = new int[41][41];
        
//        Map<String, Map<String, 
        List<List<String>> records = new ArrayList<>();
try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
    String line;
    
    
        String[] heads = br.readLine().split(",");
        
    while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        
        records.add(Arrays.asList(values));
    }
}catch(Exception e){}
        
        return res;
    }
    
    
}
