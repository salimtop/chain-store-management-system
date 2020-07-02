/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import chainmanagement.Driver;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
;

/**
 *
 * @author Salim
 */
public class Employee extends Driver{
    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String status = "worker";//worker as default
    private double salary = 0;
    private int permit = 15;
    private String vacationTo;
    private boolean onVacation;
    private String starting;
    private int place;
    private static ArrayList<Message> messages;
    
    public Employee(){
        
    }
    

    public Employee(String name, String surname, int place){
        //For status: WORKER = WORKER, SUPERVISOR = SV, CEO = CEO
        this.name = name;
        this.surname = surname;
        this.place = place;
        this.starting = LocalDate.now().toString();
    }  
    
    @Override
    public boolean add_item(Object fresh){ 
        
        if(fresh instanceof Employee){
            try{
            int newId;
            String uName=name+"_"+id;
            String uPass="12"+name.substring(0,3);
            String sql = "INSERT INTO employees(name,surname,username,password,salary,status,permit,startingDay,place) " + "VALUES("+"'"+name+"','"+surname+"','"+uName+"','"+uPass+"',"+salary+",'"+status+"',"+permit+",'"+starting+"',"+place+")";
            myStmt.executeUpdate(sql);
            newId = DealAndUpdate.downloadID("employee");
            if(newId == -1)
                return false;
            ((Employee)fresh).setId(newId);
            return true;
             }
             catch(SQLException exc){
                 Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, exc);
                 return false;
             }   
            
        }
        
        
        return false;
    }
    
    
    @Override
      public <Employee>Object get_item(int id){
        
        try{
            this.messages=Message.getAll_Messages(id);
            myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (id ="+ id+")");
            if(myRs.next())
            return setter(myRs);
        }
        catch(SQLException exc){
            System.out.println("Failed Employee/get_item");
        }
        return null;
       
    }
      
      
    @Override
      public ArrayList<Employee> getAll(){
            ArrayList<Employee> myEmps = new ArrayList();           
            Employee result;
            try{
                myRs = myStmt.executeQuery("SELECT * FROM employees");
            while(myRs.next()){
                result = setter(myRs);
                myEmps.add(result);
            }
            }
            catch (SQLException ex) {
                System.out.println("Failed Employee/getAll");
        }                 
      return myEmps;      
}
    
      @Override
    public ArrayList<Employee> delete_item(ArrayList empList,int id){    
       try{
           
          if(empList.remove(findEmployee(empList, id))){
              myStmt.executeUpdate("DELETE FROM employees WHERE id ="+ id);
              empList.remove(findEmployee(empList, id));
              return empList;
          }
               
        }
        catch(SQLException exc){
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, exc);
            return null;
        }
        
        return null;
    }
    
 
    
    public static Employee setter(ResultSet mySet){
        Employee result = new Employee();

        try {
                result.setId(mySet.getInt("id"));
                result.setName(mySet.getString("name"));
                result.setSurname(mySet.getString("surname"));
                result.setUsername(mySet.getString("username"));
                result.setPassword(mySet.getString("password"));               
                result.setStatus(mySet.getString("status"));
                result.setSalary(mySet.getDouble("salary"));
                result.setPermit(mySet.getInt("permit"));
                result.setOnVacation(mySet.getBoolean("onVacation"));
                result.setVacationTo(mySet.getString("vacationTo"));
                result.setStart_date(mySet.getString("startingDay"));
                result.setPlace(mySet.getInt("place"));
                return result;
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Employee findEmployee(ArrayList<Employee> list, int id){
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Employee && list.get(i).getId() == id)
                return list.get(i);            
        } 
        return null;
    }
    
    
    public static void displayAll(ArrayList list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Employee){
                System.out.println(list.get(i).toString());
            }
        }
    }
    
    
    public int getSupervisorID(){
        try {
           myRs = myStmt.executeQuery("SELECT * FROM employees WHERE (place= "+place+" and STATUS = 'SV')");
           while(myRs.next())
               return myRs.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return -1;
    }
    
    public boolean updateOnVacation(boolean choice){
        try { 
            myStmt.executeUpdate("update employees set onVacation = "+choice+" where id = "+ id );
            this.setOnVacation(choice);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public boolean updateVacationTo(String choice){
        try { 
            myStmt.executeUpdate("update employees set vacationTo = '"+choice+"' where id = "+ id );
            this.setVacationTo(choice);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
     
     public boolean updatePermit(int choice){
        try { 
            myStmt.executeUpdate("update employees set permit = "+choice+" where id = "+ id );
            this.setPermit(choice);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
     public boolean updateSalary(double choice){
        try { 
            myStmt.executeUpdate("update employees set salary = "+choice+" where id = "+ id );
            this.setSalary(choice);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }
     
     
     
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPermit(int permit) {
        this.permit = permit;
    }
    
    public void setStart_date(String start_date) {
        this.starting = start_date;
    }


    public void setPlace(int where) {
        this.place = where;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVacationTo(String vacationTo) {
        this.vacationTo = vacationTo;
    }

    public void setOnVacation(boolean onVacation) {
        this.onVacation = onVacation;
    }
    

    public void setStarting(String starting) {
        this.starting = starting;
    }
    
    
    
    
    
    public int getPlace() {
        return place;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getSalary() {
        return salary;
    }

    public int getPermit() {
        return permit;
    }

    public String getVacationTo() {
        return vacationTo;
    }

    public boolean isOnVacation() {
        return onVacation;
    }

    public String getStarting() {
        return starting;
    }
    /*CHANGE LATER
    public double calcComp() throws ParseException{
        Calendar now=Calendar.getInstance();
        Calendar start=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date cr=Calendar.getInstance().getTime();
        now.setTime(cr);
        Date sta=(Date) format.parse(this.starting);
        start.setTime(sta);
        int diff=(int)Duration.between(start.toInstant(), now.toInstant()).toDays()/30;
        
        return this.salary*(double)(1+(diff/60));
        
    }*/
    
    
    
    
    
    @Override
    public String toString() {
        return "ID :" + id + "\nName :" + name + "\nSurname :" + surname + "\nStatus :" + status + "\nSalary :" + salary + "\nPermit :" + permit + "\nStarting Date :" + starting + '\n'+"place "+ place+'\n';
    }

    
}
