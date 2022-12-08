<%-- 
    Document   : display
    Created on : 12/07/2022, 01:47:39 PM
    Author     : kiwir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
    
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
<!--    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>-->
    <link rel="stylesheet" type="text/css" href="styles/timeline.css">
    <link rel="stylesheet" type="text/css" href="styles/styles.css">

    <!-- CSS only 
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    -->
    <!--    switcher-->
    <link rel="stylesheet" href="https://unpkg.com/ol-layerswitcher@4.1.0/dist/ol-layerswitcher.css" />
    <!-- // Add jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>Open History Mapper for Mapper</title>
</head>

<body>
    <div class="topnav">
        <h1 class="nav-item">Open History Mapper</h1>
    </div>
        <div class="row">
            <c:choose>
                <c:when test="${dto.entidad.tipo == 'mapper'}">
                    <div class="col-2">
                        <form action="Mapa?accion=draw" method="post">
                            <button class="btn btn-primary" type="submit">Dibujar</button>
                        </form>
                    </div>
                    <div class="col-2">
                        <form action="Login?accion=cerrar" method="post">
                            <button class="btn btn-primary" type="submit">Cerrar Sesión</button>
                        </form>
                    </div>
                    <div class="col-2">
                        <form action="Usuario?accion=menu" method="post">
                            <button class="btn btn-primary" type="submit">Menú usuario</button>
                        </form>
                    </div>
                </c:when>
                <c:when test="${dto.entidad.tipo == 'admin'}">

                    <form action="Login?accion=cerrar" method="post">
                        <button class="btn btn-primary" type="submit">Cerrar Sesión</button>
                    </form>
                    <form action="Admin?accion=menu" method="post">
                        <button class="btn btn-primary" type="submit">Menú Admin</button>
                    </form>
                    <form action="Usuario?accion=menu" method="post">
                        <button class="btn btn-primary" type="submit">Tus Mapas</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="Usuario?accion=crear" method="post">                        
                        <button class="btn btn-info btn-lg btn-block" type="submit">Crear usuario</button>
                    </form>
                </c:otherwise>
             </c:choose>
        </div>
       <form action="Mapa?accion=buscar" method="post">
            <div class="mb-3">
              <label for="name">Búsqueda</label>
              <input  type="text" class="form-control" id="search" name="search" aria-describedby="searchHelp" required>
              <div id="searchHelp" class="form-text">Ingresa el nombre, descripción, año o fuente</div>
            </div>
           <button type="submit" class="btn btn-dark">Buscar</button>
        </form>
        
        
    
        <div>
            <p>Bienvenido ${dto.entidad.name}</p>
            <p>${msj}</p>

        </div>
    
    <div id="map" class="map"></div>
    <div style="display: none;">
      
      <!-- Popup -->
      <div id="popup2"></div>
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
        
        
	var myview = new ol.View({
		center: [981546161170.347, 2496866.115180862],
          zoom: 4
	});
	
	var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Group({
                    title: 'Base maps',
                    layers: [
                        new ol.layer.Tile({
                            title: 'Satelite',
                            type: 'base',
                            visible: true,
                            source:  new ol.source.XYZ({
                                attributions: ['Esri, Maxar, Earthstar Geographics, and the GIS User Community'],
                                attributionsCollapsible: false,
                                url: 'https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
                            })
                        }),
                        new ol.layer.Tile({
                            title: 'OSM',
                            type: 'base',
                            visible: false,
                            source: new ol.source.OSM(),
                        })
                    ]
                }),
                new ol.layer.Group({
                    title: 'Años',
                    fold: 'open',
                    layers:[
                        <c:forEach items="${geojsonList}" var="mdto"> 
                                            
                        new ol.layer.Vector({
                            title: '${mdto.entidad.year}',
                            
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(${mdto.entidad.map}),                                            
                            })
                        }),
                        </c:forEach>            
                    ]
                })
            ],
            view: myview
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
        
       
        const element = popup.getElement();
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
                cont += '<p>DESCRIPCIÓN: ' + feature.get('DESCRIPTION') + '</p>';
                cont += '<p>YEAR: ' + feature.get('YEAR') + '</p>';
                cont += '<p>SOURCE: ' + feature.get('SOURCE') + '</p>';
                //cont += '<h3>ID: ' + feature.get('MAP_ID') + '</h3>';
                cont+= '<form action="Mapa?accion=editar" method="post">';
                cont+= '<input type="hidden" value="'+feature.get('MAP_ID')+'" name="id"/>';
                cont+= '<button type="submit" class="btn btn-primary">Editar</button></form>';
                /* let popover = bootstrap.Popover.getInstance(element);
                if (popover) {
                  popover.dispose();
                }
                popover = new bootstrap.Popover(element, {
                  animation: false,
                  container: element,
                  content: cont,
                  html: true,
                  placement: 'top',
                  title: feature.get('COUNTRY_NAME')
                });
                popover.show();   */        
                content.innerHTML = cont;
                popup.setPosition(evt.coordinate);
                
                //console.info(feature.getProperties());
            }
        });
        // fin popup  
    </script>
    <script src="js/main.js"></script>
</body>
</html>
