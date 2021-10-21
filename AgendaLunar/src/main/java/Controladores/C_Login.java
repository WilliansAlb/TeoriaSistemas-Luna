/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Conexion.ConnectionDB;
import Conexion.ControlDBUsuario;
import POJOS.Usuario;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sergi
 */
@WebServlet(name = "C_Login", urlPatterns = {"/vistas/C_Login"})
public class C_Login extends HttpServlet {

    private ConnectionDB connectionDB = new ConnectionDB();
    private Connection connection =  connectionDB.getConnection();
    private ControlDBUsuario controlDBUsuario =  new ControlDBUsuario(connection);
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
       try{
           
           String nombreUsuario = request.getParameter("usuario");
           String contrasenia = request.getParameter("password");
           Usuario usuario = controlDBUsuario.getUsuarioPorNombreUsuarioYPassword(nombreUsuario, contrasenia);//obtener un usuario
           if (usuario != null) {
               request.getSession().setAttribute("usuario", usuario.getNombreUsuario());
               request.getSession().setAttribute("password", usuario.getPassword());
               request.getSession().setAttribute("tipo", usuario.getTipo());
               if (usuario.getTipo() == 0) {
                   response.sendRedirect("/AgendaLunar/vistas/Administracion.jsp");
               }else{
                   response.sendRedirect("/AgendaLunar");
               }               
           }else{
               requestError(request,response);
           }
       }catch(Exception e){
           requestError(request,response);
       }
    }
    
    private void requestError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("success", 0);//atributo que sirve para verficar si ingreso las credenciales correctamente
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

}
