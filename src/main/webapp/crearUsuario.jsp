<%-- 
    Document   : menuUsuario
    Created on : 22/08/2022, 01:02:19 PM
    Author     : Usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Crear usuario</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>
  <body>
    <div class="container-fluid">
        <nav class="navbar navbar-expand-lg navbar-light bg-light ">
            <a class="navbar-brand" href="/"><h1>OpenHM</h1></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                        
                    
                </ul>
                <form action="Login?accion=verMapa" method="post">
                    <div class="d-grid gap-2 col-lg mx-auto">
                        <input class="btn btn-success" type="submit" value="Ver Mapa">
                    </div>
                </form>  
                
            </div>
        </nav>
        <div class="text-center">
            <h1>Creación de usuario</h1>

            
        </div>
            <h3>${msj}</h3>
            
            <form action="Usuario?accion=insertar" method="post">
                
                <div class="mb-3">
                  <label for="name">Nombre</label>
                  <input  type="text" class="form-control" id="name" name="name"  required>
                </div>
                <div class="mb-3">
                  <label for="password">Contraseña</label>
                  <input type="password" class="form-control" name="password" id="password" maxlength="72" required>
                </div>
                <div class="mb-3">
                  <label for="name">Correo</label>
                <input type="email"  class="form-control" name="email" id="email" required>
                </div>
                <div>
                    <p>${msj_us}</p>
                </div>
                <button class="btn btn-outline-success" type="submit">Registrar</button>
                
            </form>
                
        </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  </body>
</html>
</html>
