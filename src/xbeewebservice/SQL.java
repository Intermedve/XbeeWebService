/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xbeewebservice;

import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Jimmy
 */
public class SQL {
    
    private Statement statement = null;
        
    //Constructor
    public SQL (){
        String host = "localhost";
        String user = "root";
        String password = "Google2";
        String dbName = "test";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            statement = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbName, user, password).createStatement();
        } catch (Exception ex) {
            System.out.print("Error at SQL Initialization: " + ex + "\n");
        }
    }
    
    public void SaveData(String input) {
        try {
            statement.executeUpdate("INSERT INTO testtable (text) VALUES ('" + input + "')");
        } catch (Exception ex) {
            System.out.print("Error at SQL data insertion: " + ex + "\n");
        }
    }
    
}
