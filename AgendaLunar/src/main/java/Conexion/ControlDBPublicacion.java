/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Comentario;
import POJOS.Etiqueta;
import POJOS.Publicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class ControlDBPublicacion {

    private Connection connection;

    public ControlDBPublicacion(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtiene todas las publicaciones segun el id del usuario en este caso su
     * nombre
     *
     * @param nombreUsuario
     * @return
     */
    public List<Publicacion> getTodasPublicacionesPorNombreUsuario(String nombreUsuario) {
        List<Publicacion> publicaciones = new ArrayList<>();

        String query = "SELECT * FROM publicacion WHERE id_usuario = ? ORDER BY fecha_publicacion DESC";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            preSt.setString(1, nombreUsuario);

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Publicacion publicacion = new Publicacion();
                publicacion.setIdPublicacion(result.getString(1));
                publicacion.setNombreUsuario(result.getString(2));
                publicacion.setContenido(result.getString(3));
                publicacion.setFecha(result.getString(4));
                publicaciones.add(publicacion);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //Obtener las etiquetas y comentarios de la publicacion
        for (Publicacion publicacion : publicaciones) {
            List<String> etiquetas = getTodasEtiquetasPorIdPublicacion(publicacion.getIdPublicacion());
            publicacion.setEtiquetas(etiquetas);

            List<Comentario> comentarios = getTodosComentariosPorIdPublicacion(publicacion.getIdPublicacion());
            publicacion.setComentarios(comentarios);
        }

        return publicaciones;
    }

    /**
     * Obtiene todos los nombres de las etiquetas segun el id de la publicacion
     *
     * @param idPublicacion
     * @return
     */
    public List<String> getTodasEtiquetasPorIdPublicacion(String idPublicacion) {
        List<String> etiquetas = new ArrayList<>();

        String query = "SELECT e.nombre FROM etiqueta_publicacion AS ep INNER JOIN etiqueta AS e WHERE ep.id_etiqueta = e.id AND ep.id_publicacion = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            preSt.setString(1, idPublicacion);

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                etiquetas.add(result.getString(1));
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return etiquetas;
    }

    /**
     * Obtiene todos los comentarios segun el id de la publicacion
     *
     * @param idPublicacion
     * @return
     */
    public List<Comentario> getTodosComentariosPorIdPublicacion(String idPublicacion) {
        List<Comentario> comentarios = new ArrayList<>();

        String query = "SELECT c.id,u.nombre,c.id_publicacion,c.comentario,c.fecha FROM comentario AS c INNER JOIN usuario AS u WHERE id_publicacion = ? AND u.nombreusuario = c.id_usuario ORDER BY c.fecha ASC";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            preSt.setString(1, idPublicacion);

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Comentario comentario = new Comentario();
                comentario.setNombreUsuario(result.getString(2));
                comentario.setIdPublicacion(result.getString(3));
                comentario.setContenido(result.getString(4));
                comentario.setFecha(result.getString(5));

                comentarios.add(comentario);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return comentarios;
    }

    /**
     * Inserta la publicacion en la DB, ademas de insertar las etiquetas
     * correspondientes
     *
     * @param nombreUsuario
     * @param contenido
     * @param fecha
     * @param etiquetas
     * @return
     */
    public boolean insertarPublicacion(String nombreUsuario, String contenido, String fecha, List<String> etiquetas) {
        boolean exitoso = true;

        String query = "INSERT INTO publicacion (id_usuario, contenido, fecha_publicacion) VALUES (?, ?, ?)";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreUsuario);
            preSt.setString(2, contenido);
            preSt.setString(3, fecha);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error:... " + e.getMessage());
            exitoso = false;
        }

        if (exitoso) {
            //Traer ultima publicacion
            String idPublicacion = getUltimaPublicacionInsertada();
            //Revisar etiqueta
            //Get idsEtiquetas
            List<String> idsEtiquetas = revisarEtiquetas(etiquetas,idPublicacion);
            System.out.println("INGRESARA ETIQUETAS EN DB");
            exitoso = insertarEtiquetasPublicacion(idsEtiquetas, idPublicacion);
        }

        return exitoso;
    }

    /**
     * Obtiene el id de la ultima publicacion insertada
     *
     * @return
     */
    public String getUltimaPublicacionInsertada() {
        String idPublicacion = "";

        String query = "SELECT id FROM publicacion ORDER BY id DESC";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                idPublicacion = result.getString(1);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return idPublicacion;
    }

    /**
     * Inserta la relacion entre las etiquetas y la publicacion
     *
     * @param etiquetas
     * @param idPublicacion
     * @return
     */
    public boolean insertarEtiquetasPublicacion(List<String> etiquetas, String idPublicacion) {
        boolean exitoso = true;

        String query = "INSERT INTO etiqueta_publicacion (id_etiqueta, id_publicacion) VALUES (?, ?)";
        for (String etiqueta : etiquetas) {
            try (PreparedStatement preSt = connection.prepareStatement(query);) {
                preSt.setString(1, etiqueta);
                preSt.setString(2, idPublicacion);

                preSt.executeUpdate();

                preSt.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                exitoso = false;
            }
        }

        return exitoso;
    }

    /**
     * Obtiene todas las etiquetas que estan en la base de datos
     *
     * @return
     */
    public List<Etiqueta> getTodasEtiquetas() {
        List<Etiqueta> etiquetas = new ArrayList<>();

        String query = "SELECT * FROM etiqueta";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setIdEtiqueta(result.getString(1));
                etiqueta.setValor(result.getString(2));

                etiquetas.add(etiqueta);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return etiquetas;
    }

    /**
     * Inserta la etiqueta en la base de datos
     *
     * @param valorEtiqueta
     * @return
     */
    public boolean insertarEtiqueta(String valorEtiqueta) {
        boolean exitoso = true;

        String query = "INSERT INTO etiqueta (nombre) VALUES(?)";
        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, valorEtiqueta);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }

    /**
     * Inserta el comentario en la base de datos Hay que enviar la fecha en tipo
     * yyyy-mm-dd
     *
     * @param idUsuario
     * @param idPublicacion
     * @param comentario
     * @param fecha
     * @return
     */
    public boolean insertarComentario(String idUsuario, String idPublicacion, String comentario, String fecha) {
        boolean exitoso = true;

        String query = "INSERT INTO comentario (id_usuario, id_publicacion, comentario, fecha) VALUES (?, ?, ?, ?);";
        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, idUsuario);
            preSt.setString(2, idPublicacion);
            preSt.setString(3, comentario);
            preSt.setString(4, fecha);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }

    public List<String> revisarEtiquetas(List<String> etiquetas,String idPublicacion) {
        List<String> idetiquetas = new ArrayList<>();

        for (String etiqueta : etiquetas) {
            int idEt = existeEtiqueta(etiqueta);
            if (idEt == -1) {
                //no existe
                insertarEtiqueta(etiqueta);
                //Get ultimo idEtiqueta
                idetiquetas.add(String.valueOf(getUltimaEtiquetaInsertada()));
            }else{
                idetiquetas.add(String.valueOf(idEt));
            }
        }
        return idetiquetas;
    }

    public int existeEtiqueta(String nombreEtiqueta) {
        int idEt = -1;

        String query = "SELECT * FROM etiqueta WHERE nombre = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreEtiqueta);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                idEt = (result.getInt(1));
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return idEt;
    }
    
    public int getUltimaEtiquetaInsertada(){
        int idEt = -1;
        String query = "SELECT * FROM etiqueta ORDER BY id DESC";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            
            ResultSet result = preSt.executeQuery();
            if(result.next()) {
                idEt = (result.getInt(1));
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return idEt;
    }

    
    public List<Publicacion> getTodasPublicacionesPublicas() {
        List<Publicacion> publicaciones = new ArrayList<>();

        String query = "SELECT * FROM publicacion AS p INNER JOIN usuario AS u WHERE u.tipo = 0 AND u.nombreusuario = p.id_usuario ORDER BY p.fecha_publicacion DESC";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {            

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Publicacion publicacion = new Publicacion();
                publicacion.setIdPublicacion(result.getString(1));
                publicacion.setNombreUsuario(result.getString(2));
                publicacion.setContenido(result.getString(3));
                publicacion.setFecha(result.getString(4));
                publicaciones.add(publicacion);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //Obtener las etiquetas y comentarios de la publicacion
        for (Publicacion publicacion : publicaciones) {
            List<String> etiquetas = getTodasEtiquetasPorIdPublicacion(publicacion.getIdPublicacion());
            publicacion.setEtiquetas(etiquetas);

            List<Comentario> comentarios = getTodosComentariosPorIdPublicacion(publicacion.getIdPublicacion());
            publicacion.setComentarios(comentarios);
        }

        return publicaciones;
    }
    
    
    
}
