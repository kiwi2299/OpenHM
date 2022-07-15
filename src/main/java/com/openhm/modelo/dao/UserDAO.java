/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.modelo.dao;


import com.openhm.modelo.dto.UserDTO;
import com.openhm.modelo.entidades.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDAO {
    
    private static final String SQL_INSERT="insert into usuarios (name, password) values(?,?)";
    private static final String SQL_UPDATE="update usuarios set name = ?, password = ? where id = ?";
    private static final String SQL_DELETE="delete from usuarios where id = ?";
    private static final String SQL_READ="select * from usuarios where name = ?, password = ?";
    private static final String SQL_READ_ALL="select * from usuarios";

    private Connection con;
    public Connection ObtenerConexion(){
       String usr = "postgres";
       String pwd = "postgres";
       String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,usr,pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void create(UserDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = con.prepareStatement(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getPassword());
            cs.executeUpdate();
        } finally {
            if(cs != null){
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    
    public void update(UserDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null; //Callablestatement es para stock procedures
        try {
            cs = con.prepareStatement(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getPassword());
            cs.setInt(3, dto.getEntidad().getId());
            cs.executeUpdate();
        } finally {
            if(cs != null){
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void delete(UserDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = con.prepareStatement(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getId());
            cs.executeUpdate();
        } finally {
            if(cs != null){
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public UserDTO read(UserDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getPassword());
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (UserDTO) resultados.get(0);
            }else{
                return null;
            }
        } finally {
            if(rs != null){
                rs.close();
            }
            if(cs != null){
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public List readAll() throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ_ALL);
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return resultados;
            }else{
                return null;
            }
        } finally {
            if(rs != null){
                rs.close();
            }
            if(cs != null){
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException{
        List resultados = new ArrayList();
        while (rs.next()) {            
            UserDTO dto = new UserDTO();
            dto.getEntidad().setId(rs.getInt("id"));
            dto.getEntidad().setName(rs.getString("name"));
            dto.getEntidad().setPassword(rs.getString("password"));
            resultados.add(dto);
        }
        return resultados;
    }
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        UserDTO dto = new UserDTO();
        User entidad = new User();
        entidad.setId(1);
        dto.setEntidad(entidad);
        try {
            
            dto = dao.read(dto);
            System.out.println(dto.getEntidad().getName());
            System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

