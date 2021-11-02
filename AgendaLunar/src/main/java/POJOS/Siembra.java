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
public class Siembra {
    private String idLugar;
    private String idUsuario;
    private String fechaSiembra;
    private int cosechado;
    private String nombre;
    private String nombreUsuario;
    private String idSiembra;
    private String idCultivo;

    public Siembra() {
        this.idLugar = "";
        this.idUsuario = "";
        this.fechaSiembra = "";
        this.cosechado = 0;
        this.nombre = "";
        this.nombreUsuario = "";
        this.idSiembra = "";
    }

    public String getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(String idLugar) {
        this.idLugar = idLugar;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(String fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public int getCosechado() {
        return cosechado;
    }

    public void setCosechado(int cosechado) {
        this.cosechado = cosechado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getIdSiembra() {
        return idSiembra;
    }

    public void setIdSiembra(String idSiembra) {
        this.idSiembra = idSiembra;
    }

    public String getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(String idCultivo) {
        this.idCultivo = idCultivo;
    }
    
    
    
    
    
}
