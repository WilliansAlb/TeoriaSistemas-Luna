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
        <title>Cultivos</title>
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
                        <li class="nav-item nav-link"><a class="nav-link" href="Administracion" >Principal</a></li>
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
            <div id="encabezado-siembras" style="background-image:url(../assets/img/cultivos.jpg)">
                <div style="padding:2em;"><h1 style="text-align: center;margin:0;">Cultivos</h1></div>
            </div>
            <div style="padding: 1em; background-color: white; color: black;">
                <h1 style="margin: 0; padding: 0;">Agrega un cultivo nuevo</h1>
                <div class="conte">
                    <div>
                        <label for="cultivo">Cultivo nuevo:</label>
                        <input type="text" id="cultivo" name="cultivo">
                    </div>
                    <input class="btn btn-info" value="Agregar" type="button" onclick="agregarCultivo()">
                </div>
            </div>
            <div class="table-responsive" style="height:340px; overflow: auto;">
                <table class="table table-bordered" style="text-align:center;" >
                    <thead>
                        <tr class="bg-light">
                            <th scope="col">Tipo</th>
                        </tr>
                    </thead>
                    <tbody id="tbody_siembras" style="color:white;">
                        <%
                                List<Cultivo> cultivos = cdb.getTodosLosTiposDeCultivos();
                                if (cultivos.size()>0){
                                for (int i = 0; i < cultivos.size(); i++) {
                            %>
                            <tr>
                                <td><%out.print(cultivos.get(i).getTipo());%></td>
                            </tr>
                            <%
                                }} else{
                            %>
                            <tr>
                                <td>Aún no hay creados cultivos</td>
                            </tr>
                            <%
                                }
                            %>
                    </tbody>
                </table>
            </div>
        </div>
                    
        <div id="cargando" style="display:none;">
            <div class="loader"></div>
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
        <script src="../assets/js/cultivos.js"></script>
        <%}%>
    </body>
</html>
