package com.openhm.modelo.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.openhm.modelo.entidades.Geometry;
import java.io.Serializable;

public class GeometryDTO implements Serializable{
    private Geometry entidad;

    public GeometryDTO() {
        entidad = new Geometry();
    }

    public Geometry getEntidad() {
        return entidad;
    }

    public void setEntidad(Geometry entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getEntidad().getId()).append("\n");
        sb.append("name: ").append(getEntidad().getName()).append("\n");
        sb.append("geom: ").append(getEntidad().getGeom()).append("\n");
        return sb.toString();
    }
}
