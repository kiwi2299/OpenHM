package com.openhm.modelo.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.openhm.modelo.entidades.Mapa;
import java.io.Serializable;

public class MapaDTO implements Serializable{
    private Mapa entidad;

    public MapaDTO() {
        entidad = new Mapa();
    }

    public Mapa getEntidad() {
        return entidad;
    }

    public void setEntidad(Mapa entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getEntidad().getId()).append("\n");
        sb.append("name: ").append(getEntidad().getName()).append("\n");
        sb.append("userId: ").append(getEntidad().getUser_id()).append("\n");
        sb.append("year: ").append(getEntidad().getYear()).append("\n");
        sb.append("view: ").append(getEntidad().isView()).append("\n");
        sb.append("map: ").append(getEntidad().getMap()).append("\n");
        sb.append("description: ").append(getEntidad().getDescription()).append("\n");
        sb.append("source: ").append(getEntidad().getSource()).append("\n");
        sb.append("insert_date: ").append(getEntidad().getInsert_date()).append("\n");
        return sb.toString();
    }
}
