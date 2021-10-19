/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;

/**
 *
 * @author user-ubunto
 */
public class ControlDBEventos {
    
    private Connection connection;
    
    public ControlDBEventos(Connection connection) {
        this.connection = connection;
    }
    
    
    
}
