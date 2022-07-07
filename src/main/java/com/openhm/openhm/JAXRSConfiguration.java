package com.openhm.openhm;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
    public static void main(String[] args) {
        System.out.println("Hello WOrld");
    }
    
}
