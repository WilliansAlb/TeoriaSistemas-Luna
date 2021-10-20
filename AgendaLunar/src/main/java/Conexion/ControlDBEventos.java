/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Cultivo;
import POJOS.Evento;
import POJOS.Lugar;
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

    /**
     * Obtiene todos los eventos de un usuario por mes y anio
     * @param id
     * @param mes
     * @param anio
     * @return
     */
    public List<Evento> getTodosEventosPorIdUsuarioPorMes(String id, int mes, int anio) {
        YearMonth yearMonthObject = YearMonth.of(anio, mes);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        String fecha1 = anio + "-" + mes + "-01";
        String fecha2 = anio + "-" + mes + "-" + daysInMonth;

        System.out.println(fecha1);
        System.out.println(fecha2);

        List<Evento> eventos = getTodasPublicacionesPorNombreUsuario(id, fecha1, fecha2);
        return eventos;
    }

    /**
     * Obtiene las publicaciones de un usuario en un mes
     * @param nombreUsuario
     * @param fecha1
     * @param fecha2
     * @return
     */
    public List<Evento> getTodasPublicacionesPorNombreUsuario(String nombreUsuario, String fecha1, String fecha2) {
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

    /**
     * Obtiene todos los eventos que sean siembra
     * @param nombreUsuario
     * @return
     */
    public List<Evento> getTodosEventosQueSeanSiembra(String nombreUsuario) {
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

    public boolean insertarEventoSiembra(String idUsuario, String nombre, String fechaEvento, String descripcion, String tipo, String idLugar, String idCultivo) {
        boolean exitoso = true;

        //Inserta la siembra
        boolean siembraInsertada = insertarSiembra(idLugar, idCultivo, fechaEvento);
        if (siembraInsertada) {

            //get id ultima siembra
            int idSiembra = getUltimoIdSiembraInsertada();

            //Inserta el evento 
            String query = "INSERT INTO eventos (id_usuario,id_siembra,nombre,fechaEvento,descripcion,tipo) VALUES (?,?,?,?,?,?)";

            try (PreparedStatement preSt = connection.prepareStatement(query);) {
                preSt.setString(1, idUsuario);
                preSt.setInt(2, idSiembra);
                preSt.setString(3, nombre);
                preSt.setString(4, fechaEvento);
                preSt.setString(5, descripcion);
                preSt.setString(6, tipo);

                preSt.executeUpdate();

                preSt.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                exitoso = false;
            }
        } else {
            exitoso = false;
        }

        return exitoso;
    }

    public boolean insertarSiembra(String idLugar, String idCultivo, String fechaSiembra) {
        boolean exitoso = true;

        String query = "INSERT INTO siembra (id_lugar,id_cultivo,fechaSiembra,cosechado) VALUES (?,?,?,0)";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, idLugar);
            preSt.setString(2, idCultivo);
            preSt.setString(3, fechaSiembra);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }

    public boolean insertarEventoNoSiembra(String idUsuario, String nombre, String fechaEvento, String descripcion, String tipo) {
        boolean exitoso = true;
        String query = "INSERT INTO eventos (id_usuario,nombre,fechaEvento,descripcion,tipo) VALUES (?,?,?,?,?)";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, idUsuario);            
            preSt.setString(2, nombre);
            preSt.setString(3, fechaEvento);
            preSt.setString(4, descripcion);
            preSt.setString(5, tipo);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }
        return exitoso;
    }

    public int getUltimoIdSiembraInsertada() {
        int idSiembra = 0;

        String query = "SELECT id FROM siembra ORDER BY id DESC";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                idSiembra = result.getInt(1);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return idSiembra;
    }
        
    public List<Cultivo> getTodosLosTiposDeCultivos(){
        List<Cultivo> cultivos = new ArrayList<>();

        String query = "SELECT * FROM cultivo";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {            

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Cultivo cultivo = new Cultivo();
                cultivo.setIdCultivo(result.getString(1));
                cultivo.setTipo(result.getString(2));

                cultivos.add(cultivo);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return cultivos;
    }
    
    public List<Lugar> getTodosLugares(){
        List<Lugar> lugares = new ArrayList<>();
        String query = "SELECT * FROM lugar";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {            

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Lugar lugar = new Lugar();
                lugar.setIdLugar(result.getString(1));
                lugar.setNombre(result.getString(2));
                lugar.setUbicacion(result.getString(3));
                lugar.setClima(result.getString(4));
                lugares.add(lugar);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lugares;
    }

}
