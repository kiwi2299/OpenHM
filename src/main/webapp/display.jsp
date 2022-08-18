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
    <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.14.1/css/ol.css" type="text/css">
    <link rel="stylesheet" href="styles/styles.css">
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.14.1/build/ol.js"></script>

    <link rel="stylesheet" type="text/css" href="styles/timeline.css">
    <!-- CSS only 
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    -->
    <!-- // Add jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

    <script src="js/timeline.js"></script>

    <title>Open History Mapper for Mapper</title>
</head>

<body>
    <div class="topnav">
        <button onclick="myFunction()" class="nav-item">Iniciar sesion</button>
        <h1 class="nav-item">Open History Mapper</h1>
    </div>
    <div id="myDIV" style="display: none;">
        <form action="" class="form">
            <h3 class="h3 mb-3 fw-normal">Iniciar sesion</h3>
            <div class="form-floating">
                <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
                <label for="floatingInput">Email address</label>
            </div>

            <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
                <label for="floatingPassword">Password</label>
            </div>
        </form>
    </div>
    <div>
        <p>${size}</p>
        <input type='hidden' id='geojson' name="geojson" value="${listaMapas[1].entidad.map}">
        
    </div>
    <div id="map" class="map"></div>
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
    
	//var geoJsonFeatures = new ol.Feature;
	
	
          
       
        var layer = [mylayer];
	
        console.log(${size});
      
       <c:forEach items="${listaMapas}" var="mapa" varStatus="status"> 
            layer.push(new ol.layer.Vector({
                    source: new ol.source.Vector({
                        features: new ol.format.GeoJSON().readFeatures(${mapa.entidad.map})
                    })
                })
            );
        console.log(${mapa.entidad.map});
        </c:forEach> 
       


	
	
	
	var map = new ol.Map({
            target: 'map',
            layers: layer,
            view: myview
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