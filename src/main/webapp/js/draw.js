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