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
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
<!--    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>-->
    <link rel="stylesheet" type="text/css" href="styles/styles.css">

    <!-- CSS only 
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    -->
    <!--    switcher-->
    <link rel="stylesheet" href="https://unpkg.com/ol-layerswitcher@4.1.0/dist/ol-layerswitcher.css" />
    <!-- // Add jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <title>Open History Mapper View</title>
</head>

<body>
    <div class="container-fluid">
        
        <nav class="navbar navbar-expand-lg navbar-light bg-light ">
            <c:if test="${dto != null}">
                <a class="navbar-brand" href="/Mapa?accion=display"><h1>OpenHM</h1></a>
            </c:if>
            <c:if test="${dto == null}">
                <a class="navbar-brand" href="/Login?accion=verMapa"><h1>OpenHM</h1></a>
            </c:if>
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
        <c:if test="${dto != null}">
        
        <form action="Admin?accion=verMapas" method="post">
            <input type="hidden" value="${user.entidad.id}" name="id"/>
            <input type="hidden" value="${user.entidad.name}" name="name"/>
            <button class="btn btn-primary" type="submit">Mapas de ${user.entidad.name}</button>
        </form>
            
    
    
        
    
        </c:if> 
        <div id="map" class="map"></div>
    </div>
    <!--  pop up de informacion  -->
    <div id="popup" class="ol-popup">
        <a href="#" id="popup-closer" class="ol-popup-closer"></a>
        <div id="popup-content"></div>
    </div>
    <!--  fin pop up de informacion  -->
  
    

    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,Object.assign,Event,URL"></script>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@main/dist/en/v7.0.0/legacy/ol.js"></script>
    <script src="https://unpkg.com/ol-layerswitcher@4.1.0"></script>
    <script type="text/javascript">
        
        
        //draw
        const raster = new ol.layer.Tile({
            title: 'OSM',
          source: new ol.source.OSM(),
        });

        const source =  new ol.source.Vector({
           
                features:  new ol.format.GeoJSON().readFeatures(${geojson}),                                            
            });
                        

        const vector = new ol.layer.Vector({
             title: 'Consulta',
             source: source,
        });
        
        // Limit multi-world panning to one world east and west of the real world.
        // Geometry coordinates have to be within that range.
        const extent = ol.proj.get('EPSG:3857').getExtent().slice();
        extent[0] += extent[0];
        extent[2] += extent[2];

        const map = new ol.Map({
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
                }), vector],
          target: 'map',
          view: new ol.View({
            center: [-11000000, 4600000],
            zoom: 4,
            extent,
          }),
        });
        
        
        
        //layer switcher
        var layerSwitcher = new ol.control.LayerSwitcher({
            activationMode: 'click',
            startActive: false,
            tipLabel: 'Cambiar mapas',
            groupSelectStyle: 'children' // Can be 'children' [default], 'group' or 'none'
          });
          map.addControl(layerSwitcher);
          
        //Popup
        var container = document.getElementById('popup');
        var content = document.getElementById('popup-content');
        var closer = document.getElementById('popup-closer');
        
        var popup = new ol.Overlay({
            element: container, 
            autoPan: {
                animation: {
                    duration: 250,
                },  
            },
        });
        
        
        closer.onclick = function(){
            popup.setPosition(undefined);
            closer.blur();
            return false;
        };
        
        map.addOverlay(popup);
        
       
        
        map.on('singleclick', function(evt){
            //var feature = new ol.Feature;
            var feature = map.forEachFeatureAtPixel(evt.pixel,
              function(feature) {
                return feature;
              });
            if (feature) {
                console.log(evt.coordinate);
                
                 
                //var coord = feature.getGeometry().flatCoordinates;
                var props = feature.getProperties();
                console.log(props);
                var cont = '<p>Nombre: ' + feature.get('COUNTRY_NAME') + '</p>';
                    cont += '<p>Descripción: ' + feature.get('DESCRIPTION') + '</p>';
                    cont += '<p>Año: ' + feature.get('YEAR') + '</p>';
                    cont += '<p>Fuente: ' + feature.get('SOURCE') + '</p>';
                    cont += '<a href="'+feature.get('SOURCE')+'" class="btn btn-success" target="_blank">Ir a fuente</a>';

                content.innerHTML = cont;
                popup.setPosition(evt.coordinate);

                //console.info(feature.getProperties());
            }
        });
        // fin popup
    </script>

</body>

</html>