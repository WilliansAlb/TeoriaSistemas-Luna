/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Comentario;
import POJOS.Etiqueta;
import POJOS.Evento;
import POJOS.Publicacion;
import POJOS.Usuario;
import java.sql.Connection;
import java.util.ArrayList;
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
        //pruebaUsuario(connection);
        
        //Prueba publicaciones
       // pruebaPublicacion(connection);
       
       //Prueba Eventos
       pruebaEventos(connection);
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
    
    private static void pruebaPublicacion(Connection connection){
        ControlDBPublicacion controlDBP = new ControlDBPublicacion(connection);
        
        
        // Get publicaciones segun nombre usuario
//        String nombreUsuario = "Admin";
//        List<Publicacion> publicaciones = controlDBP.getTodasPublicacionesPorNombreUsuario(nombreUsuario);
//        mostrarPublicaciones(publicaciones);
        
        //Insertar Publicacion 
//        List<String> etiquetas = new ArrayList<>();
//        etiquetas.add("1");
//        etiquetas.add("3");
//        
//        String fecha = "2021-07-12";
//        
//        String contenido = "El contenido de la publicacion es esto";
//        
//        String nombreUsuario = "Admin";
//        
//        boolean exito = controlDBP.insertarPublicacion(nombreUsuario, contenido, fecha, etiquetas);
//        if (exito) {
//            System.out.println("Insertado Con exito");
//        }else{
//            System.out.println("Insersion fallida");
//        }

        //Insertar Comentario
//        String idPublicacion = "11";
//        
//        String fecha = "2021-07-12";
//        
//        String contenido = "El contenido del comentario es corto";
//        
//        String nombreUsuario = "cbroadfield0";
//        
//        boolean exito = controlDBP.insertarComentario(nombreUsuario, idPublicacion, contenido, fecha);
//        if (exito) {
//            System.out.println("Comentario Insertado Con exito");
//        }else{
//            System.out.println("Comentario: Insersion fallida");
//        }

        //Mostrar Todas las etiquetas        
        List<Etiqueta> etiquetas = new ArrayList<>();
        etiquetas = controlDBP.getTodasEtiquetas();
        mostrarTodasEtiquetas(etiquetas);
        
    }
    
    private static void pruebaEventos(Connection connection){
        ControlDBEventos controlE = new ControlDBEventos(connection);
        
        //get eventos por usuario y mes y anio
        List<Evento> eventos = controlE.getTodosEventosPorIdUsuarioPorMes("Admin", 7, 2021);
        mostrarEventos(eventos);
    }
    
    private static void mostrarPublicaciones(List<Publicacion> publicaciones){
        for (Publicacion publicacion : publicaciones) {
            System.out.println("---------------");
            System.out.println("ID publ: " + publicacion.getIdPublicacion());
            System.out.println("ID usuario: " + publicacion.getNombreUsuario());
            System.out.println("Fecha: " + publicacion.getFecha());
            System.out.println("Contenido: " + publicacion.getContenido());
            
            mostrarEtiquetasPublicacion(publicacion.getEtiquetas());
            mostrarComentarios(publicacion.getComentarios());
        }
    }
    
    private static void mostrarComentarios(List<Comentario> comentarios){
        System.out.println("    *******Comentarios*******");            
        for (Comentario comentario : comentarios) {            
            System.out.println("    IdPublicacion:"+comentario.getIdPublicacion());
            System.out.println("    Id Usuario:"+comentario.getNombreUsuario());
            System.out.println("    Fecha:"+comentario.getFecha());
            System.out.println("    Contenido:"+comentario.getContenido());
        }
    }
    
    private static void mostrarEtiquetasPublicacion(List<String> etiquetas){
        System.out.println("    //////Etiquetas//////");
        for (String etiqueta : etiquetas) {            
            System.out.println("    Valor:"+etiqueta);
        }    
    }
    
    private static void mostrarTodasEtiquetas(List<Etiqueta> etiquetas){
        System.out.println("//////Etiquetas//////");
        for (Etiqueta etiqueta : etiquetas) {
            System.out.println("ID Etiqueta: " + etiqueta.getIdEtiqueta());
            System.out.println("Valor: " + etiqueta.getValor());
        }
    }
    
    private static void mostrarEventos(List<Evento> eventos){
        for (Evento evento : eventos) {
            System.out.println("-------------");
            System.out.println("Nombre: "+ evento.getNombre());
            System.out.println("Tipo: "+ evento.getTipo());
            System.out.println("Usuario: "+ evento.getIdUsuario());
            System.out.println("ID Siembra: "+ evento.getIdSiembra());
            System.out.println("Dsscripcion: "+ evento.getDescripcion());            
        }
    }
}
