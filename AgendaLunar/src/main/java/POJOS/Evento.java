/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

/**
 *
 * @author user-ubunto
 */
public class Evento {
    
    private String idUsuario;
    private String idSiembra; // sino hay siembra == null
    private String nombre;
    private String fechaEvento;
    private String descripcion;
    private String tipo;

    public Evento() {
        this.idUsuario = "";
        this.idSiembra = "";
        this.nombre = "";
        this.fechaEvento = "";
        this.descripcion = "";
        this.tipo = "";
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdSiembra() {
        return idSiembra;
    }

    public void setIdSiembra(String idSiembra) {
        this.idSiembra = idSiembra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    
    
}
