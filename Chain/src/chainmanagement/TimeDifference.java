/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Salim
 */
public interface TimeDifference {
    
    static int isCloseBbf(String date){
        //date must be yyyy-mm-dd
        String[] info = date.replaceAll("\\s", "").split("-"); 
        
        Calendar bbf = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            bbf.set(Integer.parseInt(info[0]), Integer.parseInt(info[1])-1, Integer.parseInt(info[2])-1);
            Date cr = Calendar.getInstance().getTime();
            current.setTime(cr);
            double between= (Duration.between(cr.toInstant(), bbf.toInstant()).toSeconds()+40000)/86400;
            System.out.println(between);
            if(between < 10)
                return 2;
       
            else
                return 0;
        }
        catch(NullPointerException e){
            return -1;
        }
        
        
    }
    
    
    static boolean isVacOver(String date){
        //date must be yyyy-mm-dd
        String[] info = date.replaceAll("\\s", "").split("-"); 
        
        Calendar bbf = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            bbf.set(Integer.parseInt(info[0]), Integer.parseInt(info[1])-1, Integer.parseInt(info[2])-1);
            Date cr = Calendar.getInstance().getTime();
            current.setTime(cr);
            int between=(int)(Duration.between(current.toInstant(), bbf.toInstant()).toDays()-0.5);
            System.out.println(between);
            if(between < 0)
                return true;
            return false;
            
        }
        catch(NullPointerException e){
            return false;
        }
        
        
    }
    
    
    static String dayAgo(int ago){
        
        Calendar current = Calendar.getInstance();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            Date cr = Calendar.getInstance().getTime();
            current.setTime(cr);
            current.add(Calendar.DAY_OF_YEAR, -ago);
            return formatter.format(current.getTime()).toString();
            
        }
        catch(NullPointerException e){
            return null;
        }
        
    }
    
}
