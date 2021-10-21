/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Conexion.ConnectionDB;
import Conexion.ControlDBEventos;
import POJOS.Evento;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willi
 */
@WebServlet(name = "Calendario", urlPatterns = {"/Calendario"})
public class Calendario extends HttpServlet {

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
            out.println("<title>Servlet Calendario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Calendario at " + request.getContextPath() + "</h1>");
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
        System.out.println(request.getParameter("mes"));
        int mes = Integer.parseInt(request.getParameter("mes"));
        int anio = Integer.parseInt(request.getParameter("anio"));
        ConnectionDB db = new ConnectionDB();
        ControlDBEventos ctdb = new ControlDBEventos(db.getConnection());
        List<Evento> eventos = new ArrayList<>();
        if (request.getParameter("dia")!=null){
            List<Evento> eventos_temp = ctdb.getTodosEventosPorIdUsuarioPorMes("Admin", mes, anio);
            int di = Integer.parseInt(request.getParameter("dia"));
            for (int i = 0; i < eventos_temp.size(); i++) {
                String[] s = eventos_temp.get(i).getFechaEvento().split("-");
                int a = Integer.parseInt(s[0]);
                int m = Integer.parseInt(s[1]);
                int d = Integer.parseInt(s[2]);
                if (d==di && m == mes && a == anio){
                    eventos.add(eventos_temp.get(i));
                }
            }
        } else {
            eventos = ctdb.getTodosEventosPorIdUsuarioPorMes("Admin", mes, anio);
        }
        Gson gs = new Gson();
        String json = gs.toJson(eventos);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
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
        ConnectionDB db = new ConnectionDB();
        ControlDBEventos ctdb = new ControlDBEventos(db.getConnection());
        if (request.getParameter("esSiembra").equalsIgnoreCase("true")){
            System.out.println("llega acá");
            if (ctdb.insertarEventoSiembra("Admin", request.getParameter("nombre"), request.getParameter("fecha"),
                    request.getParameter("descripcion"),"siembra",request.getParameter("lugar"),request.getParameter("cultivo"))){
                response.setContentType("text/plain");
                response.getWriter().write("CORRECTO");
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("ERROR");
            }
        } else {
            System.out.println(request.getParameter("fecha"));
            if (ctdb.insertarEventoNoSiembra("Admin", request.getParameter("nombre"), request.getParameter("fecha"),
                    request.getParameter("descripcion"),request.getParameter("tipo"))){
                response.setContentType("text/plain");
                response.getWriter().write("CORRECTO");
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("ERROR");
            }
        }
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
