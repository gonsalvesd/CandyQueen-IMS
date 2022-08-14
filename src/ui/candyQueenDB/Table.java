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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Falcon
 */
public class Table {
    
    private Connection conn;
    private Statement exeStmt;
    private PreparedStatement prepStmt;
    private ResultSet cursor;
    private Table sqlTable;
    private String credentialCount;
    private String itemCount;
    private String sql;
    private String tableName;
    private String columnValues;
    private String setColumn;
    private String toValue;
    private String cartoonSellingPrice;
    private String profitCount;
    private String money;
    private String stockValue;
    private String reOrder;
    private String expenseCount;
    private String expenseCount_;
    private String AccountNumberCount;
    private String[] itemNameList;
    private String[] barcodeList;
    private String[] supplierList;
    private String[] cartoonCostPriceList;
    private String[] expenseDateList;
    private String[] expenseTimeList;
    private String[] expenseNameList;
    private String[] expenseTotalList;
    private int[] quantityList;
    private int i = 0;
    private int accNumber;

        public void createTable(String TableName, String ColumnValues){
        tableName = TableName;
        columnValues = ColumnValues;
        sql = "CREATE TABLE IF NOT EXISTS "+tableName+"("+columnValues+");";
        //System.out.println(sql); //for debugging
        try{ 
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:candyq.db"); 
                conn.setAutoCommit(false);
                exeStmt = conn.createStatement(); 
                exeStmt.executeUpdate(sql);
                conn.commit();
        }
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(ui.candyQueenDB.Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try { 
                conn.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(ui.candyQueenDB.Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void initInsertToTable(String TableName, String ColumnValues){
        tableName = TableName;
        columnValues = ColumnValues;
        sql = "INSERT OR IGNORE INTO "+tableName+" VALUES( "+columnValues+");";
        //System.out.println(sql); //for debugging
        try{ 
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false);
            exeStmt = conn.createStatement();
            exeStmt.executeUpdate(sql);
            conn.commit();
        }     
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                conn.close();
            } 
            catch (SQLException ex) { 
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    }
    public void insertToTable(String TableName, String ColumnValues){
        tableName = TableName;
        columnValues = ColumnValues;
        sql = "INSERT INTO "+tableName+" VALUES( "+columnValues+");";
        //System.out.println(sql); //for debugging
        try{ 
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false);
            exeStmt = conn.createStatement();
            exeStmt.executeUpdate(sql);
            conn.commit();
        }     
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                conn.close();
            } 
            catch (SQLException ex) { 
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    }
    
    public void updateTable(String TableName, String ColumnValues, String VariableName){
        tableName = TableName;
        columnValues = ColumnValues;
        toValue = VariableName;
        sql = "UPDATE "+tableName+" SET "+columnValues+" "+toValue+";";
        //System.out.println(sql); //for debugging
        try{ 
            Class.forName("org.sqlite.JDBC"); conn = DriverManager.getConnection("jdbc:sqlite:candyq.db"); conn.setAutoCommit(false);
            exeStmt = conn.createStatement();
            exeStmt.executeUpdate(sql);
            conn.commit(); 
        }
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    }
    
    public void enableDisplay(){
        sql = "SELECT ITEMNAME, ITEMCODE, SUPPLIER, CTNCOSTPRICE, CTNQUANTITY FROM inv_ws ORDER BY ITEMNAME ASC";
        sqlTable = new Table();
        try{
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db"); 
            conn.setAutoCommit(false);
            sqlTable = new Table();
            itemCount = sqlTable.displayItemCount();
            itemNameList = new String[Integer.parseInt(itemCount)];
            barcodeList = new String[Integer.parseInt(itemCount)];
            supplierList = new String[Integer.parseInt(itemCount)];
            cartoonCostPriceList = new String [Integer.parseInt(itemCount)];
            quantityList = new int[Integer.parseInt(itemCount)];
            exeStmt = conn.createStatement();
            cursor = exeStmt.executeQuery(sql);
            i=0;
            while (cursor.next() &&  i != Integer.parseInt(itemCount)){
                itemNameList[i] = cursor.getString(1);
                barcodeList[i] = cursor.getString(2);
                supplierList[i] = cursor.getString(3);
                cartoonCostPriceList[i] = cursor.getString(4);
                quantityList[i] = cursor.getInt(5);
                i++;
            }
            sql = "SELECT Date, Time, Expense, value FROM expenses ORDER BY Time ASC;";
            cursor = exeStmt.executeQuery(sql);
            
            expenseCount = sqlTable.displayExpenseCount();
            expenseDateList = new String[Integer.parseInt(expenseCount)];
            expenseTimeList = new String[Integer.parseInt(expenseCount)];
            expenseNameList = new String[Integer.parseInt(expenseCount)];
            expenseTotalList = new String[Integer.parseInt(expenseCount)];
            i=0;
            while (cursor.next() && i != Integer.parseInt(expenseCount)){
                expenseDateList[i] = cursor.getString(1);
                expenseTimeList[i] = cursor.getString(2);
                expenseNameList[i] = cursor.getString(3);
                expenseTotalList[i]= cursor.getString(4);
                i++;
            }
            sql = "SELECT count(AccountNumber) FROM customers;";
            cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){
                AccountNumberCount = cursor.getString(1);
                if (AccountNumberCount == null){
                    AccountNumberCount = "0";
                }
                else{
                    accNumber = Integer.parseInt(AccountNumberCount);
                    accNumber ++;
                }
            }
        }
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{
            try{
                conn.close(); 
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    }
    
    public ResultSet setCartoonSellPrice(String iName){
        try{
            sql = "SELECT CTNSELLINGPRICE FROM inv_ws WHERE ITEMNAME = ?";
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db"); 
            conn.setAutoCommit(false);
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, iName);
            cursor = prepStmt.executeQuery();
            while (cursor.next()){
            cartoonSellingPrice = cursor.getString(1);
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
        return cursor;
    }
    
    public void setProfitCount(String iName){
        try{
            sql = "SELECT ProfitCount FROM inv_ws WHERE ITEMNAME = ?";
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db"); 
            conn.setAutoCommit(false);
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, iName);
            cursor = prepStmt.executeQuery();
            while (cursor.next()){
            profitCount = cursor.getString(1);
            }
            conn.commit();
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
    
    public int getAccountNumberCount(){
        return accNumber;
    }
    
    public String[] getDisplayName(){
        return itemNameList;
    }
    
    public String[] getDisplayBarcode(){
        return barcodeList;
    }
    
    public String[] getDisplaySupplier(){
        return supplierList;
    }
    
    public String[] getDisplayCartoonCost(){
        return cartoonCostPriceList;
    }
    
    public int[] getDisplayQuantity(){
        return quantityList;
    }
    
    public String getDisplaySellingPrice(){
        return cartoonSellingPrice;
    }
    
    public String getDisplayProfitCount(){
        return profitCount;
    }
    
    public String[] getDisplayExpenseDate(){
        return expenseDateList;
    }
    
    public String[] getDisplayExpenseTime(){
        return expenseTimeList;
    }
    
    public String[] getDisplayExpenseName(){
        return expenseNameList;
    }
    
    public String[] getDisplayExpenseTotal(){
        return expenseTotalList;
    }
    
    public String displayItemCount(){
        try{
            sql = "SELECT count(*) FROM inv_ws;";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false);
            exeStmt = conn.createStatement();
            cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){
            itemCount = cursor.getString(1);
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{ 
                conn.close(); 
            } 
            catch (SQLException ex) { 
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
        return itemCount;
    }
    
    public String displayExpenseCount(){
        try{
            sql = "SELECT count(*) FROM expenses;";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false);
            exeStmt = conn.createStatement();
            cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){
            expenseCount = cursor.getString(1);
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{ 
                conn.close(); 
            } 
            catch (SQLException ex) { 
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
        return expenseCount;
    }
    
    public String displayDistinctExpenseCount(){
        try{
            sql = "SELECT DISTINCT count(*) FROM expenses;";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false);
            exeStmt = conn.createStatement();
            cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){
            expenseCount_ = cursor.getString(1);
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{ 
                conn.close(); 
            } 
            catch (SQLException ex) { 
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
        return expenseCount_;
    }
    
    public String displayOverallMoney(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT OverallMoneyEarnedWS FROM bank WHERE id = 1;";
            exeStmt = conn.createStatement(); cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                money = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return money;
    }

public String displayOverallProfit(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT OverallProfitEarnedWS FROM bank WHERE id = 1;";
            exeStmt = conn.createStatement(); cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                profitCount = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return profitCount;
    }

public String displayMoney(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT MoneyEarnedWS FROM bank WHERE id = 1;";
            exeStmt = conn.createStatement(); cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                money = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return money;
    }

public String displayProfit(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT ProfitEarnedWS FROM bank WHERE id = 1;";
            exeStmt = conn.createStatement(); cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                profitCount = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return profitCount;
    }

public String displayStockValue(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT WholesaleStockWorth FROM stock_worth WHERE id = 1;";
            exeStmt = conn.createStatement();
            cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                stockValue = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stockValue;
    }

public String displayReOrder(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT count(*) FROM inv_ws WHERE CTNQUANTITY <= 3;";
            exeStmt = conn.createStatement(); cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                reOrder = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return reOrder;
    }

public String displayCredentialCount(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:candyq.db");
            conn.setAutoCommit(false); 
            sql = "SELECT count(*) FROM history_within_history;";
            exeStmt = conn.createStatement(); cursor = exeStmt.executeQuery(sql);
            while (cursor.next()){ 
                credentialCount = cursor.getString(1); 
            }
        } 
        catch (ClassNotFoundException | SQLException ex){ 
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally{ 
            try{
                conn.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return credentialCount;
    }
}