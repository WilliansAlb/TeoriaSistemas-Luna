/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Conexion.ConnectionDB;
import Conexion.ControlDBUsuario;
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
 * @author user-ubunto
 */
@WebServlet(name = "CrearUsuario", urlPatterns = {"/CrearUsuario"})
public class CrearUsuario extends HttpServlet {

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
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet CrearUsuario</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet CrearUsuario at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
        request.getRequestDispatcher("/vistas/CrearUsuario.jsp").forward(request, response);
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Se creara el usuario");
        
        /*--Traer Datos--*/
        String nombreUsuario = request.getParameter("nombreUsuario");
        String nombreCompleto = request.getParameter("nombreCompleto");
        String contrasenia = request.getParameter("contrasenia");
        String tipoUsuario = request.getParameter("tipoUsuario");
        
        System.out.println("NU: "+nombreUsuario);
        System.out.println("NC: "+nombreCompleto);
        System.out.println("CS: "+contrasenia);
        System.out.println("TU: "+tipoUsuario);
        
        int tipo = 0;                        
        if (tipoUsuario.equalsIgnoreCase("Cliente")) {
            tipo = 1;
        }
        
        /*--Realizar Conexion--*/
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();
        
        ControlDBUsuario controlDbUsuarios = new ControlDBUsuario(connection);
        controlDbUsuarios.insertarUsuario(nombreUsuario, nombreCompleto, contrasenia, tipo);
        
        processRequest(request, response);
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
