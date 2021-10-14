/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Usuario;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class TestConnection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();
        
        //Prueba conexion Usuarios        
        pruebaUsuario(connection);
        
    }
    
    private static void pruebaUsuario(Connection connection){
        //Pruebas de conexion con usuarios
        ControlDBUsuario controlDbUsuarios = new ControlDBUsuario(connection);
        
        //Get todos los usuarios
//        List<Usuario> usuarios = controlDbUsuarios.getTodosUsuarios();
//        mostrarUsuarios(usuarios);
        
        //Get un usuario 
//        String nombreUsuario = "Admin";
//        String password = "LiDvvKG";
//        Usuario usuario = controlDbUsuarios.getUsuarioPorNombreUsuarioYPassword(nombreUsuario, password);
//        mostrarUsuario(usuario);
        
        //Insertar un usuario
//        String nombreUsuario2 = "Admin2";
//        String nombreCompleto2 = "Administrador2";
//        String password2 = "admin2";
//        int tipo2 = 0;
//        boolean exito = controlDbUsuarios.insertarUsuario(nombreUsuario2, nombreCompleto2, password2, tipo2);
//        if (exito) {
//            System.out.println("Usuario insertado exitosamente");
//        }else{
//            System.out.println("Error al insertar usuario");
//        }

        //Get existencia usuario
//        String nombreUsuario3 = "Admin3";
//        Usuario usuario3 = controlDbUsuarios.getUsuarioPorNombreUsuario(nombreUsuario3);
//        if (usuario3 != null) {
//            System.out.println("El usuario existe");
//        }else{
//            System.out.println("El usuario no existe");
//        }
        
    }
    
    private static void mostrarUsuarios(List<Usuario> usuarios){
        for (Usuario usuario : usuarios) {
            System.out.println("-----------------");
            System.out.println("Usuario: " + usuario.getNombreUsuario());
            System.out.println("Nombre: " + usuario.getNombreCompleto());
            System.out.println("Password: " + usuario.getPassword());
            System.out.println("Tipo: " + usuario.getTipo());
        }
    }
    
    private static void mostrarUsuario(Usuario usuario){        
        System.out.println("-----------------");
        System.out.println("Usuario: " + usuario.getNombreUsuario());
        System.out.println("Nombre: " + usuario.getNombreCompleto());
        System.out.println("Password: " + usuario.getPassword());
        System.out.println("Tipo: " + usuario.getTipo());
        
    }
    
}
