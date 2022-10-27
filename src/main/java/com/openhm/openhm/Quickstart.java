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


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

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
                
        String inputTypeName = "pruebagis";
        SimpleFeatureType inputType = dataStore.getSchema(inputTypeName);
        int attributeCount = inputType.getAttributeCount();
        System.out.println(attributeCount);   
        
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(inputTypeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = source.getFeatures();
//        String id = features.getID();
//        System.out.println(id);
        FeatureIterator<SimpleFeature> iterator = features.features();
        while(iterator.hasNext()){
            SimpleFeature feature = iterator.next();
            List<Object> attributes = feature.getAttributes();
            String id = feature.getID();System.out.println(id);
            for (int i = 0; i < attributes.size(); i++) {
                Object arg = attributes.get(i);
                System.out.println(arg.toString());
            }
        }
    }
}