
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Conexion.ConnectionDB;
import Conexion.ControlDBPublicacion;
import Conexion.ControlDBUsuario;
import POJOS.Usuario;
import com.google.gson.Gson;
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
@WebServlet(name = "Comentarios", urlPatterns = {"/Comentarios"})
public class Comentarios extends HttpServlet {

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
        
        String ID= (String)request.getSession().getAttribute("usuario");
        String id_publicacion=request.getParameter("id");
        String comentario=request.getParameter("comentario");
        
        //establece la conexion a la DB
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();
        
        ControlDBPublicacion ControlP2 = new ControlDBPublicacion(connection);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        try {
            

            ControlP2.insertarComentario(ID, id_publicacion, comentario, timeStamp);

        } catch (Exception e) {

        }
        ConnectionDB connect2 = new ConnectionDB();
        Connection connection2 = connect2.getConnection();
        ControlDBUsuario user=new ControlDBUsuario(connection2);
        Usuario us;
        us=user.getUsuarioPorNombreUsuario(ID);
        //us.getNombreCompleto();
        
        Gson gs = new Gson();
        String json = gs.toJson(us.getNombreCompleto()+","+timeStamp);
        System.out.println(json+" el json en java");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);

        
//        //establece la conexion a la DB
//        ConnectionDB connect2 = new ConnectionDB();Connection connection2 = connect2.getConnection();ControlDBUsuario user=new ControlDBUsuario(connection2);Usuario us;us=user.getUsuarioPorNombreUsuario(comentario);us.getNombreCompleto();        
        System.out.println(ID+" USER");
        System.out.println(id_publicacion+" publicacion");
        System.out.println(comentario+" comentario");
        
        
        request.getSession().setAttribute("usuario", ID);

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}