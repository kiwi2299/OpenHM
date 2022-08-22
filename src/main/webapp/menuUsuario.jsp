<%-- 
    Document   : menuUsuario
    Created on : 22/08/2022, 01:02:19 PM
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="">
            <input type="text" name="name" id="name" required>
            <label for="name">Nombre</label>
            <input type="password" name="password" id="password" required>
            <label for="password">Contraseña</label>
            <input type="email" name="email" id="email" required>
            <label for="name">Correo</label>
            
            <button type="submit">Registrar</button>
        </form>
    </body>
</html>
