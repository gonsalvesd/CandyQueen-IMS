/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.candyQueenDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Falcon
 */
public class LoginForm {
   private String getUsername;
   private String getPassword;
   private String sql;
   private String getHash;
   private Table sqlTable;
   private Connection conn;
   private PreparedStatement prepStmt;
   private ResultSet cursor;
   
    public void requestPasswordHash(String forUsername){
        try{
            sql = "SELECT defupw_hist FROM history_within_history WHERE defuid_hist = ?;";
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db"); 
            conn.setAutoCommit(false);
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, forUsername);
            cursor = prepStmt.executeQuery();
            while (cursor.next()){
            getHash = cursor.getString(1);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                conn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void regenNewPassordHash(String setHash, String forUsername){
        sql = "UPDATE history_within_history SET defupw_hist = ? WHERE defuid_hist = ?;";
       try {
           Class.forName("org.sqlite.JDBC");
           conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
           conn.setAutoCommit(false);
           prepStmt = conn.prepareStatement(sql);
           prepStmt.setString(1, setHash);
           prepStmt.setString(2, forUsername);
           prepStmt.executeUpdate();
           conn.commit();
       } 
       catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }
       finally{
           try{
               conn.close();
           }
           catch (SQLException ex) {
               Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        
    }
    
    public String getRequestedPasswordHash(){
        return getHash;
    }
   
    
}
