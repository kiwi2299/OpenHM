<%-- 
    Document   : gestionUsuario
    Created on : 02/12/2022, 03:49:19 PM
    Author     : kiwir
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestión de cuenta</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  </head>
  <body>
    <div class="container">
            <h1>${dto.entidad.name}</h1>
            <h2>Tu cuenta</h2>
            <h5>${msj_us}</h5>
            <form action="Usuario?accion=menu" method="post">
                                
                <button type="submit" class="btn btn-dark">Regresar</button>
            </form> 
            <form action="Usuario?accion=borrar" method="post">
                <input type="hidden" value="${dto.entidad.id}" name="id"/>            
                <button type="submit" class="btn btn-danger">Borrar Cuenta</button>
            </form> 
            <form action="Usuario?accion=update" method="post">
                <input type="hidden" value="${dto.entidad.id}" name="id"/>
            <table class="table">
            <thead>
                <tr>
                    <th>Campo</th>
                    <th>Valor</th>
                    <th colspan="3">Modificar</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>                                
                        Nombre de Usuario
                    </td>
                    <td>                                
                        <c:out value="${dto.entidad.name}"/>
                    </td>
                    <td>    
                        
                        <input type="text" class="form-control" id="name" name="name"/>
                        
                    </td>
                </tr>
                <tr>
                    <td>                                
                        Contraseña
                    </td>
                    <td>                                
                        <c:out value="********"/>
                    </td>
                    <td>    
                        
                            <input type="text" class="form-control" id="password" name="password"/>
                            
                    </td>
                </tr>
                <tr>
                    <td>                                
                        Correo
                    </td>
                    <td>                                
                        <c:out value="${dto.entidad.email}"/>
                    </td>
                    <td>    
                        
                            <input type="email" class="form-control" id="email" name="email"/>
                            
                    </td>
                </tr>
            </tbody>
            
        </table>
                    <button type="submit" class="btn btn-danger">Cambiar</button>
        </form>

        
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  </body>
</html>
</html>
