/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import static chainmanagement.Driver.myRs;
import static chainmanagement.Driver.myStmt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salim
 */
public interface DealAndUpdate{
    
    //to get all datas and distribute objects MANDATORY TO USE IN INITIAL
    public static ArrayList<Store> collect_and_deal(){
        
        ArrayList<Employee> empList = new ArrayList<>();
        ArrayList<Product> prdList = new ArrayList<>();
        ArrayList<Store> strList;
        Store chain = new Store();
        
        strList = chain.getAll();
        
        for (int i = 0; i < strList.size(); i++) {
            try {
                myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (place = "+ strList.get(i).getStoreID()+")");
                while(myRs.next())
                    empList.add(Employee.setter(myRs));
                               
                myRs = myStmt.executeQuery("SELECT * FROM products WHERE (place = "+ strList.get(i).getStoreID()+")");
                while(myRs.next())
                    prdList.add(Product.setter(myRs));
                
                strList.get(i).setEmployees(empList);
                strList.get(i).setProducts(prdList);
                empList.clear();
                empList.clear();
            
            } catch (SQLException ex) {
                 Logger.getLogger(DealAndUpdate.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        return strList;
    }
    
    public static boolean updateDatabase(String classname, String variableName, String value, int id){
        //you can parse number values into string
        classname = classname.toLowerCase();
        variableName = variableName.toLowerCase();
        try { 
            myStmt.executeUpdate("update "+classname+"s"+" set "+ variableName+" = "+ value + " where id = "+ id );
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DealAndUpdate.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public static int downloadID(String classname){
        //you can parse number values into string
        classname = classname.toLowerCase()+"s";
        
        try { 
            if(classname.equals("employees")){
                myRs = myStmt.executeQuery("SELECT max(id) FROM "+classname);
                if(myRs.next())
                return myRs.getInt(1);
            }
            else if(classname.equals("products")){
                myRs = myStmt.executeQuery("SELECT max(stockID) FROM "+classname);
                if(myRs.next())
                return myRs.getInt(1);
            }
             else if(classname.equals("stores")){
                myRs = myStmt.executeQuery("SELECT max(storekID) FROM "+classname);
                if(myRs.next())
                return myRs.getInt(1);
               
            }
            else
                System.out.println("Wrong table name");
            
        } catch (SQLException ex) {
            Logger.getLogger(DealAndUpdate.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Download ID Error");
            return -1;
        }
        
        return -1;
        
    }
   
    
    
    
    
     public static int checkUsername(String username){
                      
       String control = "";
       int id = 0;
       if(Driver.isInitilized == false) new Employee();
        try{
            myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (username = '"+username+"')");
            while(myRs.next()){
                control = myRs.getString("username");
                id = myRs.getInt("id");
            }
                
            if(control == null || control == "")
                return -1;
            return id;
        }
        catch(SQLException exc){
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, exc);
            return -1;
        }
        catch(NullPointerException e){
            System.out.println("kes lan");
            return -1;
        }
    }

    
    public static int checkPassword(String username,String password){
        String control = "";
        int id = 0;
        
        try{
            myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (password ='"+ password+"')");
            while(myRs.next()){
                control = myRs.getString("password");
                id = myRs.getInt("id");
            }
                
            if((control == null || control == "")&&(id == 0))
                return -1;
            return id;
        }
        catch(SQLException exc){
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, exc);
            return -1;
        }
    }
    
}
