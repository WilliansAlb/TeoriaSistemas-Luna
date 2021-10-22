/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Conexion.ConnectionDB;
import Conexion.ControlDBPublicacion;
import POJOS.Publicacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author James Gramajo
 */
@WebServlet(name = "PublicarPost", urlPatterns = {"/PublicarPost"})
public class PublicarPost extends HttpServlet {
String ID;
String texto;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ID= request.getParameter("usuario");
            texto=request.getParameter("texto");

            ID="mcoupe1";

            doPost(request,response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            ID= request.getParameter("usuario");
            texto=request.getParameter("texto");

            ID="mcoupe1";
            List<Publicacion> publicaciones = new ArrayList<>();
                
                
                //establece la conexion a la DB
                ConnectionDB connect = new ConnectionDB();
                Connection connection = connect.getConnection();
                
                ControlDBPublicacion ControlP=new ControlDBPublicacion(connection);
                ControlDBPublicacion ControlP2=new ControlDBPublicacion(connection);
                try{
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                System.out.println(timeStamp);
                System.out.println(ID);
                System.out.println(texto);
                List<String> etiq = new ArrayList<>();
                ControlP2.insertarPublicacion(ID, texto, timeStamp, etiq);
                } catch(Exception e){
                    
                }
                //realiza el ingreso de la publicacion en la BD
                

                //obtiene las publicaciones 
                publicaciones = ControlP.getTodasPublicacionesPorNombreUsuario(ID);
                //obtiene las etiquetas
                
                request.getSession().setAttribute("usuario", ID);
                request.setAttribute("CON", connection);
                
                request.setAttribute("PUBLICACIONES", publicaciones);
                request.getRequestDispatcher("Muro").forward(request, response);

            //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
