<%@page import="java.sql.Connection"%>
<%@page import="Conexion.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="POJOS.*"%>
<%@page import="java.util.List"%>
<%@page import="servlet.*"%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>agenda_lunar</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cabin:700">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/simple-line-icons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Account-setting-or-edit-profile.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Blog-Detail-App.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gradient-navbar-1.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gradient-navbar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/tag.css">
        <script src="https://code.jquery.com/jquery-3.5.0.js"></script>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Profile-with-data-and-skills.css">
    </head>

    <body background="${pageContext.request.contextPath}/assets/img/star-sky2.png" >
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("usuario") == null) {
                //response.sendRedirect("/AgendaLunar/vistas/Login.jsp");
            }
            String ID = (String) request.getSession().getAttribute("usuario");
        %>

        <div id="main-content" class="blog-page">
            <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
                <div class="container"><a class="navbar-brand" href="../index.html">Moon</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
                    <div class="collapse navbar-collapse" id="navbarResponsive">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item nav-link"><a class="nav-link" href="Calendario.jsp">Calendario</a></li>
                            <li class="nav-item nav-link"><a class="nav-link" href="Muro.jsp">Mi Blog personal</a></li>
                            <li class="nav-item nav-link"><a class="nav-link" href="Cuenta.jsp">Cuenta</a></li>
                            <li class="nav-item nav-link"><a class="nav-link" href="Login.jsp">Cerrar Session</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <br>
            <br>
            <br>
            <br>
            <div class="container">
                <div class="row clearfix">
                    <div class="col-lg-8 col-md-12 left-box">

                        <div class="card" align="center" id="publicacion" name="publicacion" >

                            <input type="text" class="form-control" id="usuario" name="usuario"  style="display: none;">
                            
                            <textarea id="textop" rows="5" placeholder="En que estas pensando..." ></textarea>
                            
                            <div class="container">

                                <div class="tag-container">
                                    <input  />  
                                </div>

                            </div>     

                            <button type="button" onclick="publicar();" class="btn btn-success" >Publicar</button>

                        </div>


                        <%
                            try {
                                List<Publicacion> publicaciones = new ArrayList<>();

                                //establece la conexion a la DB
                                ConnectionDB connect = new ConnectionDB();
                                Connection connection = connect.getConnection();

                                ControlDBPublicacion ControlPP = new ControlDBPublicacion(connection);
                                //obtiene las publicaciones 
                                publicaciones = ControlPP.getTodasPublicacionesPorNombreUsuario(ID);
                                if (publicaciones.size() > 0) {
                                    for (Publicacion my_post : publicaciones) {
                        %>
                        <div class="card single_post">
                            <div class="body">
                                <!--<h3><a href="">Posible titulo</a></h3>-->
                                <p> <%=my_post.getContenido()%> </p>
                            </div>     
                            <%
                                ///ETIQUETAS
                                ControlDBPublicacion ControlP = new ControlDBPublicacion(connection);
                                List<String> etiquetas = new ArrayList<>();
                                try {

                                    etiquetas = ControlP.getTodasEtiquetasPorIdPublicacion(my_post.getIdPublicacion());
                                } catch (Exception e) {

                                }
                                if (!etiquetas.isEmpty()) {
                            %>

                            <div class="body widget">
                                <ul class="list-unstyled categories-clouds m-b-0">
                                    <li>
                                        <% for (int i = etiquetas.size() - 1; i >= 0; i--) {
                                                try {%>
                                        <a href="javascript:void(0);">#<%=etiquetas.get(i)%></a>
                                        <%} catch (Exception e) {
                                                }
                                            }%>
                                    </li>
                                </ul>
                            </div>
                            <%
                                }%>
                            <button type="button" onclick="mostrarComentarios(<%=my_post.getIdPublicacion()%>)" id="btn_mostar<%=my_post.getIdPublicacion()%>" class="btn btn-dark">Mostar comentarios</button>
                            <button type="button" onclick="ocultarComentarios(<%=my_post.getIdPublicacion()%>)" id="btn_ocultar<%=my_post.getIdPublicacion()%>" class="btn btn-dark" style="display: none;" >Ocultar Comentarios</button>

                            <div class="card" id="mostrar_comentarios<%=my_post.getIdPublicacion()%>" name="mostrar_comentarios<%=my_post.getIdPublicacion()%>" style="display: none;" >

                                <%
                                    List<Comentario> comentarios = new ArrayList<>();
                                    try {
                                        comentarios = ControlP.getTodosComentariosPorIdPublicacion(my_post.getIdPublicacion());
                                    } catch (Exception e) {

                                    }
                                %>
                                <div class="header">
                                    <h4>Comentarios <%=comentarios.size()%></h4>
                                </div>
                                <div class="body">

                                    <ul class="comment-reply list-unstyled">
                                        <div id="comentarios_publicacion<%=my_post.getIdPublicacion()%>" >
                                            <%
                                                if (!comentarios.isEmpty()) {
                                                    for (int i = 0; i < comentarios.size(); i++) {%>
                                            <li class="row clearfix">
                                                <div class="icon-box col-md-2 col-4">
                                                    <img class="img-fluid img-thumbnail" src="https://cdn.pixabay.com/photo/2021/06/07/13/46/user-6318008_1280.png" alt="Perfil"></div>
                                                <div class="text-box col-md-10 col-8 p-l-0 p-r0">


                                                    <h5 class="m-b-0"><%=comentarios.get(i).getNombreUsuario()%></h5>
                                                    <p><%=comentarios.get(i).getContenido()%></p>
                                                    <ul class="list-inline">
                                                        <li><a href="javascript:void(0);"><%=comentarios.get(i).getFecha()%></a></li>
                                                    </ul>
                                                </div>
                                            </li>
                                            <%}
                                                }%>
                                        </div>
                                    </ul>


                                    <div class="card">
                                        <div class="body">
                                            <div class="comment-form">
                                                <form class="row clearfix">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <textarea rows="4" id="co<%=my_post.getIdPublicacion()%>" class="form-control no-resize" placeholder="Escribe Aqui..."></textarea>
                                                        </div>
                                                        <button type="button" onclick="comentar(this,<%=my_post.getIdPublicacion()%>)" value="<%=my_post.getIdPublicacion()%>" class="btn btn-block btn-primary">Comentar</button>
                                                    </div>                                
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>                         
                        <%  }
                                }
                            } catch (Exception e) {
                            } //termina for de publicacion %>

                    </div>
                    <div class="col-lg-4 col-md-12 right-box">
                        <div class="card">
                            <div class="body search">
                                <form action="../BuscarPublicacion" method="GET" >
                                    <div class="input-group m-b-0">
                                        <input required="Ingresa un campo valido" id="busqueda" name="busqueda" type="text" class="form-control" placeholder="Buscar Publicacion...">  
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><button  class="fa fa-search"></button></span>
                                        </div>
                                    </div>
                                    
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script>

