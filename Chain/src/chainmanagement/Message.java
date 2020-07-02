/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainmanagement;

import static chainmanagement.Driver.myStmt;
import static chainmanagement.Employee.setter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACK
 */
public class Message extends Driver{
    
    private int fromID;
    private int toID;
    private String subject;
    private String context;
    private boolean checked=false;//set false as default in sql

    public Message() {
    }
    
    
    
    public Message(int fromID, int toID, String subject, String context){
    this.fromID = fromID;
    this.toID = toID;
    this.subject = subject;
    this.context = context;        
}
    
     @Override
    public boolean add_item(Object fresh){ 
        
        if(fresh instanceof Message){
            try{
            String sql = "INSERT INTO messages(fromID,toID,subject,context,checked) " + "VALUES("+((Message) fresh).fromID+","+((Message) fresh).toID+",'"+((Message) fresh).getSubject()+"','"+((Message) fresh).getContext()+"',"+((Message) fresh).checked+")";
            myStmt.executeUpdate(sql);
            return true;
             }
             catch(SQLException exc){
                 Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, exc);
                 return false;
             }    
        }
        
      return false;         
}
    
    @Override
      public <Message>Object get_item(int toID){
        
       try {
                myRs = myStmt.executeQuery("SELECT * FROM messages WHERE (toID ="+ toID+")");
                if(myRs.next())
                return setter(myRs);
        } catch (SQLException ex) {
              
                System.out.println("failed <Message>Object get_item");
        }
        
       return null;
    }
      
      
      public static ArrayList<Message> getAll_Messages(int toID){
          
          ArrayList<Message> myMsgs = new ArrayList();           
            Message result;
            try{
                myRs = myStmt.executeQuery("SELECT * FROM messages WHERE (toID ="+ toID+")");
            while(myRs.next()){
                result = setter(myRs);
                myMsgs.add(result);
            }
            }
            catch (SQLException ex) {
                System.out.println("Failed Message/getAll_Messages");
        }                 
      return myMsgs;       
      }

     @Override
    public ArrayList getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
      @Override
    public ArrayList<Employee> delete_item(ArrayList msgList,int toID){    
       try{
           
          if(msgList.remove(findMessage(msgList, toID))){
              myStmt.executeUpdate("DELETE FROM messages WHERE toID ="+ toID);
              msgList.remove(findMessage(msgList, this.toID));
              return msgList;
          }
               
        }
        catch(SQLException exc){
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, exc);
            return null;
        }
        
        return null;
    }
    
    
    
    
     public static Message findMessage(ArrayList<Message> list, int toID){
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof Message && list.get(i).getToID() == toID)
                return list.get(i);            
        } 
        return null;
    }
    
      
      public static Message setter(ResultSet mySet){
        Message result = new Message();

        try {
                result.setFromID(mySet.getInt("fromID"));
                result.setToID(mySet.getInt("toID"));
                result.setSubject(mySet.getString("subject"));
                result.setContext(mySet.getString("context"));
                result.setChecked(mySet.getBoolean("checked"));
                return result;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
      
      
    //update  
    public void updateCheck(){
        try {
            myStmt.executeUpdate("update messages set checked = true where toID = "+ toID );
            this.checked = true;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    //setter
    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public void setToID(int toID) {
        this.toID = toID;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getFromID() {
        return fromID;
    }

    public int getToID() {
        return toID;
    }

    public String getSubject() {
        return subject;
    }

    public String getContext() {
        return context;
    }

    public boolean isChecked() {
        return checked;
    }

   
    

      
      
      
      


}
