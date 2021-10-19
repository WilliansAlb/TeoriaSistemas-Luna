/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class Publicacion {
    
    private String idPublicacion;
    private String nombreUsuario;
    private String contenido;
    private String fecha;
    private List<String> etiquetas;   
    private List<Comentario> comentarios;

    public Publicacion() {
        this.idPublicacion = "";
        this.nombreUsuario = "";
        this.contenido = "";
        this.fecha = "";
        this.etiquetas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }    
    

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
    
    public void insertarEtiqueta(String nombreE){
        this.etiquetas.add(nombreE);
    }
    
    public void insertarComentario(Comentario comentario){
        this.comentarios.add(comentario);
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
}
