<%-- 
    Document   : CrearUsuario
    Created on : 1/11/2021, 15:18:53
    Author     : user-ubunto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Usuario</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/icono1.png" type="image/x-icon">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cabin:700">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/simple-line-icons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Account-setting-or-edit-profile.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Blog-Detail-App.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gradient-navbar-1.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gradient-navbar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Login-Form-Dark.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Profile-with-data-and-skills.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/CRUDUsuarios.css">
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("Login.jsp");
            } else {
                Integer tipo = (Integer) session.getAttribute("tipo");
                if (tipo != 0) {
                    response.sendRedirect("/AgendaLunar");
                }
            }
        %>

        <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
            <div class="container"><a class="navbar-brand" href="../index.jsp"><img src="${pageContext.request.contextPath}/assets/img/icono1.png" width="40px">Agenda Lunar</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item nav-link"><a class="nav-link active" href="/AgendaLunar">TS INFO</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="${pageContext.request.contextPath}/vistas/Blog.jsp">BLOG</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="${pageContext.request.contextPath}/vistas/Login.jsp">Login</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="${pageContext.request.contextPath}/vistas/Cuenta.jsp">Cuenta</a></li>
                    </ul>
                </div>
            </div>
        </nav>        
        <div id="contenedor">
            <div id="fondo">
                <h1>Crear Usuario</h1>                
                <div id="opciones">
                    <div>
                        <img src="${pageContext.request.contextPath}/assets/img/o3.png">
                        <h1>USUARIOS</h1>
                        <hr style="width:50%">
                        <form action="${pageContext.request.contextPath}/CrearUsuario" method="post">
                            <p>Nombre de Usuario</p>
                            <input type="text" id="nombreUsuario" name="nombreUsuario">
                            <p>Nombre Completo</p>
                            <input type="text" id="nombreCompleto" name="nombreCompleto">         
                            <p>Contraseña</p>
                            <input type="text" id="contrasenia" name="contrasenia">
                            <br>
                            <select name="tipoUsuario" id="tipoUsuario">
                                <option value="Cliente">Cliente</option>
                                <option value="Administrador">Administrador</option>                            
                            </select>
                            <br>
                            <input class="btn btn-info" value="Crear" type="submit">
                        </form>
                    </div>
                </div>                

            </div>
        </div>

    </body>
</html>
