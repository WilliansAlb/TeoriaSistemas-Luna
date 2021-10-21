<%-- 
    Document   : Calendario
    Created on : 15/10/2021, 01:10:27 AM
    Author     : willi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calendario</title>
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
        <link rel="stylesheet" href="../assets/css/moon.css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="../assets/css/calendario.css">
    </head>
    <body>
        <%        
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("/AgendaLunar/vistas/Login.jsp");
            }  
        %>
        <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
            <div class="container"><a class="navbar-brand" href="../index.jsp"><img src="../assets/img/icono1.png" width="40px">Agenda Lunar</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item nav-link"><a class="nav-link active" href="/AgendaLunar">TS INFO</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Blog.jsp">BLOG</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Login.jsp">Login</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Cuenta.jsp">Cuenta</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="contenedor-calendario">
		<div><h1 style="text-align: center;margin:0;">Calendario</h1></div>
		<div class="selector">
                    <div class="selector-mes"><span class="control" onclick="cambiaMes(true)"><img src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span><span id="mes">OCTUBRE</span><span class="control" onclick="cambiaMes(false)"><img class="alreves" src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span></div>
                    <div class="selector-anio"><span class="control" onclick="cambiaAnio(true)"><img src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span><span id="anio">2021</span><span class="control" onclick="cambiaAnio(false)"><img class="alreves" src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span></div>
		</div>
		<div class="calendario" id="contenedor-calendario">
			<div class="nombre-dia">Domingo</div>
			<div class="nombre-dia">Lunes</div>
			<div class="nombre-dia">Martes</div>
			<div class="nombre-dia">Miercoles</div>
			<div class="nombre-dia">Jueves</div>
			<div class="nombre-dia">Viernes</div>
			<div class="nombre-dia">Sabado</div>
		</div>
	</div>
	<script src="../assets/js/luna.js"></script>
    </body>
</html>