//                $('#texto').blur(function () {
//                    // replace hashtags (parsing should prob. be enhanced)
//                    $(this).html($(this).html().replace(/(#\S+)/, '<span style="color: blue">$1</span>'));
//                });


                function comentar(boton, texto) {
                    var james = boton.parentNode;
                    var id_publicacion = boton.value;
                    var com = document.getElementById('co' + texto)
                    var comm = com.value
                    var data = {id: id_publicacion, comentario: comm};
                    console.log(id_publicacion);

                    $.ajax({
                        type: "POST",
                        url: "../Comentarios",
                        data: data,
                        beforeSend: function () {
                            console.log(' antes enviar');
                        },
                        complete: function (data) {

                        },
                        success: function (data) {

                            console.log('#comentarios_publicacion' + id_publicacion);

                            const split = data.split(',');
                            var nombre = split[0];
                            var fecha = split[1];


                            $('#comentarios_publicacion' + id_publicacion).append('<li class="row clearfix"><div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://cdn.pixabay.com/photo/2021/06/07/13/46/user-6318008_1280.png" alt="Perfil"></div><div class="text-box col-md-10 col-8 p-l-0 p-r0"><h5 class="m-b-0">' + nombre + '</h5><p>' + comm + '</p><ul class="list-inline"><li><a href="javascript:void(0);">' + fecha + '</a></li></ul></div></li>');
                            com.value = '';
                        },
                        error: function (data) {
                            alert("Error al realizar un comentario intentalo mas tarde");
                        }
                    });
                }

                function mostrarComentarios(id) {
                    var coment = document.getElementById('mostrar_comentarios' + id);
                    var btn_mostar = document.getElementById('btn_mostar' + id);
                    var btn_ocultar = document.getElementById('btn_ocultar' + id);
                    coment.style.display = 'inline';
                    btn_mostar.style.display = 'none';
                    btn_ocultar.style.display = 'inline';

                }

                function ocultarComentarios(id) {
                    var coment = document.getElementById('mostrar_comentarios' + id);
                    var btn_mostar = document.getElementById('btn_mostar' + id);
                    var btn_ocultar = document.getElementById('btn_ocultar' + id);
                    coment.style.display = 'none';
                    btn_mostar.style.display = 'inline';
                    btn_ocultar.style.display = 'none';

                }

            </script>
        </div>


        <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/grayscale.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/tag.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/Publicar.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </body>

</html>