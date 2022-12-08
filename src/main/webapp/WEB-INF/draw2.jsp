
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
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
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <!--    switcher-->
    <link rel="stylesheet" href="https://unpkg.com/ol-layerswitcher@4.1.0/dist/ol-layerswitcher.css" />
    <!-- ol-ext -->
    <link rel="stylesheet" href="https://cdn.rawgit.com/Viglino/ol-ext/master/dist/ol-ext.min.css" />
    <script type="text/javascript" src="https://cdn.rawgit.com/Viglino/ol-ext/master/dist/ol-ext.min.js"></script>
  </head>
  <body>
      
    <div id="map" class="map"></div>
    <div class="container">
        
        <div class="mb-3">
            <form action="Mapa?accion=display" method="post">
                                
                <button type="submit" class="btn btn-dark">Regresar</button>
            </form>
            <form action="Mapa?accion=draw" method="post">
                <label for="drawyear">Cambiar de año</label>
                <select  class="form-select" id="drawyear" name="drawyear">
                    <c:forEach
                        var="y"
                        items="${listaYears}">

                        <option value="${y}"><c:out value="${y}"/></option>



                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary">Cambiar año</button>
            </form>
        </div> 
        <div class="mb-3">
            <h6>Instrucciones</h6>
            <p>Solo se pueden registrar un Punto, LineString o Polígono a la vez. FALTA</p>
            <p>MultiPolygon permite registrar más de un polígono como una sola forma.</p>
            <p>Una vez se termine de dibujar, se debe seleccionar el botón de Cargar coordenadas! Esto debería ser automático?</p>
        </div>    
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

                <input  type="text" class="form-control" id="geometry" name="geometry" readonly>
            </div>
            <input  type="hidden" class="form-control" id="id" name="id" value="">
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
  

    <!-- Pointer events polyfill for old browsers, see https://caniuse.com/#feat=pointer -->
    <script src="https://unpkg.com/elm-pep@1.0.6/dist/elm-pep.js"></script>
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/ol-layerswitcher@4.1.0"></script>
    <script type="text/javascript">
        
        
//	
        const extent = ol.proj.get('EPSG:3857').getExtent().slice();
        extent[0] += extent[0];
        extent[2] += extent[2];
        
        var myview = new ol.View({
		center: [-499493.345476581,7199956.094740773],
          zoom: 4,
          extent,
	});
        
        //draw
        const raster = new ol.layer.Tile({
          source: new ol.source.OSM(),
        });
        
        var source = new ol.source.Vector({wrapX: false});
        var vector = new ol.layer.Vector({
            source: source,
            title: 'Dibujo'
          });
                          
        <c:forEach items="${geojsonList}" var="mdto"> 
            <c:choose>
                <c:when test="${mdto.entidad.year == year}">
                    

                       source = new ol.source.Vector({
                            features:  new ol.format.GeoJSON().readFeatures(${mdto.entidad.map}),                                            
                        });
                        vector = new ol.layer.Vector({
                            source: source,
                            title: '${mdto.entidad.year}'
                          });
                </c:when>
            </c:choose>                       
        </c:forEach>  
                       
        
        
        
        // Limit multi-world panning to one world east and west of the real world.
        // Geometry coordinates have to be within that range.
        

//        const map = new ol.Map({
//          layers: [raster, vector],
//          target: 'map',
//          view: new ol.View({
//            center: [-11000000, 4600000],
//            zoom: 4,
//            extent,
//          }),
//        });
        
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
                    layers:[ vector
                                   
                    ]
                })
            ],
            view: myview
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
               if(typeof feature.getId() === 'undefined'){
                   const value = typeSelect.value;
                   console.log(value);
                    if (value !== 'Polygon') {
                        geoString = feature.getGeometry().flatCoordinates;
                    }else{
                        console.log('aqio!!');
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
                    }
                   
               }
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
        // fin ls
        
        //erase
        var select = new ol.interaction.Select();

        window.addEventListener('keydown', function (event) {
            // A
            if (event.key === "a") {
                map.addInteraction(select);
            }
        });
        window.addEventListener('keyup', function (event) {
            if (event.key === "a") {
                var selectedFeatures = select.getFeatures();
                //selectedFeatures.clear();
                map.removeInteraction(select);
            }
        });
        //fin erase
    </script>
  </body>
</html>