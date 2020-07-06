/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;


import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Salim
 */


public abstract class Driver{
    public static Connection myCon;
    public static Statement myStmt;
    protected static ResultSet myRs;
    public static boolean isInitilized = false;
    
    
    
    public Driver(){
        if(isInitilized == false){
            try {
            //1. Get a connection to database;
            myCon = DriverManager.getConnection("jdbc:mysql://****************************","******" ,"**********");
            //2. Create a statement
            myStmt = myCon.createStatement();
            //3. Execute SQL query
            
            //myStmt.executeUpdate("DELETE FROM employees WHERE name = ''");
           
            //myRs = myStmt.executeQuery("SELECT * FROM employees");
            
            //4. Process the result set
            /*while (myRs.next()){
                System.out.println(myRs.getString("surname")+ ", "+ myRs.getString("name"));
            }      */      
             } catch (SQLException ex) {
                System.out.println("No Connection!");
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       isInitilized = true;
        }
    }

    public abstract boolean add_item(Object list);
   
    public abstract <E>Object get_item(int id);
    
    public abstract ArrayList getAll();
    
    public abstract ArrayList delete_item(ArrayList list, int id);
    
    
        
    
    
    
}
