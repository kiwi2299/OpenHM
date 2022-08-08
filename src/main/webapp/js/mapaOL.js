//var map = new ol.Map({
//    target: 'map',
//    layers: [
//      new ol.layer.Tile({
//        source: new ol.source.OSM()
//      })
//    ],
//    view: new ol.View({
//      center: ol.proj.fromLonLat([37.41, 8.82]),
//      zoom: 4
//    })
//  });
  
  var myview = new ol.View({
        center: [981546161170.347, 2496866.115180862],
  zoom: 4
});
	
var mylayer = new ol.layer.Tile({
        source: new ol.source.OSM()
});

 //const geojsonObject = document.getElementById("geojson");
		const geojsonObject = {"type":"GeometryCollection","crs":{"type":"name","properties":{"name":"EPSG:3857"}},"geometries":[{"type":"Polygon","coordinates":[[[645740.014953,1986139.742962],[1350183.667629,2671015.516397],[1448023.063834,1516510.641178],[645740.014953,1986139.742962]]]}]};
		//const geojsonObject2 = {"type":"GeometryCollection","crs":{"type":"name","properties":{"name":"EPSG:3857"}},"geometries":[{"type":"Polygon","coordinates":[[[645740.014953,1986139.742962],[1350183.667629,2671015.516397],[1448023.063834,1516510.641178],[645740.014953,1986139.742962]]]}]};
	
	
const vectorSource = new ol.source.Vector({
  features: new ol.format.GeoJSON().readFeatures(geojsonObject), 
});

const vectorLayer = new ol.layer.Vector({
  source: vectorSource
  
});


	
var layer = [mylayer,vectorLayer]
	
var map = new ol.Map({
	target: 'map',
	layers: layer,
	view: myview
});