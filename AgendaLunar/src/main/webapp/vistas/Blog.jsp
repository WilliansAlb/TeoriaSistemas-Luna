<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>agenda_lunar</title>
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
</head>

<body>
    <%        
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("/AgendaLunar/vistas/Login.jsp");
            }  
    %>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />

<div id="main-content" class="blog-page">
    <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
        <div class="container"><a class="navbar-brand" href="/AgendaLunar">Moon</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item nav-link"><a class="nav-link active" href="/AgendaLunar">TS INFO</a></li>
                    <li class="nav-item nav-link"><a class="nav-link" href="Login.jsp">Login</a></li>
                    <li class="nav-item nav-link"><a class="nav-link" href="Cuenta.jsp">Cuenta</a></li>
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
                    <div class="card single_post">
                        <div class="body">
                            <div class="img-post">
                                <img class="d-block img-fluid" src="https://i.ytimg.com/vi/FzXvbNv1uzw/maxresdefault.jpg" alt="First slide">
                            </div>
                            <h3><a href="blog-details.html">All photographs are accurate</a></h3>
                            <p>Fósforo: es muy importante para el desarrollo de las raíces y para la fecundación, el desarrollo y la maduración del grano. En las plantas deficientes en fósforo los pistilos emergen lentamente, lo que produce fecundaciones irregulares. Potasio: es esencial para la formación de la mazorca.</p>
                        </div>     
                        

                        
                        <div class="body widget">
                            <ul class="list-unstyled categories-clouds m-b-0">
                                <li><a href="javascript:void(0);">#Abono</a>
                                <a href="javascript:void(0);">#Siembras</a>
                                <a href="javascript:void(0);">#Plantas</a>
                                <a href="javascript:void(0);">#Fail</a>
                                <a href="javascript:void(0);">#Ganado</a>
                                <a href="javascript:void(0);">#Cosecha</a>
                                <a href="javascript:void(0);">#Agricultura</a></li>
                            </ul>
                        </div>

                        <button type="button" onclick="mostrarComentarios()" id="btn_mostar" class="btn btn-outline-secondary">Mostar comentarios</button>
                        <button type="button" onclick="ocultarComentarios()" id="btn_ocultar" class="btn btn-outline-secondary" style="display: none;" >Ocultar Comentarios</button>


                        <div class="card" id="mostrar_comentarios" name="mostrar_comentarios" style="display: none;" >
                            <div class="header">
                                <h4>Comments 3</h4>
                            </div>
                            <div class="body">
                                <ul class="comment-reply list-unstyled">
                                    <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://cdn.pixabay.com/photo/2021/06/07/13/46/user-6318008_1280.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">James Gramajo</h5>
                                            <p> La milpa no crece :(</p>
                                            <ul class="list-inline">
                                                <li><a href="javascript:void(0);">Mar 09 2018</a></li>
                                                
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://cdn.pixabay.com/photo/2021/06/07/13/46/user-6318008_1280.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">Willians Lopez</h5>
                                            <p> le hechaste mucho fertilizante cerca de la planta bro... </p>
                                            <ul class="list-inline">
                                                <li><a href="javascript:void(0);">Mar 12 2018</a></li>

                                            </ul>
                                        </div>
                                    </li>
                                    <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://cdn.pixabay.com/photo/2021/06/07/13/46/user-6318008_1280.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">William Umaña</h5>
                                            <p> Yo solo se que no se nada...</p>
                                            <ul class="list-inline">
                                                <li><a href="javascript:void(0);">Mar 20 2018</a></li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://cdn.pixabay.com/photo/2021/06/07/13/46/user-6318008_1280.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">Segio</h5>
                                            <p> Yo solo se que no entre a al meet</p>
                                            <ul class="list-inline">
                                                <li><a href="javascript:void(0);">Mar 20 2018</a></li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>  


                                <div class="card">
                                    <div class="body">
                                        <div class="comment-form">
                                            <form class="row clearfix">
                                                <div class="col-sm-12">
                                                    <div class="form-group">
                                                        <textarea rows="4" class="form-control no-resize" placeholder="Escribe Aqui..."></textarea>
                                                    </div>
                                                    <button type="submit" class="btn btn-block btn-primary">Comentar</button>
                                                </div>                                
                                            </form>
                                        </div>
                                    </div>
                                </div>


                            </div>


                            
                        </div>
                    </div>   



                </div>
                <div class="col-lg-4 col-md-12 right-box">
                    <div class="card">
                        <div class="body search">
                            <div class="input-group m-b-0">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fa fa-search"></i></span>
                                </div>
                                <input type="text" class="form-control" placeholder="Buscar Publicacion...">                                    
                            </div>
                        </div>
                    </div>


                </div>
            </div>

        </div>
            <script>
        var coment = document.getElementById('mostrar_comentarios');
        var btn_mostar = document.getElementById('btn_mostar');
        var btn_ocultar = document.getElementById('btn_ocultar');
        

        function mostrarComentarios () {
            coment.style.display = 'inline';
            btn_mostar.style.display = 'none';
            btn_ocultar.style.display = 'inline';
            
        }

    function ocultarComentarios () {
            coment.style.display = 'none';
            btn_mostar.style.display = 'inline';
            btn_ocultar.style.display = 'none';
            
        }

    </script>
    </div>


    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/grayscale.js"></script>
</body>

</html>