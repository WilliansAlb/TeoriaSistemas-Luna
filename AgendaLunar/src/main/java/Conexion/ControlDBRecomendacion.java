/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import POJOS.Evento;
import POJOS.Recomendacion;
import POJOS.Siembra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sergi
 */
public class ControlDBRecomendacion {
    
    //fases lunares
    protected static final int NEW_MOON = 0;
    protected static final int WAXING_CRESCENT = 1;
    protected static final int FIRST_QUARTER = 2;
    protected static final int WAXING_GIBBOUS = 3;
    protected static final int FULL_MOON = 4;
    protected static final int WANING_GIBBOUS = 5;
    protected static final int THIRD_QUARTER = 6;
    protected static final int WANING_CRESCENT = 7;
    
    
    private Connection connection;
    
    public ControlDBRecomendacion(Connection connection){
        this.connection = connection;
    }
    
    public List<Recomendacion> getTodasRecomendaciones(String nombreUsuario, int mes, int anio) {

        List<Evento> eventos = new ArrayList<>();
        
        YearMonth yearMonthObject = YearMonth.of(anio, mes);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        String fecha1 = anio + "-" + mes + "-01";
        String fecha2 = anio + "-" + mes + "-" + daysInMonth;

        String query = "SELECT * FROM eventos WHERE id_usuario = ? AND id_siembra != \'NULL\' AND fechaEvento BETWEEN ? AND ?";

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
        
        List<Recomendacion> recomendaciones = new ArrayList();
        //calcular la fase
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        int day   = localDate.getDayOfMonth();
        System.out.println(year +" / " + month +" / " + day);
        int fase = getFaseLunar(year,month,day);
        
        for (Evento evento : eventos) {
           recomendaciones.add(verificarFasesDarRecomendacion(evento,fase));           
        }        
        return recomendaciones;
    }
    
    private Recomendacion verificarFasesDarRecomendacion(Evento evento, int fase){
        
        Siembra siembra;
        String cultivo = null;
        String contenido = "No existe recomendacion para el cultivo";
        
        String query = "SELECT * FROM siembra,cultivo WHERE siembra.id=? AND siembra.id_cultivo = cultivo.id LIMIT 1";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);) {

            preSt.setInt(1,Integer.parseInt(evento.getIdSiembra()));

            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                siembra = new Siembra();               
                cultivo = result.getString("tipo");
                siembra.setIdLugar(result.getString("id_lugar"));
                siembra.setCosechado(result.getInt("cosechado"));
                siembra.setFechaSiembra(result.getString("fechaSiembra"));          
            }
            result.close();
            preSt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }        
        if (cultivo != null) {
            
            contenido = getRecomendacion(fase,cultivo);            
        }
        
        Recomendacion recomendacion = new Recomendacion();
        recomendacion.setContenido(contenido);
        recomendacion.setIdSiembra(Integer.parseInt(evento.getIdSiembra()));
        return recomendacion;        
    }
    
    protected int getFaseLunar(int year,int month,int day){
        double c, e , jd , b , bc = 0;
        if (month < 3) {
            year--;
            month += 12;
        }

        ++month;

        c = 365.25 * year;

        e = 30.6 * month;

        jd = c + e + day - 694039.09; //jd is total days elapsed

        jd /= 29.5305882; //divide by the moon cycle


        b = (int) jd; //int(jd) -> b, take integer part of jd

        jd -= b; //subtract integer part to leave fractional part of original jd

        System.out.println("---" + jd);
        bc = Math.round(jd * 100); //scale fraction from 0-8 and round

        System.out.println("---" + bc + "% en dia " + day);
        b = Math.round(jd * 8); //scale fraction from 0-8 and round
        System.out.println("---" + b);
        //var a = Math.round(jd * 27); //scale fraction from 0-8 and round
        //console.log("---"+b+" day" + day);
        if (b >= 8) {
            b = 0; //0 and 8 are the same so turn 8 into 0
        }
        /*String txt = "";
        if (bc >= 0 && bc <= 10) {
            txt = "Luna nueva";
        } else if (bc >= 25 && bc <= 35) {
            txt = "Cuarto Creciente";
        } else if (bc >= 50 && bc <= 60) {
            txt = "Luna llena";
        } else if (bc >= 75 && bc <= 85) {
            txt = "Cuarto Menguante";
        }
        */
        // 0 => New Moon
        // 1 => Waxing Crescent Moon
        // 2 => Quarter Moon
        // 3 => Waxing Gibbous Moon
        // 4 => Full Moon
        // 5 => Waning Gibbous Moon
        // 6 => Last Quarter Moon
        // 7 => Waning Crescent Moon
        return (int) b;
    }
    
    private String getRecomendacion(int fase, String cultivo){
        /*
        creciente -> FIRST_QUARTER,WAXING_CRESCENT

        llena -> FULL_MOON

        meguante -> THIRD_QUARTER

        nuevo -> NEW_MOON

        descendente ->WANING_CRESCENT

        ascendente -> WANING_GIBBOUS,WAXING_GIBBOUS
        */
        
        String recomendacion = "No hay recomendacion disponible";
        switch(cultivo){
            case "Raices y tuberculos":{
                switch(fase){
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case THIRD_QUARTER:{
                        return "Seguir cultivando y abonar los que ya esten";
                    }                    
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Es un buen momento para el reposo";
                    }
                }
                break;
            }
            case "cerales":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Es un buen momento para el reposo";
                    }
                }
                break;
            }
            case "Leguminosas":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Es un buen momento para el reposo";
                    }
                }
                break;
            }
            case "Oleaginosas":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Es un buen momento para el reposo";
                    }
                }
                break;
           }
            case "Hortalizas":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Seguir cultivando mas hortalizas";   
                     }
                    
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para cosechar";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable cosechar";
                    }
                }
                break;
            }
            case "Frutales":{
                
                 switch(fase){
                     case FIRST_QUARTER:
                     case WAXING_CRESCENT:{
                         return "Podar y sembrar mas frutales";                                   
                     }
                     case WANING_GIBBOUS:
                     case WAXING_GIBBOUS:{
                      return "Seguir cultivando mas frutos";
                     }
                     case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                     case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                     case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                 }                
                break;
            }
            case "Ornamentales":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Seguir cultivando mas ornamentales";
                    }
                }
                break;
            }
            case "Medicinales":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Seguir cultivando mas ornamentales";
                    }
                }
                break;
            }
            case "Tropicales":{
                switch(fase){
                    case FULL_MOON:{
                        return "Periodo de reposo,no es buen momento para trabajar la tierra";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case NEW_MOON:{
                        return "Recomendable realizar limpieza";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Es un buen momento para el reposo";
                    }
                }
                break;
            }
            case "Pasto":{                
                switch(fase){
                    case FULL_MOON:{
                        return "Eliminar las plantas espontaneas";
                    }
                    case NEW_MOON:{
                        return "Retirar plantas espontaneas, podas";
                    }
                    case FIRST_QUARTER:
                    case WAXING_CRESCENT:{
                        return "Buen momento para aplicar fertilizantes y tratamientos preventivos";
                    }
                    case THIRD_QUARTER:{
                        return "Limpiar y mantener la siembra, recomendable colocar abono";
                    }
                    case WANING_CRESCENT:{
                        return "Recomendable aplicar fertilizante y cuidar la tierra";
                    }
                    case WANING_GIBBOUS:
                    case WAXING_GIBBOUS:{
                      return "Recomendable arrancar los esquejes";
                    }
                }                
                break;
            }
            default:{
                break;
            }
        } 
        return recomendacion;
    }
}
