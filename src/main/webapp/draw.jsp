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
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
    <style>
      .map {
        height: 800px;
        width: 100%;
      }
    </style>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>
    
    
  </head>
  <body>
    <div id="map" class="map"></div>
    <form class="form-inline" action="Mapa?accion=crear" method="post">
      <label for="type">Geometry type: &nbsp;</label>
      <select class="form-control mr-2 mb-2 mt-2" id="type" name="type">
        <option value="Point">Point</option>
        <option value="LineString">LineString</option>
        <option value="Polygon">Polygon</option>
        <option value="Circle">Circle</option>
        <option value="None">None</option>
      </select>
      <input class="form-control mr-2 mb-2 mt-2" type="button" value="Undo" id="undo">
      <input class="form-control mr-2 mb-2 mt-2" type="button" value="Save" id="save">
      <input  type="text" id="geometry" name="geometry" readonly>
      <label for="name">Nombre</label>
      <input  type="text" id="name" name="name" required>
      <input class="form-control mr-2 mb-2 mt-2" type="submit" value="submit" id="submit">
    </form>
    <!-- Pointer events polyfill for old browsers, see https://caniuse.com/#feat=pointer -->
    <script src="https://unpkg.com/elm-pep@1.0.6/dist/elm-pep.js"></script>
    
    <script type="text/javascript">
        
    const raster = new ol.layer.Tile({
          source: new ol.source.OSM(),
        });

        const source = new ol.source.Vector({wrapX: false});

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
        
        const modify = new ol.interaction.Modify({source: source});
        map.addInteraction(modify);

        let snap; // global so we can remove them later
        const typeSelect = document.getElementById('type');

        let draw; // global so we can remove it later
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
            //let feature = new ol.Feature;
            for (var i = 0; i < features.length; i++) {
               var feature = new ol.Feature;
               feature = features[i];
               console.log(feature.getGeometry());
               document.getElementById("geometry").value = feature.getGeometry().flatCoordinates;
               
            }
            
                
                
            
            //console.log(source.getFeatures().getGeometry());
        });

        addInteractions();


    </script>
  </body>
</html>