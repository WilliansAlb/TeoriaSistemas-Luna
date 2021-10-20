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

    public Siembra() {
        this.idLugar = "";
        this.idUsuario = "";
        this.fechaSiembra = "";
        this.cosechado = 0;
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
    
    
    
    
    
}
