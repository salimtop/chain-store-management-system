/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import static chainmanagement.Driver.myStmt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salim
 */

/*
ıs there a ceo? check
calculate endorsement
number of employee
number of "uct
calculate profit
add an employee
stock decrease/increase, save sales
clean method for clean items which are not in a store
*/
public class Store extends Driver  implements ParseString {
    
    private int storeID;
    private String name;
    private String city;
    private double[] endor = new double[31];//endorsement in last 30 days
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    
    public Store(){
        
    }

    public Store(String name, String city) {
        this.name = name;
        this.city = city;    
    }
    
    
    public boolean addEmployee(Employee worker){
        worker.setPlace(storeID);
        if(employees.add(worker)){
            worker.add_item(worker);
            return true;
        }
            
        return false;
    }
    
     public boolean removeEmployee(Employee worker){
        worker.delete_item(employees, worker.getId());
        if(employees.remove(worker))
            return true;
        return false;
            
    }
    
     public boolean addProduct(Product thing){
        thing.setPlace(storeID);
        if(products.add(thing)){
            thing.add_item(thing);
            return true;
        }
            
        return false;
    }
    
     public boolean removeProduct(Product thing){
        thing.delete_item(products, thing.getStockID());
        if(products.remove(thing))
            return true;
        return false;
            
    }
     
    public ArrayList<Employee> getAllEmps(){
        ArrayList<Employee> myEmps = new ArrayList();
        Employee result;
        try{
            myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (place ="+ storeID+")");
            while(myRs.next()){
                result = Employee.setter(myRs);
                myEmps.add(result);
            }
            this.employees=myEmps;
            return myEmps;
        }
        catch (SQLException ex){
            System.out.println("Failed Employee/getAll");
        }
        return null;
    }
    
    public ArrayList<Store> setAllEmps(ArrayList<Store> list){
        ArrayList<Employee> myEmps = new ArrayList();
        Employee result;
        for (int i = 0; i < list.size(); i++) {
            try{
            myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (place ="+ list.get(i).getStoreID()+")");
            while(myRs.next()){
                result = Employee.setter(myRs);
                myEmps.add(result);
            }
            list.get(i).setEmployees(myEmps);
        }
        catch (SQLException ex){
            System.out.println("Failed store set all Employee");
        }
           
        }
         return list;
    }
     
     public ArrayList<Product> getAllProds(){
        ArrayList<Product> myPrds = new ArrayList();
        Product result;
        try{
            myRs = myStmt.executeQuery("SELECT * FROM products WHERE (place ="+ storeID+")");
            while(myRs.next()){
                result = Product.setter(myRs);
                myPrds.add(result);
            }
            this.products=myPrds;
            return myPrds;
        }
        catch (SQLException ex){
            System.out.println("Failed Store Product/getAll");
        }
        return null;
    }
    
     
     public ArrayList<Store> setAllProds(ArrayList<Store> list){
        ArrayList<Product> myPrds = new ArrayList();
        Product result;
         for (int i = 0; i < list.size(); i++) {
         try{
            myRs = myStmt.executeQuery("SELECT * FROM products WHERE (place ="+ list.get(i).getStoreID()+")");
            while(myRs.next()){
                result = Product.setter(myRs);
                myPrds.add(result);
            }
            list.get(i).setProducts(myPrds);
        }
        catch (SQLException ex){
            System.out.println("Failed Store setAllProds");
        }
         }
          return list;
    }
     

    //Adds Store to Database
     @Override
    public boolean add_item(Object fresh){ 
        
        if(fresh instanceof Employee){
            try{
            int newId;
            String sql = "INSERT INTO stores(storeID,name,city,endor) " + "VALUES("+storeID+",'"+name+"','"+city+"','"+Arrays.toString(endor)+"')";
            myStmt.executeUpdate(sql);
            newId = DealAndUpdate.downloadID("store");
            if(newId == -1)
                return false;
            ((Store)fresh).setStoreID(newId);
            return true;
             }
             catch(SQLException exc){
                 return false;
             }        
        }  
        return false;
        
    }
    
    //Gets Store from Database
    @Override
      public <Store>Object get_item(int storeID){
        
        try{
            myRs = myStmt.executeQuery("SELECT * FROM stores WHERE (storeID ="+ storeID +")");
            if(myRs.next())
            return setter(myRs);
        }
        catch(SQLException exc){
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, exc);
        }
        return null;
       
    }
    //Gets all Store from Database
  @Override
      public ArrayList<Store> getAll(){
            ArrayList<Store> myStore = new ArrayList();           
            Store result = new Store();
            try{
                myRs = myStmt.executeQuery("SELECT * FROM stores");
            while(myRs.next()){
                result = setter(myRs);
                myStore.add(result);
            }
            }
            catch (SQLException ex) {
        }   
         
        myStore = result.setAllEmps(myStore);
        myStore = result.setAllProds(myStore);
            
      return myStore;      
}
    
   //Delete Store from Database
        @Override
    public ArrayList<Store> delete_item(ArrayList storeList,int storeID){    
       try{
           
          if(storeList.remove(findStore(storeList, storeID))){
              myStmt.executeUpdate("DELETE FROM stores WHERE storesID ="+ storeID);
              myStmt.executeUpdate("DELETE FROM employees WHERE place ="+ storeID);
              myStmt.executeUpdate("DELETE FROM products WHERE place ="+ storeID);
              return storeList;
          }
               
        }
        catch(SQLException exc){
            return null;
        }
        
        return null;
    }
    
    public static Store findStore(ArrayList<Store> list, int id){
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Store && list.get(i).getStoreID()== id)
                return list.get(i);            
        } 
        return null;
    }
    
    public double calc_endor30(){
    
        double a=0;
        for(int i=0;i<endor.length;i++){
            a+=endor[i];
        }
        return a/endor.length;
    }
    
    
    //Update
    public boolean updateEndor(double[] arr){
        
        try { 
            myStmt.executeUpdate("update stores set endor = '"+ Arrays.toString(arr) + "' where storeID = "+ storeID );
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    //--------------------------------------------------------------------------
    //Setters
    
    private static Store setter(ResultSet mySet){
        Store result = new Store();

        try {
                result.setStoreID((mySet.getInt("storeID")));
                result.setCity(mySet.getString("city"));
                result.setName(mySet.getString("name"));
                result.saveEndor(mySet.getString("endor"));
                //result.setEmployees(result.getAllEmps());
                //result.setProducts(result.getAllProds());
                return result;
        } catch (SQLException ex) {
           return null;
        }
    }
    
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEndor(double[] endor) {
        this.endor = endor;
    }


    
    private void saveEndor(String text) {
        this.endor = ParseString.stringTodoubleArr(text);
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    

    public String getName() {
        return name;
    }

    //--------------------------------------------------------------------------
    //Getters
    public double[] getEndor() {
        return endor;
    }

    public int getStoreID() {
        return storeID;
    }

    public String getCity() {
        return city+" / "+name+" / "+storeID;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
        

}


