/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.edu.ihu.shashmap;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.LongAdder;


/**
 * An implementation utilizing HashMap and MapReduce framework to produce a
 * sorted list of all elements in the stream and their respective frequencies.
 * Used to review and verify the results from Space-Saving Algorithm. 
 */
public class SHashMap {
    
    private HashMap<String, Integer> dataMap = new HashMap<String, Integer>(); 
    private  ArrayList<String> arr = new ArrayList<String>();
    public static String filename = "C:\\Users\\Hyae\\Documents\\Zipf125M.txt";
    //public static String outputname = ("C:\\Users\\Hyae\\Documents\\SHresults.txt");
    private static double phi = 0.001; 
    private static LongAdder nf = new LongAdder(); //number of frequent items


        
    public ArrayList<String> getList(){
        return this.arr;
    } 
    
    public HashMap<String, Integer> getDataMap(){
        return this.dataMap;
    } 
    
    public void map(){
        try{
            FileReader f2 = new FileReader(filename);
            Scanner s = new Scanner(f2); 
                while (s.hasNextLine()) { 
                    String line = s.nextLine(); 
                    String[] items = line.split("\\s"); 
                    for (int i=0;i<items.length; i++){ 
                        String item = items[i];                            
                        if(! item.isEmpty()) {  
                            arr.add(item);
                        }     
                    }   
                }                
                f2.close();                
        }catch(IOException ex1){                             
            System.out.println("An error occurred."); 
            ex1.printStackTrace(); 
        } 
    }
    
    public void reduce (ArrayList<String> arr) {
        //Iterator<String> it1 = arr.iterator();        
        for (int i=0; i<arr.size(); i++){
            String item = arr.get(i);
            if (dataMap.containsKey(item)){
                int x = dataMap.get(item);
                x++;
                dataMap.put(item,x);
            } else {
                dataMap.put(item,1);
            }
        }
    }
    
    public void sort (ArrayList<String> arr){
        Collections.sort(arr);
        /*Iterator<String> it1 = arr.iterator();
        while (it1.hasNext()){
            String item = it1.next().toString();
            System.out.println(item);            
        }*/
    }
    
    //Sort hashmap according to values
    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm){
        //Create a list from the elements in the HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
 
        //Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
         
        //Put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> e : list) {
            temp.put(e.getKey(), e.getValue());
        }
        return temp;
    }
    
    
    public static void main(String[] args) {
        SHashMap shm = new SHashMap();         
        shm.map();
        ArrayList<String> arr = shm.getList();
        shm.sort(arr);
        shm.reduce(arr);
        HashMap<String, Integer> dataMap = shm.getDataMap();
        Map<String, Integer> sortedMap = shm.sortByValue(dataMap);
                       
                
        System.out.println("FREQUENT ITEMS:");
        for (Map.Entry<String, Integer> en : sortedMap.entrySet()) {
            if (en.getValue() >(phi*arr.size())){
                nf.increment();                
                System.out.println("Item = " + en.getKey() + ", Value = " + en.getValue());
            }            
        }    
                
        System.out.println("No. of distinct items:" + sortedMap.size());
        System.out.println("N:" + arr.size());
        System.out.println("No. frequent items:" + nf.longValue());
        
           
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory usage in bytes: " + memory);        
        
        
    }
     
}
