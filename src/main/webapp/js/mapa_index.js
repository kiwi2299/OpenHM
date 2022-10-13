	
        
    var geojson;
    $.post('Mapa?accion=get', function(geojsonString) {
        //console.log(geojsonString);
        geojson = geojsonString;
    }).done(function(){
            //console.log(geojson);
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
                        }),
                        
                    ]
                }),
                new ol.layer.Group({
                    title: 'Años',
                    fold: 'open',
                    layers:[
                        new ol.layer.Vector({
                            title: '2022',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '2000',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1900',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1800',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1700',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1600',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1500',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1400',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
                        }),
                        new ol.layer.Vector({
                            title: '1300',
                            source: new ol.source.Vector({
                                features:  new ol.format.GeoJSON().readFeatures(geojson),                                            
                            })
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
        // fin popup

    });
        
        
        