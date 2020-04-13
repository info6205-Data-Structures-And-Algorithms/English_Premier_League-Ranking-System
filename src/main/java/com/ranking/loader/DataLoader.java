/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class DataLoader {
    
    static String fileName ;
    
    
    public int[][] getMat(){
    
        int[][] res = new int[41][41];
        
        List<List<String>> records = new ArrayList<>();
try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
    String line;
    
    
        line = br.readLine();
        
    while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
    }
}catch(Exception e){}
        
        return res;
    }
    
    
}
