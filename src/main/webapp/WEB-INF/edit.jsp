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
    <title>Open History Mapper for Mapper</title>
</head>

<body>
    <div class="topnav">
       
        
        <h1 class="nav-item">Open History Mapper</h1>
        <form action="Usuario?accion=menu" method="post">
            <button class="btn btn-dark" type="submit">Regresar</button>
        </form>
    </div>
    
    <div>
        <p>Editar con ${dto.entidad.name}</p>
    </div>
    <div id="map" class="map"></div>
    <div class="container">
        
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
              <label for="name">ID</label>
              <input  type="text" class="form-control" id="id" name="id" value="${mdto.entidad.id}"  readonly>
              
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
  
    

    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,Object.assign,Event,URL"></script>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@main/dist/en/v7.0.0/legacy/ol.js"></script>
    <script src="https://unpkg.com/ol-layerswitcher@4.1.0"></script>
    <script type="text/javascript">
        
        
//	var myview = new ol.View({
//		center: [981546161170.347, 2496866.115180862],
//          zoom: 4
//	});
//        
//        const source =  new ol.layer.Vector({
//                            source: new ol.source.Vector({
//                                features:  new ol.format.GeoJSON().readFeatures(${geojson}),                                            
//                            })
//                        });
//	
//	var map = new ol.Map({
//            target: 'map',
//            layers: [
//                new ol.layer.Group({
//                    title: 'Base maps',
//                    layers: [
//                        new ol.layer.Tile({
//                            title: 'Satelite',
//                            type: 'base',
//                            visible: true,
//                            source:  new ol.source.XYZ({
//                                attributions: ['Esri, Maxar, Earthstar Geographics, and the GIS User Community'],
//                                attributionsCollapsible: false,
//                                url: 'https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
//                            })
//                        }),
//                        new ol.layer.Tile({
//                            title: 'OSM',
//                            type: 'base',
//                            visible: false,
//                            source: new ol.source.OSM(),
//                        })
//                    ]
//                }),
//                new ol.layer.Group({
//                    title: 'Edición',
//                    fold: 'open',
//                    layers:[source]
//                })
//            ],
//            view: myview
//	});
        
        //modify
//        const select = new ol.interaction.Select({
//            wrapX: false,
//        });
//        
//        const modify = new ol.interaction.Modify({
//            features: select.getFeatures(),
//        });
//        map.addInteraction(select);
//        map.addInteraction(modify);
        //Add Modify Control to map
        
          //Remove previous interactions
         // map.removeInteractions();

//          //Select Features
//          let select = new ol.interaction.Select({
//            layers: [source],
//            
//          });
//
//          //Add Modify Control to map
//          let modify = new ol.interaction.Modify({
//            features: select.getFeatures()  
//          });
//
//          map.addInteraction(select);
//          map.addInteraction(modify);
        
        //fin modify
        
        
        
        
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
            var geoString = "";
            console.log(features.length);
            //let feature = new ol.Feature;
            for (var i = 0; i < features.length; i++) {
               var feature = new ol.Feature;
               feature = features[i];
               console.log(feature.getGeometry());
               
               
               if(i>0){
                   console.log("div");
                   geoString += " | "+feature.getGeometry().flatCoordinates;
               }else{
                   geoString = feature.getGeometry().flatCoordinates;
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
    </script>
    <script src="js/main.js"></script>

</body>

</html>