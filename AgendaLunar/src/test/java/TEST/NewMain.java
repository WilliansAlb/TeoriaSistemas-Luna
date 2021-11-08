/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import Conexion.ConnectionDB;
import Conexion.ControlDBPublicacion;
import POJOS.Publicacion;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import static javax.servlet.jsp.tagext.TagAttributeInfo.ID;

/**
 *
 * @author James Gramajo
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<Publicacion> publicaciones = new ArrayList<>();

        //establece la conexion a la DB
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();

        ControlDBPublicacion ControlPP = new ControlDBPublicacion(connection);
        //obtiene las publicaciones 
        publicaciones = ControlPP.busquedaEnPublicacionesPrivadas("INVERSO", "Admin");
        for(Publicacion pub: publicaciones ){
            pub.getContenido();
        }
    }

}
