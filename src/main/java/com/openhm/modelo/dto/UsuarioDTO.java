package com.openhm.modelo.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.openhm.modelo.entidades.Usuario;
import java.io.Serializable;

public class UsuarioDTO implements Serializable{
    private Usuario entidad;

    public UsuarioDTO() {
        entidad = new Usuario();
    }

    public Usuario getEntidad() {
        return entidad;
    }

    public void setEntidad(Usuario entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getEntidad().getId()).append("\n");
        sb.append("name: ").append(getEntidad().getName()).append("\n");
        sb.append("pwd: ").append(getEntidad().getPassword()).append("\n");
        sb.append("email: ").append(getEntidad().getEmail()).append("\n");
        return sb.toString();
    }
}
