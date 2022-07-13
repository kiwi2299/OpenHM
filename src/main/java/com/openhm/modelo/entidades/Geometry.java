/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.modelo.entidades;

import java.io.Serializable;
import org.postgis.PGgeometry;

/**
 *
 * @author Usuario
 */
public class Geometry implements Serializable{
    private int id;
    private String name;
    private PGgeometry geom;
   // private LineString a;

    public Geometry() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeom() {
        return geom.toString();
    }

    public void setGeom(PGgeometry geom) {
        this.geom = geom;
    } 
    
}
