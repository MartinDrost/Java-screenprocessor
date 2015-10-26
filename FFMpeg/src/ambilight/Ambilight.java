/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ambilight;
import ambilight.domain.*;

/**
 *
 * @author Martin
 */
public class Ambilight {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Init");
        
        AmbilightProcessor processor = new AmbilightProcessor(1000/30, new int[]{26, 26});
        
        //Keep thread alive
        while(true)
        {}
    }
    
}
