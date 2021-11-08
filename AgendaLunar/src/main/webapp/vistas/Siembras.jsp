<%-- 
    Document   : Siembras
    Created on : 1/11/2021, 11:07:46 PM
    Author     : willi
--%>

<%@page import="POJOS.Lugar"%>
<%@page import="POJOS.Cultivo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Conexion.ControlDBEventos"%>
<%@page import="Conexion.ConnectionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Siembras</title>
        <link rel="shortcut icon" href="../assets/img/icono1.png" type="image/x-icon">
        <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cabin:700">
        <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
        <link rel="stylesheet" href="../assets/fonts/simple-line-icons.min.css">
        <link rel="stylesheet" href="../assets/css/Account-setting-or-edit-profile.css">
        <link rel="stylesheet" href="../assets/css/Blog-Detail-App.css">
        <link rel="stylesheet" href="../assets/css/gradient-navbar-1.css">
        <link rel="stylesheet" href="../assets/css/gradient-navbar.css">
        <link rel="stylesheet" href="../assets/css/Login-Form-Dark.css">
        <link rel="stylesheet" href="../assets/css/Profile-with-data-and-skills.css">
        <link rel="stylesheet" href="../assets/css/calendario.css">
        <link rel="stylesheet" href="../assets/css/siembras.css">
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            ConnectionDB cn = new ConnectionDB();
            ControlDBEventos cdb = new ControlDBEventos(cn.getConnection());
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("Login.jsp");
            } else {
        %>
        <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
            <div class="container"><a class="navbar-brand" href="../index.jsp"><img src="../assets/img/icono1.png" width="40px">Agenda Lunar</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto">                        
                        <li class="nav-item nav-link"><a class="nav-link" href="Administracion.jsp" >Principal</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Calendario.jsp">Calendario</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Muro.jsp">Muro Privado</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="MuroPublico.jsp">Muro Publico</a></li>                        
                        <li class="nav-item nav-link"><a class="nav-link" href="Login.jsp">Cerrar Sesion</a></li>               
                    </ul>
                </div>
            </div>
        </nav>
        <div id="fondo"></div>
        <div id="cargando" style="display: none;">
            <div class="loader"></div>
        </div>
        <div class="contenedor">
            <div id="encabezado-siembras">
                <div style="padding:2em;"><h1 style="text-align: center;margin:0;">Siembras</h1></div>
            </div>
            <div style="padding: 1em; background-color: white; color: black;">
                <h1 style="margin: 0; padding: 0;">Crea nueva siembra</h1>
                <div class="conte">
                    <div>
                        <label for="lugares">Lugar de la siembra:</label>
                        <select name="lugares" id="lugares">
                            <%                                
                                List<Lugar> lugares = cdb.getTodosLugares(session.getAttribute("usuario").toString());
                                if (lugares.size()>0){
                                for (int i = 0; i < lugares.size(); i++) {
                            %>
                            <option value="<%out.print(lugares.get(i).getIdLugar());%>"><%out.print(lugares.get(i).getNombre() + "-" + lugares.get(i).getUbicacion());%></option>
                            <%
                                }} else{
                            %>
                            <option value="-1">Crea un lugar antes</option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div>
                        <label for="cultivos">Cultivo sembrado:</label>
                        <select name="cultivos" id="cultivos">
                            <%
                                List<Cultivo> cultivos = cdb.getTodosLosTiposDeCultivos();
                                if (cultivos.size()>0){
                                for (int i = 0; i < cultivos.size(); i++) {
                            %>
                            <option value="<%out.print(cultivos.get(i).getIdCultivo());%>"><%out.print(cultivos.get(i).getTipo());%></option>
                            <%
                                }} else{
                            %>
                            <option value="-1">Crea un cultivo antes</option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div>
                        <label for="fecha">Fecha en que se sembró:</label>
                        <input type="date" id="fecha" name="fecha">
                    </div>
                    <div>
                        <label for="nombre">Nombre que identificará a la siembra:</label>
                        <input type="text" id="nombre" name="nombre">
                    </div>
                    <input class="btn btn-info" value="Crear" type="button" onclick="crear_evento()">
                </div>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered" style="text-align:center;" >
                    <thead>
                        <tr class="bg-light">
                            <th scope="col">Lugar</th>
                            <th scope="col">Cultivo</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">Cosechado</th>
                        </tr>
                    </thead>
                    <tbody id="tbody_siembras" style="color:white;">
                        <%                        
                            ArrayList<List<String>> siembra_datos = cdb.datos_siembras(session.getAttribute("usuario").toString());
                            if (siembra_datos.size() > 0) {
                                for (int i = 0; i < siembra_datos.size(); i++) {
                                    List<String> datos = siembra_datos.get(i);
                        %>
                        <tr id="t<%out.print(datos.get(0));%>">
                            <td><%out.print(datos.get(1));%></td>
                            <td><%out.print(datos.get(2));%></td>
                            <td><%out.print(datos.get(3));%></td>
                            <td><%out.print(datos.get(4));%></td>
                            <td><%
                                if (datos.get(5).equalsIgnoreCase("1")) {
                                %>
                                <img src="../assets/img/o7.png" width="20px">
                                <%} else {%>
                                <input type="checkbox" style="width:20px; height: 20px;" onchange="crear_evento_id_siembra(<%out.print(datos.get(0));%>,this)">
                                <%}%>
                            </td>
                        </tr>
                        <%}
                        } else {%>
                        <tr>
                            <td>No</td>
                            <td>has</td>
                            <td>creado</td>
                            <td>ninguna</td>
                            <td>siembra</td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="oculto" id="oculto3" style="display: none;">
            <div id="creacion">
                <img src="../assets/img/o6.png" width="50px" id="img_tipo">
                <h1 id="tipo_mensaje">ERROR</h1>
                <span id="mensaje"></span>
                <input style="background-color:green;width:fit-content;margin:auto;" class="btn btn-info" value="Cerrar" type="button" onclick="document.getElementById('oculto3').style.display = 'none';">
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../assets/js/siembras.js"></script>
        <%}%>
    </body>
</html>
