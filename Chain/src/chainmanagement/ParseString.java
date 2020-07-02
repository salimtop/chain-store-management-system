/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

/**
 *
 * @author Salim
 */
public interface ParseString{
    
    public static double[] stringTodoubleArr(String arr){
        
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        double[] results = new double[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Double.parseDouble(items[i]);
          } catch (NumberFormatException nfe) {
              
          }
    }
    return results;
}
    
}
