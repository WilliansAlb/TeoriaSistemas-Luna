/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class ControlDBEventos {

    private Connection connection;

    public ControlDBEventos(Connection connection) {
        this.connection = connection;
    }

    public List<Evento> getTodosEventosPorIdUsuarioPorMes(String id, int mes, int anio) {
        YearMonth yearMonthObject = YearMonth.of(anio, mes);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        
        String fecha1 = anio+"-"+mes+"-01";
        String fecha2 = anio+"-"+mes+"-"+daysInMonth;
        
        System.out.println(fecha1);
        System.out.println(fecha2);
        
        List<Evento> eventos = getTodasPublicacionesPorNombreUsuario(id,fecha1,fecha2);
        return eventos;
    }

    public List<Evento> getTodasPublicacionesPorNombreUsuario(String nombreUsuario,String fecha1,String fecha2) {
        List<Evento> eventos = new ArrayList<>();

        String query = "SELECT * FROM eventos WHERE id_usuario = ? AND fechaEvento BETWEEN ? AND ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            preSt.setString(1, nombreUsuario);
            preSt.setString(2, fecha1);
            preSt.setString(3, fecha2);

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Evento evento = new Evento();
                evento.setIdUsuario(result.getString(2));
                evento.setIdSiembra(result.getString(3));
                evento.setNombre(result.getString(4));
                evento.setFechaEvento(result.getString(5));
                evento.setDescripcion(result.getString(6));
                evento.setTipo(result.getString(7));
                
                eventos.add(evento);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return eventos;
    }
    
    public List<Evento> getTodasEventosQueSeanSiembra(String nombreUsuario) {
        List<Evento> eventos = new ArrayList<>();

        String query = "SELECT * FROM eventos WHERE id_usuario = ? AND id_siembra != \'NULL\'";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            preSt.setString(1, nombreUsuario);

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Evento evento = new Evento();
                evento.setIdUsuario(result.getString(2));
                evento.setIdSiembra(result.getString(3));
                evento.setNombre(result.getString(4));
                evento.setFechaEvento(result.getString(5));
                evento.setDescripcion(result.getString(6));
                evento.setTipo(result.getString(7));
                
                eventos.add(evento);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return eventos;
    }
    
    

}
