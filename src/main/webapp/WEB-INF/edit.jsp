<%-- 
    Document   : display
    Created on : 03/11/2022, 02:47:39 PM
    Author     : kiwir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Draw Features</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
    <style>
      .map {
        height: 800px;
        width: 100%;
      }
    </style>
    <!-- jQuery -->
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <!--    switcher-->
    <link rel="stylesheet" href="https://unpkg.com/ol-layerswitcher@4.1.0/dist/ol-layerswitcher.css" />
    <!-- ol-ext -->
    <link rel="stylesheet" href="https://cdn.rawgit.com/Viglino/ol-ext/master/dist/ol-ext.min.css" />
    <script type="text/javascript" src="https://cdn.rawgit.com/Viglino/ol-ext/master/dist/ol-ext.min.js"></script>
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
    
    
    <div id="map" class="map"></div>
    
        <div class="options" >
                <h2>Opciones:</h2>
                <ul>
                  <input id="draw" type="radio" name="toggle" checked="checked" onchange="toggle(!$(this).prop('checked'))" />
                  <label for="draw"> dibujar polígono</label>
                  <br/>
                  <input id="drawhole" type="radio" name="toggle" onchange="toggle($(this).prop('checked'))" />
                  <label for="drawhole"> dibujar agujero</label>
                </ul>
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

                <input  type="text" class="form-control" id="geometry" name="geometry" required>
            </div>
            <div class="mb-3">
              <label for="name">ID</label>
              <input  type="text" class="form-control" id="id" name="id" value="${mdto.entidad.id}"  readonly>
              <input  type="hidden" class="form-control" id="user_id" name="user_id" value="${mdto.entidad.user_id}" >
            </div>
            <div class="mb-3">
              <label for="name">Nombre</label>
              <input  type="text" class="form-control" id="name" name="name" value="${mdto.entidad.name}" aria-describedby="nameHelp" required>
              <div id="nameHelp" class="form-text">Ingresa el nombre del país o territorio</div>
            </div>
            <div class="mb-3">
              <label for="desc">Descripción</label>
              <input  type="text" class="form-control" id="desc" name="desc" value="${mdto.entidad.description}">
            </div>
            <div class="mb-3">
              <label for="year">Año</label>
              <input  type="text" class="form-control" id="year" name="year" value="${mdto.entidad.year}">
            </div>
            <div class="mb-3">
              <label for="src">Fuente</label>
              <input  type="text" class="form-control" id="src" name="src" value="${mdto.entidad.source}">
            </div>


            <button type="submit" class="btn btn-primary">Guardar</button>
        </form>
    </div>
  
    

    <!-- Pointer events polyfill for old browsers, see https://caniuse.com/#feat=pointer -->
    <script src="https://unpkg.com/elm-pep@1.0.6/dist/elm-pep.js"></script>
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/ol-layerswitcher@4.1.0"></script>
    <script type="text/javascript">
          
        //draw
        //var raster = new ol.layer.Tile({
        //  source: new ol.source.OSM(),
        //});
        

        var source =  new ol.source.Vector({
            features:  new ol.format.GeoJSON().readFeatures(${geojson}),                                            
        });
                        

        var vector = new ol.layer.Vector({
            source: source,
        });
        
        // Limit multi-world panning to one world east and west of the real world.
        // Geometry coordinates have to be within that range.
        const extent = ol.proj.get('EPSG:3857').getExtent().slice();
        extent[0] += extent[0];
        extent[2] += extent[2];
        
        var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Group({
                    title: 'Mapas Base',
                    layers: [
                        new ol.layer.Tile({
                            title: 'Satelite',
                            type: 'base',
                            visible: false,
                            source:  new ol.source.XYZ({
                                attributions: ['Esri, Maxar, Earthstar Geographics, and the GIS User Community'],
                                attributionsCollapsible: false,
                                url: 'https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
                            })
                        }),
                        new ol.layer.Tile({
                            title: 'OSM',
                            type: 'base',
                            visible: true,
                            source: new ol.source.OSM(),
                        })
                    ]
                }),
                new ol.layer.Group({
                    title: 'Modificación',
                    fold: 'open',
                    layers:[ vector
                                   
                    ]
                }),
                new ol.layer.Group({
                    title: 'Capas',
                    fold: 'open',
                    layers:[
                        <c:forEach items="${geojsonList}" var="mdto"> 
                                            
                        new ol.layer.Vector({
                            visible: false,
                            title: '${mdto.entidad.year}',
                            
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(${mdto.entidad.map}),                                            
                            })
                        }),
                        </c:forEach>            
                    ]
                })
            ],
            view: new ol.View({
                center: [-11000000, 4600000],
                zoom: 4,
                extent,
            }),
	});
        
        const modify = new ol.interaction.Modify({source: source});
        map.addInteraction(modify);

        let snap; // global so we can remove them later
        const typeSelect = document.getElementById('type');

        var draw; // global so we can remove it later
        function addInteractions() {
          const value = typeSelect.value;
          if (value !== 'None') {
            draw = new ol.interaction.Draw({
              source: source,
              type: typeSelect.value,
            });
            map.addInteraction(draw);
            snap = new ol.interaction.Snap({source: source});
            map.addInteraction(snap);
          }
        }
        //drawhole
        var drawhole = new ol.interaction.DrawHole ({
            layers: [ vector ]
        });
        drawhole.setActive(false);
        map.addInteraction(drawhole);

        function toggle(active) {
            draw.setActive(!active);
            drawhole.setActive(active);
        }
        //fin drawhole

        /**
         * Handle change event.
         */
        typeSelect.onchange = function () {
          map.removeInteraction(draw);
          map.removeInteraction(snap);
          addInteractions();
        };

        document.getElementById('undo').addEventListener('click', function () {
          draw.removeLastPoint();
         
        });
        
        document.getElementById('save').addEventListener('click', function () {
            
            var features = source.getFeatures();
            var geoString = "";
            console.log(features.length);
            
            var count = 0;
            //let feature = new ol.Feature;
            for (var i = 0; i < features.length; i++) {
               var feature = new ol.Feature;
               feature = features[i];
               console.log(feature.getId());
               //if(typeof feature.getId() === 'undefined' || feature.getId() === ${mdto.entidad.id}){
                   const value = typeSelect.value;
                   console.log(value);
                    if (value === 'Polygon') {
                       
                        console.log('POLY');
                        count++;
                        if(count>1){
                            console.log("div");
                            geoString += " | ";
                            for(var j = 0; j < feature.getGeometry().getLinearRingCount();j++){
                                console.log(feature.getGeometry().getLinearRing(j).flatCoordinates);
                                if(j>0){
                                    console.log("anillin");
                                    geoString += " | "+feature.getGeometry().getLinearRing(j).flatCoordinates;
                                }else{
                                    geoString += feature.getGeometry().getLinearRing(j).flatCoordinates;
                                }
                            }
                        }else if(count === 1){
                            for(var j = 0; j < feature.getGeometry().getLinearRingCount();j++){
                                console.log(feature.getGeometry().getLinearRing(j).flatCoordinates);
                                if(j>0){
                                    console.log("anillin");
                                    geoString += " | "+feature.getGeometry().getLinearRing(j).flatCoordinates;
                                }else{
                                    geoString += feature.getGeometry().getLinearRing(j).flatCoordinates;
                                }
                            }
                        }
                    }else if(value === 'MultiPolygon'){
                        console.log('MULTIPoly');
                        var mp = ol.geom.MultiPolygon;
                        mp = feature.getGeometry();
                        var polygons = feature.getGeometry().getCoordinates();
                        console.log(polygons);
                        polygons.forEach(function(polygon){
                            //multiPolygon.appendPolygon(polygon);
                            for(var j = 0; j<polygon.length;j++ ){
                                
                                if(j>0){
                                    console.log("div");
                                    geoString += " | "+ polygon[j];
                                }else{
                                    if(i>0){
                                        console.log("div2");
                                        geoString += " | "+ polygon[j];
                                    }else{
                                        geoString += polygon[j];
                                    }
                                    
                                }
                            }
                            
                             console.log(polygon.length);
                        });
                         //console.log(feature.getGeometry().getPolygons().getPolygon(1));
                        //console.log(mp.getProperties());
                             
                         
                    }else if(value === 'Point' || value === 'LineString'){
                        geoString = feature.getGeometry().flatCoordinates;
                    }
                   
              // }
            }
            document.getElementById("geometry").value = geoString;
            console.log(document.getElementById("name").value);
        });

        addInteractions();
        
        //layer switcher
        var layerSwitcher = new ol.control.LayerSwitcher({
            activationMode: 'click',
            startActive: false,
            tipLabel: 'Cambiar mapas',
            groupSelectStyle: 'children' // Can be 'children' [default], 'group' or 'none'
        });
        map.addControl(layerSwitcher);
        //fin ls
         //borrar selected feature
        
        var selectInteraction = new ol.interaction.Select({
            condition: ol.events.condition.pointerMove,
            wrapX: false
        });
        
        map.addInteraction(selectInteraction);
        selectInteraction.setActive(false);
        

        
        var bool = false;
          var deleteFeature = function(evt){
           if(evt.keyCode === 46){
            var selectCollection = selectInteraction.getFeatures();
            console.log(selectCollection.getLength());
            if (selectCollection.getLength() > 0) {
                console.log(selectCollection.item(0));
                source.removeFeature(selectCollection.item(0));
            }
           }
           if(evt.keyCode === 16){
               bool = !bool;
               draw.setActive(bool);
               modify.setActive(bool);
               selectInteraction.setActive(!bool); 
           }
          };
          
          var drawFunc = function(evt){
              if(evt.keyCode === 16){
               draw.setActive(true);
           }
          }
          document.addEventListener('keydown', deleteFeature, false)
          //document.addEventListener('keyup', drawFunc, false)
        //fin erase
    </script>
    

</body>

</html>