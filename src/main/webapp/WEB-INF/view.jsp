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
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>Open History Mapper View</title>
</head>

<body>
    <div class="topnav">
       
        
        <h1 class="nav-item">Open History Mapper</h1>
        <form action="Admin?accion=verMapas" method="post">
            <input type="hidden" value="${user.entidad.id}" name="id"/>
            <input type="hidden" value="${user.entidad.name}" name="name"/>
            <button class="btn btn-primary" type="submit">Regresar</button>
        </form>
    </div>
    
    <div>
        <p>Mapa de ${user.entidad.name}, Nombre: ${mdto.entidad.name}</p>
    </div>
    <div id="map" class="map"></div>
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
          source: new ol.source.OSM(),
        });

        const source =  new ol.source.Vector({
                               features:  new ol.format.GeoJSON().readFeatures(${geojson}),                                            
                           });
                        

        const vector = new ol.layer.Vector({
          source: source,
        });
        
        // Limit multi-world panning to one world east and west of the real world.
        // Geometry coordinates have to be within that range.
        const extent = ol.proj.get('EPSG:3857').getExtent().slice();
        extent[0] += extent[0];
        extent[2] += extent[2];

        const map = new ol.Map({
          layers: [raster, vector],
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
                var cont = '<h3>NOMBRE: ' + feature.get('COUNTRY_NAME') + '</h3>';
                cont += '<h3>DESCRIPCIÓN: ' + feature.get('DESCRIPTION') + '</h3>';
                cont += '<h3>YEAR: ' + feature.get('YEAR') + '</h3>';
                cont += '<h3>SOURCE: ' + feature.get('SOURCE') + '</h3>';

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