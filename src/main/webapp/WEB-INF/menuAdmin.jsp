<%-- 
    Document   : menuAdmin
    Created on : 04/11/2022, 19:02:19 PM
    Author     : kiwir
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Menú usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  </head>
  <body>
    <div class="container">
        <h1>Administrador ${dto.entidad.name}</h1>
        <h2>Usuarios del sistema</h2>
        <form action="Mapa?accion=display" method="post">
                                
            <button type="submit" class="btn btn-dark">Regresar</button>
        </form>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th colspan="1">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach
                    var="mdto"
                    items="${listaUsuarios}">
                    <tr>
                        <td>
                            <a 
                                class ='btn btn-primary btn-xs'
                                href="Usuario?accion=ver&id=<c:out value="${mdto.entidad.id}"/>">
                                <c:out value="${mdto.entidad.id}"/>
                            </a>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.name}"/>
                        </td>
                        <td>
                            <form action="Admin?accion=verMapas" method="post">
                                <input type="hidden" value="${mdto.entidad.id}" name="id"/>
                                <input type="hidden" value="${mdto.entidad.name}" name="name"/>
                                <button type="submit" class="btn btn-primary">Ver Mapas</button>
                            </form>
                        </td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
         
        
    </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  </body>
</html>
</html>
