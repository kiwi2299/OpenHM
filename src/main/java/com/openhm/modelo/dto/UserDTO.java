package com.openhm.modelo.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.openhm.modelo.entidades.User;
import java.io.Serializable;

public class UserDTO implements Serializable{
    private User entidad;

    public UserDTO() {
        entidad = new User();
    }

    public User getEntidad() {
        return entidad;
    }

    public void setEntidad(User entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getEntidad().getId()).append("\n");
        sb.append("name: ").append(getEntidad().getName()).append("\n");
        sb.append("pwd: ").append(getEntidad().getPassword()).append("\n");
        return sb.toString();
    }
}
