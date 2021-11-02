/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Usuario;
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
public class ControlDBUsuario {

    private Connection connection;

    public ControlDBUsuario(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtiene todos los usuarios que estan insertados en la base de datos
     *
     * @return usuarios
     */
    public List<Usuario> getTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        String query = "SELECT * FROM usuario";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(result.getString(1));
                usuario.setNombreCompleto(result.getString(2));
                usuario.setPassword(result.getString(3));
                usuario.setTipo(Integer.parseInt(result.getString(4)));
                usuarios.add(usuario);
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Obtiene el usuario segun el nombre y contrasenia enviados Se puede
     * utilizar para el login
     *
     * @param nombreUsuario
     * @param password
     * @return
     */
    public Usuario getUsuarioPorNombreUsuarioYPassword(String nombreUsuario, String password) {
        Usuario usuario = null;

        String query = "SELECT * FROM usuario WHERE nombreusuario = ? AND contrasenia = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreUsuario);
            preSt.setString(2, password);

            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                usuario = new Usuario();
                usuario.setNombreUsuario(result.getString(1));
                usuario.setNombreCompleto(result.getString(2));
                usuario.setPassword(result.getString(3));
                usuario.setTipo(Integer.parseInt(result.getString(4)));
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return usuario;
    }

    /**
     * Se inserta un usuario en la base de datos Los parametros tienen que venir
     * en seco :v
     *
     * @param nombreUsuario
     * @param nombreCompleto
     * @param contrasenia
     * @param tipo
     * @return
     */
    public boolean insertarUsuario(String nombreUsuario, String nombreCompleto, String contrasenia, int tipo) {
        boolean exitoso = true;

        String query = "INSERT INTO usuario (nombreusuario, nombre, contrasenia, tipo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreUsuario);
            preSt.setString(2, nombreCompleto);
            preSt.setString(3, contrasenia);
            preSt.setInt(4, tipo);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }

    /**
     * Obtiene el usuario segun el nombre de usuario
     * Se puede usar para validar si el usuario ya existe con ese nombre de usuario
     * @param nombreUsuario
     * @return
     */
    public Usuario getUsuarioPorNombreUsuario(String nombreUsuario) {
        Usuario usuario = null;

        String query = "SELECT * FROM usuario WHERE nombreusuario = ? ";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreUsuario);            

            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                usuario = new Usuario();
                usuario.setNombreUsuario(result.getString(1));
                usuario.setNombreCompleto(result.getString(2));
                usuario.setPassword(result.getString(3));
                usuario.setTipo(Integer.parseInt(result.getString(4)));
            }

            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return usuario;
    }
    
    /**
     * Se elimina un usuario 
     *
     * @param nombreUsuario
     * @return
     */
    public boolean eliminarUsuario(String nombreUsuario) {
        boolean exitoso = true;

        String query = "DELETE FROM usuario WHERE nombreUsuario = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreUsuario);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }
    
    /**
     *
     * @param nombreUsuario
     * @param nombreCompleto
     * @param contrasenia
     * @param tipo
     * @return
     */
    public boolean actualizarUsuario(String nombreUsuario, String nombreCompleto, String contrasenia, int tipo){
        boolean exitoso = true;

        String query = "UPDATE usuario SET nombre = ?, contrasenia = ? , tipo = ? WHERE nombreUsuario = ?";

        try (PreparedStatement preSt = connection.prepareStatement(query);) {
            preSt.setString(1, nombreCompleto);
            preSt.setString(2, contrasenia);
            preSt.setInt(3, tipo);
            preSt.setString(4, nombreUsuario);

            preSt.executeUpdate();

            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }
    

}
