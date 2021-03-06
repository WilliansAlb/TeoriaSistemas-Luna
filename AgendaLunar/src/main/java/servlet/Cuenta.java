/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Conexion.ConnectionDB;
import Conexion.ControlDBUsuario;
import POJOS.Usuario;
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
@WebServlet(name = "Cuenta", urlPatterns = {"/Cuenta"})
public class Cuenta extends HttpServlet {

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
            


        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ID = request.getParameter("usuario");
        System.out.println(ID);
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();
        ControlDBUsuario control=new ControlDBUsuario(connection);
        Usuario user=control.getUsuarioPorNombreUsuario(ID);
        
        request.setAttribute("DATOS_CUENTA", user);
        request.getSession().setAttribute("usuario", ID);
        request.getRequestDispatcher("vistas/Cuenta.jsp").forward(request, response);
        
        
        
        
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
