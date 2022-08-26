/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.controlador;

import com.openhm.modelo.dao.UsuarioDAO;
import com.openhm.modelo.dto.UsuarioDTO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class PruebaDB {
    
    public static void main(String[] args) {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        
        dto.getEntidad().setName("v");
        dto.getEntidad().setPassword("victor");
        dto.getEntidad().setEmail("victor@victor.com");
        //System.out.println(dto.toString());
        
        try {
            dto = dao.read(dto);
            if(dto != null){
                System.out.println(dto.getEntidad().getName());
            }else{
                System.out.println("No hay usuario con ese nombre");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PruebaDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            
        }
    }
    
}
