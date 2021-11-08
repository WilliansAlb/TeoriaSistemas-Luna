/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Conexion.ConnectionDB;
import Conexion.ControlDBPublicacion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author James Gramajo
 */
@WebServlet(name = "EliminarPost", urlPatterns = {"/EliminarPost"})
public class EliminarPost extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ID= (String)request.getSession().getAttribute("usuario");
        String Id_Pubblicacion= request.getParameter("idpost");
        
        System.out.println(ID);
        System.out.println(Id_Pubblicacion);
        
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();
        ControlDBPublicacion ControlP = new ControlDBPublicacion(connection);
        try{
            boolean flag=ControlP.eliminarPublicacion(Id_Pubblicacion);
            
            Gson gs = new Gson();
            String json="";
            String resultado="";
            System.out.println(json+" el json en java");
            
            
            if (flag){
                System.out.println("SE ELIMINO EL POST");
                resultado="1";
            }else{
                System.out.println("NOOO SE ELIMINO EL POST");
                resultado="0";
            }
            
            
            
        }catch(Exception e){
            System.out.println("ERROR AL ELIMINAR POST");
        }
        
        request.getSession().setAttribute("usuario", ID);
        
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
