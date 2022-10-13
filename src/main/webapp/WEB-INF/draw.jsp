<%-- 
    Document   : display
    Created on : 12/07/2022, 01:47:39 PM
    Author     : kiwir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    
    <title>Draw Features</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
    <style>
      .map {
        height: 800px;
        width: 100%;
      }
    </style>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    
  </head>
  <body>
    <div id="map" class="map"></div>
    <div class="container">
        
        <div class="mb-3">
            <h6>Instrucciones</h6>
            <p>Solo se pueden registrar un Punto, LineString o Polígono a la vez. FALTA</p>
            <p>MultiPolygon permite registrar más de un polígono como una sola forma.</p>
            <p>Una vez se termine de dibujar, se debe seleccionar el botón de Cargar coordenadas! Esto debería ser automático?</p>
        </div>    
        
        <form  action="Mapa?accion=crear" method="post">
            <div class="mb-3">
                <label for="type">Geometry type: &nbsp;</label>
                <select  class="form-select" id="type" name="type">
                  <option value="Point">Point</option>
                  <option value="LineString">LineString</option>
                  <option value="Polygon">Polygon</option>
                  <option value="MultiPolygon">MultiPolygon</option>
          <!--        <option value="Circle">Circle</option>-->
                  <option value="None">None</option>
                </select>
            </div>
            <div class="mb-3">
                <button type="button" class="btn btn-danger" id="undo">Deshacer</button>
                <button type="button" class="btn btn-success" id="save">Cargar coordenadas</button>

                <input  type="text" class="form-control" id="geometry" name="geometry" readonly>
            </div>
            
            <div class="mb-3">
              <label for="name">Nombre</label>
              <input  type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp" required>
              <div id="nameHelp" class="form-text">Ingresa el nombre del país o territorio</div>
            </div>
            <div class="mb-3">
              <label for="desc">Descripción</label>
              <input  type="text" class="form-control" id="desc" name="desc" >
            </div>
            <div class="mb-3">
              <label for="year">Año</label>
              <input  type="text" class="form-control" id="year" name="year" >
            </div>
            <div class="mb-3">
              <label for="src">Fuente</label>
              <input  type="text" class="form-control" id="src" name="src" >
            </div>


            <button type="submit" class="btn btn-primary">Guardar</button>
        </form>
    </div>
  
<!--    <form class="form-inline" action="Mapa?accion=geojson" method="post">
      
      
      <input class="form-control mr-2 mb-2 mt-2" type="submit" value="GeoJSON" id="submit">
    </form>-->
    <!-- Pointer events polyfill for old browsers, see https://caniuse.com/#feat=pointer -->
    <script src="https://unpkg.com/elm-pep@1.0.6/dist/elm-pep.js"></script>
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <script src="js/draw.js"></script>
  </body>
</html>