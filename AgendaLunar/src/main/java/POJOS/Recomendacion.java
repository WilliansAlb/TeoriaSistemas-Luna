/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

/**
 *
 * @author sergi
 */
public class Recomendacion {
    
    private int idSiembra;    
    private String contenido;
    
    
    public Recomendacion(){
        idSiembra = -1;
        contenido = "";        
    }

    public int getIdSiembra() {
        return idSiembra;
    }

    public void setIdSiembra(int idSiembra) {
        this.idSiembra = idSiembra;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    
    
    
}
