/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Conexion.ConnectionDB;
import Conexion.ControlDBEventos;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willi
 */
public class Estadisticas extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Estadisticas</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Estadisticas at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        ConnectionDB cn = new ConnectionDB();
        ControlDBEventos cdb = new ControlDBEventos(cn.getConnection());
        if (request.getParameter("porcentajes")!=null){
            response.setContentType("application/json");
            Gson gn = new Gson(); 
            ArrayList<String> ho = cdb.porcentajes(request.getSession().getAttribute("usuario").toString());
            if (ho.size()>0){
                String json = gn.toJson(ho);
                response.getWriter().write(json);
            } else {
                response.getWriter().write("{\"ERROR\":\"true\"}");
            }
        } else if (request.getParameter("tendencias")!=null){
            response.setContentType("application/json");
            Gson gn = new Gson(); 
            ArrayList<String> ho = cdb.tendencias(request.getSession().getAttribute("usuario").toString());
            if (ho.size()>0){
                String json = gn.toJson(ho);
                response.getWriter().write(json);
            } else {
                response.getWriter().write("{\"ERROR\":\"true\"}");
            }
        } else if (request.getParameter("eventos")!=null){
            response.setContentType("application/json");
            Gson gn = new Gson(); 
            ArrayList<String> ho = cdb.eventos_por_mes(request.getSession().getAttribute("usuario").toString());
            if (ho.size()>0){
                String json = gn.toJson(ho);
                response.getWriter().write(json);
            } else {
                response.getWriter().write("{\"ERROR\":\"true\"}");
            }
        }
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
