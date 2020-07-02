/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import static chainmanagement.Driver.myStmt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salim
 */
public class Product extends Driver{
    
    private int stockID;
    private String brand;
    private String type;    //type is like "bisküvi"
    private double cost;
    private double price;
    private int stock;
    private String category = "OTHER";//FOOD OR ACTUEL OR TECHNOLOGY OR OTHER (UPPERCASE IS RECOMENDED)
    private String expireDate = "NONE"; //MUST BE WRİTTEN YYYY-MM-DD FORM!(SET NONE AS DEFAULT)
    private int place;

    public Product() {
        
    }
    
    public Product(String brand, String type, double cost, double price, int stock, int place, String category, String exDate){
        this.brand = brand;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.place = place;
        this.category=category;
        this.expireDate=exDate;
    }
    
    @Override
    public boolean add_item(Object fresh){ 
        
        if(fresh instanceof Product){
            try{
            int newId;
            Product prd = (Product)fresh;
            String sql = "INSERT INTO products(brand,type,cost,price,stock,category, expireDate,place) " + "VALUES("+"'"+prd.getBrand()+"','"+prd.getType()+"',"+prd.getCost()+","+prd.getPrice()+","+prd.getStock()+",'"+prd.getCategory()+"','"+prd.getExpireDate()+"',"+prd.getPlace()+")";
            myStmt.executeUpdate(sql);
            newId = DealAndUpdate.downloadID("product");
            if(newId == -1)
                return false;
            prd.setStockID(newId);
            return true;
             }
             catch(SQLException exc){
                 Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, exc);
                 return false;
             }        
        }  
        return false;
    }
    
    @Override
      public <Employee>Object get_item(int stockID){
        
        try{
            myRs = myStmt.executeQuery("SELECT * FROM products WHERE (stockID ="+ stockID +")");
            if(myRs.next())
            return setter(myRs);
        }
        catch(SQLException exc){
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, exc);
        }
        return null;
       
    }
      
      @Override
      public ArrayList<Product> getAll(){
            ArrayList<Product> myProduct = new ArrayList();           
            Product result;
            try{
                myRs = myStmt.executeQuery("SELECT * FROM products");
            while(myRs.next()){
                result = setter(myRs);
                myProduct.add(result);
            }
            }
            catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }                 
      return myProduct;      
}     
      
      
        @Override
    public ArrayList<Product> delete_item(ArrayList prdList,int stockID){    
       try{
           
          if(prdList.remove(findProduct(prdList, stockID))){
              myStmt.executeUpdate("DELETE FROM products WHERE stockID ="+ stockID);
              return prdList;
          }
               
        }
        catch(SQLException exc){
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, exc);
            return null;
        }
        
        return null;
    }
    
     public static Product findProduct(ArrayList<Product> list, int stockID){
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Product && list.get(i).getStockID()== stockID)
                return list.get(i);            
        } 
        return null;
    }
     
     public static void displayAll(ArrayList list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Product){
                System.out.println(list.get(i).toString());
            }
        }
    }
      
      public static Product setter(ResultSet mySet){
        Product result = new Product();

        try {
                result.setStockID((mySet.getInt("stockID")));
                result.setBrand(mySet.getString("brand"));
                result.setType(mySet.getString("type"));
                result.setCost(mySet.getDouble("cost"));
                result.setPrice(mySet.getDouble("price"));
                result.setStock(mySet.getInt("stock"));
                result.setCategory(mySet.getString("category"));
                result.setExpireDate(mySet.getString("expireDate"));
                result.setPlace(mySet.getInt("place"));
         
                return result;
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
           return null;
        }
    }

      
    // SETTER PART
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setPlace(int place) {
        this.place = place;
    }
    
    
    
    
//GETTER PART
    //--------------------------------------------------------------------------
    public int getStockID() {
        return stockID;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }
    
    public String getExpireDate() {
        return expireDate;
    }

    public int getPlace() {
        return place;
    }
    
    
      
//------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Stock ID :" + stockID + "\nBrand :" + brand + "\nType :" + type + "\nCost :" + cost + "\nPrice :" + price + "\nStock :" + stock + "\nCategory :" + category + "\nExpire Date :" + expireDate + '\n'+place+'\n';
    }
    
    
      
      
    
}
