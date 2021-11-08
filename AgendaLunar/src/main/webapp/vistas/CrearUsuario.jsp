<%-- 
    Document   : CrearUsuario
    Created on : 1/11/2021, 15:18:53
    Author     : user-ubunto
--%>

<%@page import="Conexion.ConnectionDB"%>
<%@page import="Conexion.ControlDBUsuario"%>
<%@page import="POJOS.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuarios</title>
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
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("Login.jsp");
            } else {
                Integer tipo = (Integer) session.getAttribute("tipo");
                if (tipo != 0) {
                    response.sendRedirect("/AgendaLunar");
                }
        %>
        <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
            <div class="container"><a class="navbar-brand" href="../index.jsp"><img src="../assets/img/icono1.png" width="40px">Agenda Lunar</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto">                        
                        <li class="nav-item nav-link"><a class="nav-link" href="#" >Principal</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Calendario.jsp">Calendario</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Muro.jsp">Muro Privado</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="MuroPublico.jsp">Muro Publico</a></li>                        
                        <li class="nav-item nav-link"><a class="nav-link" href="Login.jsp">Cerrar Sesion</a></li>               
                    </ul>
                </div>
            </div>
        </nav>  
        <div class="contenedor">
            <div id="encabezado-siembras" style="background-image: url(../assets/img/usuarios.jpg);">
                <div style="padding:2em;"><h1 style="text-align: center;margin:0;">Usuarios</h1></div>
            </div>
            <div style="padding: 1em; background-color: white; color: black;">
                <h1 style="margin: 0; padding: 0;">Crear nuevo usuario</h1>
                <div class="conte">
                    <label for="nombreUsuario">Nombre del usuario:</label>
                    <input type="text" id="nombreUsuario" name="nombreUsuario">
                    <label for="nombreCompleto">Nombre completo del usuario:</label>
                    <input type="text" id="nombreCompleto" name="nombreCompleto">  
                    <label for="contrasenia">Contraseña:</label>
                    <input type="text" id="contrasenia" name="contrasenia">
                    <label for="tipoUsuario">Tipo:</label>
                    <select name="tipoUsuario" id="tipoUsuario">
                        <option value="Cliente">Cliente</option>
                        <option value="Administrador">Administrador</option>                            
                    </select>
                    <input class="btn btn-info" value="Crear" type="button" onclick="crearUsuario()">
                </div>
            </div>
            <div class="table-responsive" style=" height: 340px; overflow: auto;">
                <table class="table table-bordered" style="text-align:center;" >
                    <thead>
                        <tr class="bg-light">
                            <th scope="col">Usuario</th>
                            <th scope="col">Nombre completo</th>
                            <th scope="col">Tipo</th>
                            <th scope="col">Eliminar</th>
                        </tr>
                    </thead>
                    <tbody id="tbody_siembras" style="color:white;">
                        <%
                            ControlDBUsuario ctr = new ControlDBUsuario(cn.getConnection());
                            List<Usuario> siembra_datos = ctr.getTodosUsuarios();
                            if (siembra_datos.size() > 0) {
                                for (int i = 0; i < siembra_datos.size(); i++) {
                                    Usuario datos = siembra_datos.get(i);
                        %>
                        <tr id="<%out.print(datos.getNombreUsuario());%>">
                            <td><%out.print(datos.getNombreUsuario());%></td>
                            <td><%out.print(datos.getNombreCompleto());%></td>
                            <td><%out.print(datos.getTipo());%></td>
                            <td><input class="btn btn-info" value="Eliminar" type="button" style="background-color:red;" onclick="enviar('<%out.print(datos.getNombreUsuario());%>', this);"></td>
                        </tr>
                        <%}
                        } else {%>
                        <tr>
                            <td>No</td>
                            <td>hay</td>
                            <td>usuarios</td>
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
                <button onclick="document.getElementById('oculto3').style.display = 'none';" style="background-color:green;width:fit-content;margin:auto;">OK</button>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
                    function enviar(usuario, button) {
                        fetch("../EliminarUsuario?nombreUsuario=" + usuario, {
                            method: 'GET'
                        }).then(response => response.text()).then(data => {
                            if (data == "ELIMINADO") {
                                var aEliminar = button.parentNode.parentNode;
                                var padreEliminar = aEliminar.parentNode;
                                padreEliminar.removeChild(aEliminar);
                                document.getElementById("img_tipo").src = "../assets/img/o7.png";
                                document.getElementById("tipo_mensaje").textContent = "EXITO";
                                document.getElementById("mensaje").textContent = "Usuario eliminado";
                            } else {
                                document.getElementById("img_tipo").src = "../assets/img/o6.png";
                                document.getElementById("tipo_mensaje").textContent = "ERROR";
                                document.getElementById("mensaje").textContent = "No se puede eliminar el usuario dado que tiene referencias";
                            }
                            document.getElementById("oculto3").style.display = "flex";
                        });
                    }

                    function crearUsuario() {
                        var nombre = document.getElementById("nombreUsuario").value;
                        var completo = document.getElementById("nombreCompleto").value;
                        var contra = document.getElementById("contrasenia").value;
                        var tipo = document.getElementById("tipoUsuario").value;
                        if (nombre != "" && completo != "" && contra != "") {
                            var data = {nombreUsuario: nombre, nombreCompleto: completo, contrasenia: contra, tipoUsuario: tipo};
                            $.ajax({
                                type: "POST",
                                url: "../CrearUsuario",
                                data: data,
                                beforeSend: function () {
                                },
                                complete: function (data) {
                                    $("#oculto3").show();
                                },
                                success: function (data) {
                                    if (data.includes("ERROR")) {
                                        document.getElementById("img_tipo").src = "../assets/img/o6.png";
                                        $("#tipo_mensaje").text("ERROR");
                                        $("#mensaje").text("Ha ocurrido un error inesperado");
                                    } else {
                                        document.getElementById("nombreUsuario").value = "";
                                        document.getElementById("nombreCompleto").value = "";
                                        document.getElementById("contrasenia").value = "";
                                        document.getElementById("img_tipo").src = "../assets/img/o7.png";
                                        $("#tipo_mensaje").text("USUARIO CREADO EXITOSAMENTE");
                                        var tr = document.createElement("tr");
                                        tr.id = "t" + nombre;
                                        var td = document.createElement("td");
                                        td.textContent = completo;
                                        var td1 = document.createElement("td");
                                        var tip = (tipo == "Administrador") ? 0 : 1;
                                        td1.textContent = tip;
                                        var td2 = document.createElement("td");
                                        td2.textContent = nombre;
                                        var td3 = document.createElement("td");
                                        var check = document.createElement("input");
                                        check.classList.add("btn");
                                        check.classList.add("btn-info");
                                        check.type = "button";
                                        check.value = "Eliminar";
                                        check.style.backgroundColor = "red";
                                        var pa = "n" + nombre;
                                        check.id = pa;
                                        check.onclick = () => {
                                            enviar(nombre, document.getElementById(pa));
                                        };
                                        td3.appendChild(check);
                                        tr.append(td2, td, td1, td3);
                                        document.getElementById("tbody_siembras").appendChild(tr);
                                        $("#mensaje").text("Se ha creado exitosamente el usuario");
                                    }
                                },
                                error: function (data) {
                                    alert("Problemas al tratar de enviar el formulario");
                                }
                            });
                        } else {
                            $("#oculto3").show();
                            document.getElementById("img_tipo").src = "../assets/img/o6.png";
                            $("#tipo_mensaje").text("FALTAN");
                            $("#mensaje").text("Rellena todos los campos");
                        }
                    }
        </script>
        <%}%>
    </body>
</html>
