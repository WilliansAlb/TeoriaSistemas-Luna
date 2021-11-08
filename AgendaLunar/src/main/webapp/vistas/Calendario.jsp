<%-- 
    Document   : Calendario
    Created on : 15/10/2021, 01:10:27 AM
    Author     : willi
--%>

<%@page import="POJOS.Siembra"%>
<%@page import="java.util.ArrayList"%>
<%@page import="POJOS.Cultivo"%>
<%@page import="POJOS.Lugar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="Conexion.ControlDBEventos"%>
<%@page import="Conexion.ConnectionDB"%>
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
            ConnectionDB cn = new ConnectionDB();
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("/AgendaLunar/vistas/Login.jsp");
            } else {
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
            <div id="encabezado-controles">
                <div><h1 style="text-align: center;margin:0;">Calendario</h1></div>
                <div class="selector">
                    <div class="selector-mes"><span class="control" onclick="cambiaMes(true)"><img src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span><span id="mes">OCTUBRE</span><span class="control" onclick="cambiaMes(false)"><img class="alreves" src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span></div>
                    <div class="selector-anio"><span class="control" onclick="cambiaAnio(true)"><img src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span><span id="anio">2021</span><span class="control" onclick="cambiaAnio(false)"><img class="alreves" src="https://cdn-icons-png.flaticon.com/512/130/130906.png" width="20px" style="margin: auto;">
                        </span></div>
                </div>
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
        <div id="cargando">
            <div class="loader"></div>
        </div>
        <div class="oculto" id="oculto1" style="display:none;">
            <div style="background-color: #303030; padding: 2em;border-radius: 2em; width:70%;" class="colorFondo">
                <span id="listado_dia_evento"></span>
                <h1>LISTADO DE EVENTOS</h1>
                <div class="table-responsive">
                    <table class="table table-bordered" >
                        <thead>
                            <tr class="bg-light">
                                <th scope="col">
                                    Nombre
                                </th>
                                <th scope="col">
                                    Descripcion
                                </th>
                                <th scope="col">
                                    Tipo
                                </th>
                            </tr>
                        </thead>
                        <tbody id="tbody_eventos">
                            <tr>
                                <td>
                                    Siembra ejemplo
                                </td>
                                <td>
                                    Descripcion ejemplo
                                </td>
                                <td>
                                    tipo
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <button onclick="this.parentNode.parentNode.style.display = 'none';" style="background-color: red;width: fit-content;height: fit-content;padding: 1em">CERRAR</button>
            </div>
        </div>
        <div class="oculto" id="oculto2">
            <div id="creacion">
                <div class="creacion">
                    <h1>CREAR EVENTO</h1>
                    <div class="switch-button">
                        <input class="switch-button-checkbox" id="evento_tipo" type="checkbox" onclick="toggle_button(this)"></input>
                        <label class="switch-button-label" for=""><span class="switch-button-label-span">Cualquiera</span></label>
                    </div>
                    <h1>EN LA FECHA <span id="dia_crear">2021-10-21</span></h1>
                </div>
                <hr width="100%">
                <div id="creacion_siembra" class="creacion2" style="display:none; flex-wrap: wrap;">
                    <div id="selector-siembra" class="creacion2" style="width: 80%;">
                        <span>¿A qué siembra pertenece?</span>
                        <select name="siembras" id="siembras" onchange="cambiando_select(this,'siembra_nueva','seleccion_siembra')">
                            <option value="-1">NUEVA SIEMBRA</option>
                            <%
                                ControlDBEventos ctr = new ControlDBEventos(cn.getConnection());
                                List<Siembra> siembras = ctr.getTodasLasSiembrasPorUsuario("Admin");
                                for (int i = 0; i < siembras.size(); i++) {     
                            %>
                            <option value="<%out.print(siembras.get(i).getIdSiembra());%>"><%out.print(siembras.get(i).getNombre()+" - "+siembras.get(i).getFechaSiembra());%></option>
                            <%}%>
                        </select>
                    </div>
                    <div id="siembra_nueva" class="creacion2" style="width: 80%;">
                        <h1 style="font-size: 1.5em; margin: 0;">Datos de siembra nueva</h1>
                        <label for="lugares">Lugar:</label>
                        <select name="lugares" id="lugares">
                            <%                                
                                List<Lugar> lugares = ctr.getTodosLugares(session.getAttribute("usuario").toString());
                                for (int i = 0; i < lugares.size(); i++) {
                            %>
                            <option value="<%out.print(lugares.get(i).getIdLugar());%>"><%out.print(lugares.get(i).getNombre() + "-" + lugares.get(i).getUbicacion());%></option>
                            <%
                                }
                            %>
                        </select>
                        <label for="cultivos">Cultivo:</label>
                        <select name="cultivos" id="cultivos">
                            <%
                                List<Cultivo> cultivos = ctr.getTodosLosTiposDeCultivos();
                                for (int i = 0; i < cultivos.size(); i++) {
                            %>
                            <option value="<%out.print(cultivos.get(i).getIdCultivo());%>"><%out.print(cultivos.get(i).getTipo());%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div id="seleccion_siembra" class="creacion2" style="display: none; width: 80%;">
                        <label for="tipo_evento_siembra">¿Qué se le hará a la siembra?</label>
                        <select name="evt_siembra" id="evt_siembra" onchange="cambiando_select(this,'otro_tipo','')">
                            <option value="fertilizacion">fertilizar</option>
                            <option value="riego">regar</option>
                            <option value="cosecha">cosechar</option>
                            <option value="raleo">ralear</option>
                            <option value="transplante">transplantar</option>
                            <option value="-1">otro</option>
                        </select>
                        <div id="otro_tipo" style="display: none; flex-direction: column;">
                            <label for="hacer_siembra">Escribe acá lo que se le hará a la siembra</label>
                            <input type="text" id="hacer_siembra" name="hacer_siembra">
                        </div>
                    </div>
                </div>
                <div id="creacion_evento" class="creacion2" >
                    <h1 style="font-size: 1.5em; margin: 0;">Datos de evento</h1>
                    <span>Nombre:</span>
                    <input type="text" id="nombre_evento">
                    <span>Descripción:</span>
                    <textarea id="descripcion_evento"></textarea>
                    <div id="siembra_id" style="display: none;">
                        <span>¿A que siembra pertenece?</span>
                        <select id="siembra_evento">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                        </select>
                    </div>
                    <div id="evento_id" style="width:100%; flex-direction: column; display:flex;">
                        <span>¿Que tipo de evento es?</span>
                        <input type="text" id="tipo_evento">
                    </div>
                    <button onclick="crear_evento()" style="margin-top:2em;">CREAR EVENTO</button>
                </div>
                <button onclick="this.parentNode.parentNode.style.display = 'none';" style="background-color:red;width:fit-content;margin:auto;padding: 1em;">X</button>
            </div>
        </div>
        <div class="oculto" id="oculto3" style="display: none;">
            <div id="creacion">
                <img src="../assets/img/o6.png" width="50px" id="img_tipo">
                <h1 id="tipo_mensaje">ERROR</h1>
                <span id="mensaje"></span>
                <button onclick="window.location.href = 'http://localhost:8080/AgendaLunar/vistas/Calendario.jsp';" style="background-color:green;width:fit-content;margin:auto;">OK</button>
            </div>
        </div>
        <script src="../assets/js/luna.js"></script>
        <script src="../assets/js/calendario.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <%}%>
    </body>
</html>
