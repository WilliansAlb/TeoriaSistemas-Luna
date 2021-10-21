/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author user-ubunto
 */
public class ConnectionDB {
    
    public static final String URL = "jdbc:mysql://localhost:3306/agenda_lunar?autoReconect=true&useSSL=false&serverTimezone=UTC";//auto reconnect
    public static final String USER = "root";//a test user, u can use a personal user
    public static final String PASSWORD = "5177";//my password for the user

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);           
        } catch (Exception e) {
            System.out.println("ERROR, " + e.getMessage());            
        }
        return connection;
    }
    
}
