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
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ID= (String)request.getSession().getAttribute("usuario");
        String texto= request.getParameter("comentario");
        String etiquetas= request.getParameter("etiqueta");

        
        //establece la conexion a la DB
        ConnectionDB connect = new ConnectionDB();
        Connection connection = connect.getConnection();
        ControlDBPublicacion ControlP2 = new ControlDBPublicacion(connection);

        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            System.out.println("PUBLICARRRRRRRRRRRRRRR------------/-/-/-/-/-/");
            System.out.println(timeStamp);
            System.out.println(ID);
            System.out.println(texto);
            System.out.println(etiquetas);
            String[] parts = etiquetas.split(",");
            List<String> etiq = new ArrayList<>();
            
            for(int i =0;i<parts.length;i++){
                if(parts[i].equalsIgnoreCase("none")){
                }else{
                    etiq.add(parts[i]);
                }
                
            }
            
            
            ControlP2.insertarPublicacion(ID, texto, timeStamp, etiq);
        } catch (Exception e) {
            System.out.println("RROR AL PUBLICAR EN SERVLET "+e);
        }

        request.getSession().setAttribute("usuario", ID);
        

        //processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
