<%-- 
    Document   : Estadisticas
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
        <title>Estadisticas</title>
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
            <div id="encabezado-siembras" style="background-image:url(../assets/img/estadisticas.jpg)">
                <div style="padding:2em;"><h1 style="text-align: center;margin:0; color:#303030;">Estadisticas</h1></div>
            </div>
            <div style="padding: 1em; background-color: white; color: black;">
                <h1 style="margin: 0; padding: 0;">Reporte</h1>
                <label for="tipo_reporte">¿Qué reporte deseas ver?</label>
                <select id="tipo_reporte" name="tipo_reporte" onchange="cambiando(this)">
                    <option value="0">Porcentajes de eventos</option>
                    <option value="1">Tendencias a sembrar</option>
                    <option value="2">Eventos por mes</option>
                </select>
            </div>
            <div id="chartContainer" style="height: 370px; width: 100%;"></div>
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
        <script type="text/javascript">
            window.onload = function () {
                porcentajes();
            };
            
            function cambiando(select){
                if (select.value==0){
                    porcentajes();
                } else if (select.value==1){
                    tendencias_sembrar();
                } else {
                    eventos_por_mes();
                }
            }

            function porcentajes() {
                fetch("../Estadisticas?porcentajes=1", {
                    method: "GET"
                }).then(response => response.json()).then(data => {
                    var options = {
                        title: {
                            text: "Porcentaje de eventos"
                        },
                        data: [{
                                type: "pie",
                                startAngle: 45,
                                showInLegend: "true",
                                legendText: "{label}",
                                indexLabel: "{label} ({y})",
                                yValueFormatString: "#,##0.#" % "",
                                dataPoints: [
                                    {label: "Eventos siembra", y: parseInt(data[1].replace("%", ""))},
                                    {label: "Eventos no siembra", y: parseInt(data[2].replace("%", ""))}
                                ]
                            }]
                    };
                    $("#chartContainer").CanvasJSChart(options);
                });
            }

            function tendencias_sembrar() {
                fetch("../Estadisticas?tendencias=1", {
                    method: "GET"
                }).then(response => response.json()).then(data => {
                    var datos = [];
                    for (var i = 0; i < data.length; i=i+2) {
                        var tem = parseInt(data[i+1]);
                        datos.push({y:tem,label:data[i]});
                    }
                    var chart = new CanvasJS.Chart("chartContainer", {
                        animationEnabled: true,
                        theme: "light2", // "light1", "light2", "dark1", "dark2"
                        title: {
                            text: "Tendencias de cultivos a sembrar"
                        },
                        axisY: {
                            title: "Cantidad de siembras de dicho cultivo"
                        },
                        data: [{
                                type: "column",
                                showInLegend: true,
                                legendMarkerColor: "grey",
                                legendText: "Cultivos",
                                dataPoints: datos
                            }]
                    });
                    chart.render();
                });
            }
            
            function eventos_por_mes() {
                fetch("../Estadisticas?eventos=1", {
                    method: "GET"
                }).then(response => response.json()).then(data => {
                    var datos = [];
                    for (var i = 0; i < data.length; i=i+2) {
                        var tem = parseInt(data[i+1]);
                        datos.push({y:tem,label:data[i]});
                    }
                    var chart = new CanvasJS.Chart("chartContainer", {
                        animationEnabled: true,
                        theme: "light2", // "light1", "light2", "dark1", "dark2"
                        title: {
                            text: "Eventos por mes"
                        },
                        axisY: {
                            title: "Cantidad de eventos"
                        },
                        data: [{
                                type: "column",
                                showInLegend: true,
                                legendMarkerColor: "grey",
                                legendText: "Meses",
                                dataPoints: datos
                            }]
                    });
                    chart.render();
                });
            }
        </script>
        <script type="text/javascript" src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>  
        <script type="text/javascript" src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
        <%}%>
    </body>
</html>

