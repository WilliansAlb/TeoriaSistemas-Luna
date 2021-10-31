<%-- 
    Document   : Login
    Created on : 14/10/2021, 07:21:58 PM
    Author     : willi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="shortcut icon" href="../assets/img/icono1.png" type="image/x-icon">
        <link rel="stylesheet" href="../assets/css/login.css">
        <link rel="stylesheet" href="../assets/css/input.css">
        <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cabin:700">
        <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
        <link rel="stylesheet" href="../assets/fonts/simple-line-icons.min.css">
        <link rel="stylesheet" href="../assets/css/Account-setting-or-edit-profile.css">
        <link rel="stylesheet" href="../assets/css/Blog-Detail-App.css">
        <link rel="stylesheet" href="../assets/css/gradient-navbar-1.css">
        <link rel="stylesheet" href="../assets/css/gradient-navbar.css">
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("usuario") != null) {
                //response.sendRedirect("/AgendaLunar");
            }
        %>
        <nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
            <div class="container"><a class="navbar-brand" href="../index.jsp"><img src="../assets/img/icono1.png" width="40px">Agenda Lunar</a><button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive" type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" value="Menu"><i class="fa fa-bars"></i></button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item nav-link"><a class="nav-link" href="../index.jsp">TS INFO</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Blog.jsp">BLOG</a></li>
                        <li class="nav-item nav-link"><a class="nav-link active" href="Login.jsp">Login</a></li>
                        <li class="nav-item nav-link"><a class="nav-link" href="Cuenta.jsp">Cuenta</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <form action="C_Login" method="post">
        <div class="contenedor" id="luna" style="position:relative;top:370px;opacity: 0;">
            <h1 style="color:#303030;">AGENDA LUNAR</h1>
            <div class="omrs-input-group">
                <label class="omrs-input-underlined sombreado" style="display:block; font-family: 'Cabin';">
                    <input type="text" id="usuario" name="usuario" required style="font-family: 'Cabin';">
                    <span class="omrs-input-label">USUARIO</span>
                </label>
            </div>
            <div class="omrs-input-group">
                <label class="omrs-input-underlined sombreado" style="display:block;font-family: 'Cabin';">
                    <input type="password" id="password" name="password" required style="font-family: 'Cabin';">
                    <span class="omrs-input-label">CONTRASEÑA</span>
                </label>
            </div>
            <button class="noselect"><span class='text' style="font-family: 'Cabin';">ENTRAR</span><span class="icon"><img src="../assets/img/moon.svg" alt="luna" width="30em"></span></button>
            <a href="#" style="margin-top: 0.5em;font-family: 'Cabin';">OLVIDE MI CONTRASEÑA</a>
        </div>
        </form>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../assets/js/grayscale.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script>
            window.onload = () => {
                var luna = document.getElementById("luna");
                $("#luna").animate({
                    top: "-=350px",
                    opacity: "+=1"
                }, 1000);
            };
        </script>
    </body>
</html>
