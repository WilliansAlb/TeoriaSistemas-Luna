/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Cultivo;
import POJOS.Evento;
import POJOS.Lugar;
import POJOS.Siembra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     *
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
     *
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
     *
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

    public int insertarCultivo(String tipo) {
        int exitoso = -1;

        String query = "INSERT INTO cultivo (tipo) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, tipo);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exitoso = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = -1;
        }
        return exitoso;
    }
    
    public String obtenerNombreCultivo(int id_siembra){
        String query = "SELECT c.tipo FROM cultivo AS c, siembra AS s WHERE s.id = ? AND s.id_cultivo = c.id;";
        String retorno = "";
        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setInt(1, id_siembra);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                retorno = result.getString(1);
            }
            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return retorno;
    }
    
    public int insertarLugar(String usuario, String nombre, String ubicacion, String clima) {
        int exitoso = -1;

        String query = "INSERT INTO lugar (id_usuario,nombre,ubicacion,clima) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, usuario);
            statement.setString(2, nombre);
            statement.setString(3, ubicacion);
            statement.setString(4, clima);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exitoso = generatedKeys.getInt(1);
                } 
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = -1;
        }
        return exitoso;
    }

    public boolean insertarEventoSiembra(String idUsuario, String nombre, String fechaEvento, String descripcion, String tipo, String idLugar, String idCultivo) {
        boolean exitoso = true;

        //Inserta la siembra
        boolean siembraInsertada = insertarSiembra(idLugar, idCultivo, fechaEvento, nombre, idUsuario);
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

    public boolean insertarEventoPorIdSiembra(String idUsuario, String nombre, String fechaEvento, String descripcion, String tipo, int idSiembra) {
        boolean exitoso = true;

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

        return exitoso;
    }

    public boolean insertarSiembra(String idLugar, String idCultivo, String fechaSiembra, String nombre, String idUsuario) {
        boolean exitoso = true;

        String query = "INSERT INTO siembra (id_lugar,id_cultivo,fechaSiembra,cosechado,nombre,id_usuario) VALUES (?,?,?,0,?,?)";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, idLugar);
            preSt.setString(2, idCultivo);
            preSt.setString(3, fechaSiembra);
            preSt.setString(4, nombre);
            preSt.setString(5, idUsuario);

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

    public List<Cultivo> getTodosLosTiposDeCultivos() {
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

    public List<Lugar> getTodosLugares(String usuario) {
        List<Lugar> lugares = new ArrayList<>();
        String query = "SELECT * FROM lugar WHERE id_usuario = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, usuario);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Lugar lugar = new Lugar();
                lugar.setIdLugar(result.getString(1));
                lugar.setNombre(result.getString(3));
                lugar.setUbicacion(result.getString(4));
                lugar.setClima(result.getString(5));
                lugares.add(lugar);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lugares;
    }

    /**
     * Obtiene todas las siembras que tiene un usuario Devuelve la siembra con
     * su id,nombre de Lugar, nombre de Cultivo, valor de cosechado,nombre de la
     * siembra, y el id usuario
     *
     * @param idUsuario
     * @return
     */
    public List<Siembra> getTodasLasSiembrasPorUsuario(String idUsuario) {
        List<Siembra> siembras = new ArrayList<>();
        String query = "SELECT s.id,l.nombre,c.tipo,s.fechaSiembra,s.cosechado,s.nombre,s.id_usuario FROM siembra AS s INNER JOIN lugar AS l INNER JOIN cultivo AS c WHERE s.id_lugar = l.id AND s.id_cultivo = c.id AND s.id_usuario = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, idUsuario);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Siembra siembra = new Siembra();
                siembra.setIdSiembra(result.getString(1));
                siembra.setIdLugar(result.getString(2));
                siembra.setIdCultivo(result.getString(3));
                siembra.setFechaSiembra(result.getString(4));
                siembra.setCosechado(result.getInt(5));
                siembra.setNombre(result.getString(6));
                siembra.setIdUsuario(result.getString(7));
                siembras.add(siembra);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return siembras;
    }

    /**
     * Actualiza la siembra a tipo de cosechado 1, osea que esta cosechada esa
     * cosa pues
     *
     * @param idSiembra
     * @return
     */
    public boolean actualizarSiembraCosechar(int idSiembra) {
        boolean exitoso = true;

        String query = "UPDATE siembra SET cosechado = 1 WHERE id = ?";
        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setInt(1, idSiembra);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }
    
    public ArrayList<String> porcentajes(String usuario){
        ArrayList<String> siembras = new ArrayList<>();
        String query = "SELECT id_usuario, CONCAT( ROUND((( SELECT COUNT(*) FROM eventos WHERE id_usuario = ? AND id_siembra IS NULL ) / COUNT(*) * 100 ),\n" +
"         2 ), '%') AS porcentaje1, CONCAT( ROUND(( (SELECT COUNT(*) FROM eventos WHERE id_siembra IS NOT NULL AND id_usuario = ?)  / COUNT(*) * 100 ),\n" +
"         2 ), '%') AS porcentaje2 FROM eventos WHERE id_usuario = ?;";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, usuario);
            preSt.setString(2, usuario);
            preSt.setString(3, usuario);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                siembras.add(result.getString(1));
                siembras.add(result.getString(2));
                siembras.add(result.getString(3));
            }
            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return siembras;
    }
    
    public ArrayList<String> tendencias(String usuario){
    ArrayList<String> siembras = new ArrayList<>();
        String query = "SELECT cu.tipo, (SELECT COUNT(*) FROM cultivo AS c, siembra AS s WHERE s.id_cultivo = cu.id AND cu.id = c.id AND s.id_usuario = ?)"
                + " AS cuanto FROM cultivo AS cu GROUP BY cu.id;";
        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, usuario);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                siembras.add(result.getString(1));
                siembras.add(result.getString(2));
            }
            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return siembras;
    }
    
    public ArrayList<String> eventos_por_mes(String usuario){
    ArrayList<String> siembras = new ArrayList<>();
        String query = "SELECT MonthName(fechaEvento) AS mes, count(*) AS numFilas FROM eventos WHERE id_usuario = ? GROUP BY mes;";
        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, usuario);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                siembras.add(result.getString(1));
                siembras.add(result.getString(2));
            }
            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return siembras;
    }

    public ArrayList<List<String>> datos_siembras(String usuario) {
        ArrayList<List<String>> siembras = new ArrayList<>();
        String query = "SELECT s.id, l.nombre, c.tipo, s.nombre, s.fechaSiembra, s.cosechado FROM cultivo AS c, siembra AS s, lugar AS l WHERE c.id = s.id_cultivo AND l.id = s.id_lugar AND s.id_usuario = ? ;";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, usuario);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                List<String> datos = new ArrayList<>();
                datos.add(result.getString(1));
                datos.add(result.getString(2));
                datos.add(result.getString(3));
                datos.add(result.getString(4));
                datos.add(result.getString(5));
                datos.add(result.getString(6));
                siembras.add(datos);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return siembras;
    }
}
