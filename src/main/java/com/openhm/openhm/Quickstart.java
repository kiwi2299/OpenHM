/*
 *    GeoTools Sample code and Tutorials by Open Source Geospatial Foundation, and others
 *    https://docs.geotools.org
 *
 *    To the extent possible under law, the author(s) have dedicated all copyright
 *    and related and neighboring rights to this software to the public domain worldwide.
 *    This software is distributed without any warranty.
 * 
 *    You should have received a copy of the CC0 Public Domain Dedication along with this
 *    software. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package com.openhm.openhm;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JDataStoreWizard;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.swing.table.FeatureCollectionTableModel;
import org.geotools.swing.wizard.JWizard;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 *
 * <p>This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class Quickstart {
    
    
    private static final Logger LOGGER = org.geotools.util.logging.Logging.getLogger(Quickstart.class);
    /**
     * GeoTools Quickstart demo application. Prompts the user for a shapefile and displays its
     * contents on the screen in a map frame
     */
    public static void main(String[] args) throws Exception {
        // display a data store file chooser dialog for shapefiles
//        LOGGER.info( "Quickstart");
//        LOGGER.config( "Welcome Developers");
//        LOGGER.info("java.util.logging.config.file="+System.getProperty("java.util.logging.config.file"));
//        File file = JFileDataStoreChooser.showOpenFile("shp", null);
//        if (file == null) {
//            return;
//        }
//        LOGGER.config("File selected "+file);
//
//        FileDataStore store = FileDataStoreFinder.getDataStore(file);
//        SimpleFeatureSource featureSource = store.getFeatureSource();
//
//        // Create a map content and add our shapefile to it
//        MapContent map = new MapContent();
//        map.setTitle("Quickstart");
//
//        Style style = SLD.createSimpleStyle(featureSource.getSchema());
//        Layer layer = new FeatureLayer(featureSource, style);
//        map.addLayer(layer);
//
//        // Now display the map
//        JMapFrame.showMap(map);
        
        Map<String, Object> params = new HashMap<>();
        params.put("dbtype", "postgis");
        params.put("host", "localhost");
        params.put("port", 5432);
        params.put("schema", "public");
        params.put("database", "postgres");
        params.put("user", "postgres");
        params.put("passwd", "postgres");

        DataStore dataStore = DataStoreFinder.getDataStore(params);
        if (dataStore == null) {
                System.out.println("Could not connect - check parameters");
        }
        
        String[] typeName = dataStore.getTypeNames();
        System.out.println(typeName[0]);
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName[0]);
        Filter filter = CQL.toFilter("name = 'Arizona-Arkansas'");
        SimpleFeatureCollection features = source.getFeatures(filter);
        System.out.println(features.size());
        Object[] contenido = features.toArray();
        System.out.println(contenido[0].toString());
        
        //table model
        /*FeatureCollectionTableModel model = new FeatureCollectionTableModel(features);
        System.out.println(model.getColumnName(2));
        System.out.println(model.getValueAt(1, 1));
        model.ge*/
        
        
        /*String inputTypeName = "pruebagis";
        SimpleFeatureType inputType = dataStore.getSchema(inputTypeName);

        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(inputTypeName);
        
        Filter filter = bbox(property("geom"),bbox);
        System.out.println(inputType.getName());
        System.out.println(source.getFeatures(filter));*/
        
        
    }
    
 
}