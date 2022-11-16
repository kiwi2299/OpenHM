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
    <title>Menú usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  </head>
  <body>
    <div class="container">
        <h1>Búsqueda</h1>

        <h5>Se ingresó: '${search}'</h5>
        <form action="Mapa?accion=buscar" method="post">
            <div class="mb-3">
              <label for="name">Búsqueda</label>
              <input  type="text" class="form-control" id="search" name="search" aria-describedby="searchHelp" required>
              <div id="searchHelp" class="form-text">Ingresa el nombre, descripción, año o fuente</div>
              <button type="submit" class="btn btn-dark">Buscar</button>
            </div>
        </form>
        <form action="Mapa?accion=display" method="post">
             <div class="mb-3">
                 <button type="submit" class="btn btn-dark">Regresar</button>
             </div>
        </form>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Usuario</th>
                    <th>Año</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Visible</th>
                    <th colspan="1">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach
                    var="mdto"
                    items="${listaMapas}">
                    <tr>
                        <td>                                
                            <c:out value="${mdto.entidad.id}"/>
                        </td>
                        <td>                                
                            <c:out value="${mdto.entidad.userName}"/>
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
                            
                            <form action="Mapa?accion=editar" method="post">
                                <input type="hidden" value="${mdto.entidad.id}" name="id"/>
                                <button type="submit" class="btn btn-primary">Ver/Editar</button>
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
