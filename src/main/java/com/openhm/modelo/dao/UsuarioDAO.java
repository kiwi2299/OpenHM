/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.modelo.dao;


import com.openhm.modelo.dto.UsuarioDTO;
import com.openhm.modelo.entidades.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAO {
    
    private static final String SQL_INSERT="insert into usuario (name, password, email) values(?,crypt(?,gen_salt('bf')),?)";
    private static final String SQL_UPDATE="update usuario set name = ?, password = ?, email = ? where id = ?";
    private static final String SQL_DELETE="delete from usuario where id = ?";
    private static final String SQL_READ="select * from usuario where name = ? and password = crypt(?,password)";
    private static final String SQL_READ_ALL="select * from usuario";

    private Connection con;
    public Connection ObtenerConexion(){
//       String usr = "postgres";
//       String pwd = "postgres";
//       String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://localhost:5432/postgres";
        
//        String usr = "postgres";
//       String pwd = "adminadmin";
//       String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://tt2-2021-b023.ci6bwbdlosva.us-west-1.rds.amazonaws.com:5432/openhm";
        //heroku
        String usr = "ctkofwkznexjio";
       String pwd = "5b65ec04731aa5e62263934fc82cf236f9f2f6be3ffe5e73d7bfcacb9ed2cead";
       String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://ec2-3-219-19-205.compute-1.amazonaws.com:5432/db924bd23if0r2";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,usr,pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void create(UsuarioDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = con.prepareStatement(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getPassword());
            cs.setString(3, dto.getEntidad().getEmail());
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
    
    
    public void update(UsuarioDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null; //Callablestatement es para stock procedures
        try {
            cs = con.prepareStatement(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getPassword());
            cs.setString(3, dto.getEntidad().getEmail());
            cs.setInt(4, dto.getEntidad().getId());
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
    
    public void delete(UsuarioDTO dto) throws SQLException{
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
    
    public UsuarioDTO read(UsuarioDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getPassword());
            //cs.setString(3, dto.getEntidad().getEmail());
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (UsuarioDTO) resultados.get(0);
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
            UsuarioDTO dto = new UsuarioDTO();
            dto.getEntidad().setId(rs.getInt("id"));
            dto.getEntidad().setName(rs.getString("name"));
            dto.getEntidad().setPassword(rs.getString("password"));
            dto.getEntidad().setEmail(rs.getString("email"));
            resultados.add(dto);
        }
        return resultados;
    }
    
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        Usuario entidad = new Usuario();
        
        entidad.setName("kiwir");
        entidad.setPassword("kiwir");
        entidad.setEmail("rafakiwi99@gmail.com");
        //entidad.setId(1);
        dto.setEntidad(entidad);
        
        try {
           // dao.update(dto);
            dao.create(dto);
            System.out.println(dao.read(dto));
            if(dto != null){
                
            }
            
            //System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

