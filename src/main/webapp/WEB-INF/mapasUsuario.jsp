<%-- 
    Document   : menuUsuario
    Created on : 04/11/2022, 21:04:19 PM
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
            <h1>Mapas de ${user.entidad.name}</h1>
            <h5>${msj_admin}</h5>
            <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Año</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Visible</th>
                    <th colspan="3">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach
                    var="mdto"
                    items="${listaMapas}">
                    <tr>
                        <td>
                            <a 
                                class ='btn btn-primary btn-xs'
                                href="/openhm/Mapa?accion=mapa&id=<c:out value="${mdto.entidad.id}"/>">
                                <c:out value="${mdto.entidad.id}"/>
                            </a>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.year}"/>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.name}"/>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.description}"/>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.view}"/>
                        </td>
                        <td>
                            
                        </td>
                        <c:if test="${mdto.entidad.view == 'En Proceso'}">
                        <td>
                            <form action="Admin?accion=rechazar" method="post">
                                <input type="hidden" value="${mdto.entidad.id}" name="id"/>
                                <button type="submit" class="btn btn-danger">Rechazar</button>
                            </form>
                        </td>
                        </c:if>
                        <c:if test="${mdto.entidad.view == 'En Proceso'}">
                            <td>
                                <form action="Admin?accion=aceptar" method="post">
                                    <input type="hidden" value="${mdto.entidad.id}" name="id"/>
                                    <button type="submit" class="btn btn-primary">Aceptar</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form action="Admin?accion=menu" method="post">
                                
            <button type="submit" class="btn btn-dark">Regresar</button>
        </form>
        
        </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  </body>
</html>
</html>
