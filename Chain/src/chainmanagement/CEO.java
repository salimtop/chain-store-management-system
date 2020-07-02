/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author disco
 */
public class CEO extends Employee{
    ArrayList<Store> stores;
    
    
    private double calc_totalendor(){
        double total=0;
        for(int i=0;i<stores.size();i++){
            total+=stores.get(i).calc_endor30();
        }
        return total/stores.size();
    }










}




