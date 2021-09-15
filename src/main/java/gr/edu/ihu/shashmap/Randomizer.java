/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.edu.ihu.shashmap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Hyae
 */
public class Randomizer {
    
    public static void main(String[] args) {
                
        //create text file of random strings/integers      
        String[] input = new String[10000000];                
        try {
        FileWriter f1 = new FileWriter("C:\\Users\\random.txt", true);
        Random r = new Random();
        for (int j=0; j<input.length; j++) {
            input[j] = "a" + (r.nextInt(450000));
            f1.write(input[j] + "\n");            
        }        
        f1.close();
        } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }
    }
    
}
