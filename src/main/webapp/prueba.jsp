<%-- 
    Document   : prueba
    Created on : 11/10/2022, 05:14:58 PM
    Author     : Usuario
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/css/ol.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="styles/timeline.css">
    <link rel="stylesheet" type="text/css" href="styles/styles.css">
    
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.15.1/build/ol.js"></script>
<!--    <link rel="stylesheet" type="text/css" href="styles/timeline.css">-->
    <!-- CSS only
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    -->
    <!-- // Add jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/timeline.js"></script>
    <title>Open History Mapper</title>
    <link rel="stylesheet" href="https://unpkg.com/ol-layerswitcher@4.1.0/dist/ol-layerswitcher.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>

<body>
    <h1 class="display-6">Open History Mapper</h1>
    <div class="container text-left py-1 m-1">
        <div class="py-1 d-inline">
            <button onclick="myFunction()" class="nav-item">Iniciar sesion</button>
        </div>
<!--        <div class="py-1 d-inline">-->
<!--            <a href="draw.jsp" >Draw</a>-->
<!--        </div>-->
    </div>

    <div id="myDIV" style="display: none;">
        <section class="vh-100">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-6 text-black">

                        <div class="px-5 ms-xl-4">
                            <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
                            <span class="h1 fw-bold mb-0">OHM</span>
                        </div>

                        <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                            <form action="Login?accion=verificar" class="form" method="post">
                                <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Iniciar Sesion</h3>

                                <div class="form-outline mb-4">
                                    <input type="text" class="form-control form-control-lg" name="userName" id="userName" required>
                                    <label for="userName">Nombre de usuario</label>
                                </div>

                                <div class="form-outline mb-4">
                                    <input type="password" class="form-control form-control-lg" id="userPass" name="userPass" placeholder="Password" required>
                                    <label for="userPass">Password</label>
                                </div>
                                <div class="pt-1 mb-4">
                                    <button class="btn btn-info btn-lg btn-block" type="submit">Ingresar</button>
                                </div>

                            </form>
                            
                            

                        </div>
                        
                        <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <form action="Usuario?accion=menu" class="form" method="post">
                                
                                <div class="pt-1 mb-4">
                                    <button class="btn btn-info btn-lg btn-block" type="submit">Crear usuario</button>
                                </div>

                            </form>
                        </div>
                    </div>
                    <div class="col-sm-6 px-0 d-none d-sm-block">
                        <img src="https://images.unsplash.com/photo-1622449589142-7757caf37fb9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"
                             alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
                    </div>
                </div>
            </div>
        </section>
    </div>
    <div id="map" class="map"></div>
<!--    <div class="horizontal-timeline" id="example">-->
<!--        <div class="events-content">-->
<!--            <ol>-->
<!--                <li class="selected" data-horizontal-timeline='{"date": "01/01/1300"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/1400"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/1500"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/1600"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/1700"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/1800"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/1900"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/2000"}'></li>-->
<!--                <li data-horizontal-timeline='{"date": "01/01/2022"}'></li>-->
<!--            </ol>-->
<!--        </div>-->
<!--    </div>-->

    

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
                        title: 'Water color',
                        type: 'base',
                        visible: false,
                        source: new ol.source.Stamen({
                          layer: 'watercolor'
                        })
                      }),
                      new ol.layer.Tile({
                        title: 'OSM',
                        type: 'base',
                        visible: true,
                        source: new ol.source.OSM()
                      })
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
      

	
        
//        //Popup
//        var container = document.getElementById('popup');
//        var content = document.getElementById('popup-content');
//        var closer = document.getElementById('popup-closer');
//        
//        var popup = new ol.Overlay({
//            element: container, 
//            autoPan: {
//                animation: {
//                    duration: 250,
//                },  
//            },
//        });
//        
//        
//        closer.onclick = function(){
//            popup.setPosition(undefined);
//            closer.blur();
//            return false;
//        };
//        
//        map.addOverlay(popup);
//        
//       
//        
//        map.on('singleclick', function(evt){
//            //var feature = new ol.Feature;
//            var feature = map.forEachFeatureAtPixel(evt.pixel,
//              function(feature) {
//                return feature;
//              });
//            if (feature) {
//                console.log(evt.coordinate);
//                
//                 
//                //var coord = feature.getGeometry().flatCoordinates;
//                var props = feature.getProperties();
//                console.log(props);
//                var cont = '<h3>NOMBRE: ' + feature.get('COUNTRY_NAME') + '</h3>';
//                cont += '<h3>DESC: ' + feature.get('DESCRIPTION') + '</h3>';
//                cont += '<h3>YEAR: ' + feature.get('YEAR') + '</h3>';
//                cont += '<h3>SOURCE: ' + feature.get('SOURCE') + '</h3>';
//
//                content.innerHTML = cont;
//                popup.setPosition(evt.coordinate);
//
//                //console.info(feature.getProperties());
//            }
//        });
        // fin popup
        
    </script>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js" integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz" crossorigin="anonymous"></script>
</body>
</html>