<%-- 
    Document   : ListaUsuarios
    Created on : 2/11/2021, 14:38:02
    Author     : sergi
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de usuarios</title>
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
                    <!-- informacion viene de servlet/MostrarUsuarios -->
                    <div>
                        <table>
                            <thead>
                                <tr>
                                    <th>Nombre de usuario</th>
                                    <th>Nombre</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${usuarios}" var="usuario">
                                <tr>
                                    <td>${usuario.nombreUsuario}</td>
                                    <td> ${usuario.nombreCompleto}</td>
                                    <td>Editar</td>
                                    <td><a href="${pageContext.request.contextPath}/EliminarUsuario?nombreUsuario=${usuario.nombreUsuario}">Eliminar</a></td>
                                </tr>                              
                            </c:forEach>
                            </tbody>
                        </table> 
                    </div>
    </body>
</html>
