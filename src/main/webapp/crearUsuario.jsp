<%-- 
    Document   : menuUsuario
    Created on : 22/08/2022, 01:02:19 PM
    Author     : Usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  </head>
  <body>
    <div class="container">
            <h1>Creación de usuario</h1>
            <form action="Usuario?accion=insertar" method="post">
                
                <div class="mb-3">
                  <label for="name">Nombre</label>
                  <input  type="text" class="form-control" id="name" name="name"  required>
                </div>
                <div class="mb-3">
                  <label for="password">Contraseña</label>
                  <input type="password" class="form-control" name="password" id="password" required>
                </div>
                <div class="mb-3">
                  <label for="name">Correo</label>
                <input type="email"  class="form-control" name="email" id="email" required>
                </div>

                <button class="btn btn-primary" type="submit">Registrar</button>
            </form>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  </body>
</html>
</html>
