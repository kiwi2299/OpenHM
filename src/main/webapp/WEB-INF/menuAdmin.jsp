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
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>
  <body>
    <div class="container-fluid">
        	<nav class="navbar navbar-expand-lg navbar-light bg-light ">
            <a class="navbar-brand" href="/Mapa?accion=display"><h1>OpenHM</h1></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <c:choose>
                        <c:when test="${dto.entidad.tipo == 'mapper'}">
                            <li class="nav-item">
                                <div class="btn-group">
                                    <form action="Mapa?accion=draw" method="post">
                                        <button class="btn btn-success" type="submit">Dibujar un mapa</button>
                                    </form>
                                    <form action="Mapa?accion=loader" method="post">

                                        <button type="submit" class="btn btn-dark">Cargar un mapa</button>
                                    </form>
                                </div>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ${dto.entidad.name}
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                  <a class="dropdown-item" href="/Usuario?accion=menu">Mis mapas</a>
                                  <a class="dropdown-item" href="/Usuario?accion=gestion">Mi cuenta</a>
                                  <div class="dropdown-divider"></div>
                                  <a class="dropdown-item" target="_blank" href="https://drive.google.com/drive/folders/15NQ8R9FkoPnXj_49mS3lC5gu_Xyy4YXv?usp=sharing">Consultar documentación</a>
                                  <a class="dropdown-item" href="/Login?accion=cerrar">Cerrar sesión</a>
                                </div>
                            </li>
                        </c:when>
                        <c:when test="${dto.entidad.tipo == 'admin'}">
                            <li class="nav-item">
                                <div class="btn-group">
                                    <form action="Mapa?accion=draw" method="post">
                                        <button class="btn btn-success" type="submit">Dibujar un mapa</button>
                                    </form>
                                    <form action="Mapa?accion=loader" method="post">
                                        <button type="submit" class="btn btn-dark">Cargar un mapa</button>
                                    </form>
                                </div>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Administrador ${dto.entidad.name}
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item" href="/Admin?accion=menu">Ver Usuarios</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="/Usuario?accion=menu">Mis mapas</a>
                                    <a class="dropdown-item" href="/Usuario?accion=gestion">Mi cuenta</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="/Login?accion=cerrar">Cerrar sesión</a>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <div class="btn-group">
                                    <form action="Usuario?accion=crear" method="post">
                                        <button class="btn btn-success" type="submit">Crear una cuenta</button>
                                    </form>
                                    <form action="Login?accion=cerrar" method="post">
                                        <button type="submit" class="btn btn-dark">Salir</button>
                                    </form>
                                </div>
                            </li>

                        </c:otherwise>
                     </c:choose>
                        
                    
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <form class="d-flex" role="search" action="Login?accion=buscar" method="post">
                            <input class="form-control me-2" type="search" id="search" name="search" placeholder="Nombre, descripción, año, fuente" aria-label="Search">
                            <button class="btn btn-outline-success" type="submit">Buscar</button>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="text-center">
            <h2>Usuarios del sistema</h2>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Activo</th>
                    <th colspan="1">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach
                    var="mdto"
                    items="${listaUsuarios}">
                    <tr>
                        <td>    
                            <c:out value="${mdto.entidad.id}"/>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.name}"/>
                        </td>
                        <td>
                            <c:out value="${mdto.entidad.activo}"/>
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
