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
import java.util.ArrayList;
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
@WebServlet(name = "Muro", urlPatterns = {"/Muro"})
public class Muro extends HttpServlet {
String ID;
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
             ID="mcoupe1";
             System.out.println(ID);
             doPost(request,response);
            //request.setAttribute("USER", USER);
            //request.getRequestDispatcher("/PagesGerente/CrearGerente.jsp").forward(request, response);
        }
    }

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }


    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               
                List<Publicacion> publicaciones = new ArrayList<>();
                
                
                //establece la conexion a la DB
                ConnectionDB connect = new ConnectionDB();
                Connection connection = connect.getConnection();
                
                ControlDBPublicacion ControlP=new ControlDBPublicacion(connection);
                //obtiene las publicaciones 
                publicaciones = ControlP.getTodasPublicacionesPorNombreUsuario(ID);
                //obtiene las etiquetas
                
                request.getSession().setAttribute("usuario", ID);
                request.setAttribute("CON", connection);
                
                request.setAttribute("PUBLICACIONES", publicaciones);
                request.getRequestDispatcher("vistas/Muro.jsp").forward(request, response);
                
        
        
        
        //processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


//<%
//            HttpSession sesionAsociaciones = request.getSession();
//            boolean correcto = false;
//            if (sesionAsociaciones.getAttribute("tipo") != null) {
//                if (sesionAsociaciones.getAttribute("tipo").toString().equalsIgnoreCase("CLIENTE")) {
//                    correcto = true;
//                } else {
//                    response.sendRedirect("home.jsp");
//                }
//            } else {
//                response.sendRedirect("login.jsp");
//            }
// %>
