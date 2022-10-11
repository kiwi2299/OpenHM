<%-- 
    Document   : display
    Created on : 12/07/2022, 01:47:39 PM
    Author     : kiwir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>
    <link rel="stylesheet" type="text/css" href="styles/timeline.css">
    <link rel="stylesheet" type="text/css" href="styles/styles.css">
    <!-- CSS only 
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    -->
    <!-- // Add jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/timeline.js"></script>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>Open History Mapper for Mapper</title>
</head>

<body>
    <div class="topnav">
        <a class="btn btn-primary" href="draw.jsp" role="button">Draw</a>
        
        <h1 class="nav-item">Open History Mapper</h1>
    </div>
    
    <div>
        <p>Bienvenido ${dto.entidad.id}</p>
        <p>${msj}</p>
        
    </div>
    <div id="map" class="map"></div>
    <!--  pop up de informacion  -->
    <div id="popup" class="ol-popup">
        <a href="#" id="popup-closer" class="ol-popup-closer"></a>
        <div id="popup-content"></div>
    </div>
    <!--  fin pop up de informacion  -->
    <div class="horizontal-timeline" id="example">
        <div class="events-content">
            <ol>
                <li class="selected" data-horizontal-timeline='{"date": "01/01/1300"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/1400"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/1500"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/1600"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/1700"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/1800"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/1900"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/2000"}'></li>
                <li data-horizontal-timeline='{"date": "01/01/2022"}'></li>
            </ol>
        </div>
    </div>

    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <script type="text/javascript">
        	
	var myview = new ol.View({
		center: [981546161170.347, 2496866.115180862],
          zoom: 4
	});
	
	var mylayer = new ol.layer.Tile({
            source:  new ol.source.XYZ({
                url: 'http://mt0.google.com/vt/lyrs=s&hl=en&x={x}&y={y}&z={z}'
            })
	});
    
    
		//const geojsonObject = {"type":"GeometryCollection","crs":{"type":"name","properties":{"name":"EPSG:3857"}},"geometries":[{"type":"Polygon","coordinates":[[[645740.014953,1986139.742962],[1350183.667629,2671015.516397],[1448023.063834,1516510.641178],[645740.014953,1986139.742962]]]}]};
     // {"type":"GeometryCollection","crs":{"type":"name","properties":{"name":"EPSG:3857"}},"geometries":[{"type":"Polygon","coordinates":[[[-15556556.862994775,10772535.8838538],[-13615177.357247386,6284066.466565839],[-10338128.751545794,6454907.863071609],[-8753963.074855926,5585169.844496779],[-6936831.85747637,5911321.601462341],[-7464887.08303966,8567128.765324768],[-9577107.985292818,11238466.965233175],[-15556556.862994775,10772535.8838538]]]}]}
     
     //
//var geoJsonFeatures = new ol.Feature;
	
	
          
       
        var layer = [mylayer];
	
        console.log(${size});
      
      
            layer.push(new ol.layer.Vector({
                    source: new ol.source.Vector({
                        features:  new ol.format.GeoJSON().readFeatures(${geojsonString}),                                            
                    })
                })
            );
        
        
            
        
       

	var map = new ol.Map({
            target: 'map',
            layers: layer,
            view: myview
	});
        
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
                cont += '<h3>DESC: ' + feature.get('DESCRIPTION') + '</h3>';
                cont += '<h3>YEAR: ' + feature.get('YEAR') + '</h3>';
                cont += '<h3>SOURCE: ' + feature.get('SOURCE') + '</h3>';

                content.innerHTML = cont;
                popup.setPosition(evt.coordinate);

                //console.info(feature.getProperties());
            }
        });
    </script>
    <script>
        $('#example').horizontalTimeline({
            dateDisplay: "year"
        });

    </script>

    <script>
        // esta pedazo de codigo mandara a llamar la funcion para recuperar
        function showtest() {
            alert("Mostrar las capas de vectores referentes a esta fecha");
        }
    </script>
    <script src="js/main.js"></script>

</body>

</html>