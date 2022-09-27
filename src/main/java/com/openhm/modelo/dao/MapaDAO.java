/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.modelo.dao;


import com.openhm.modelo.dto.MapaDTO;
import com.openhm.modelo.entidades.Mapa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgis.LineString;
import org.postgis.PGgeometry;

public class MapaDAO {

    /*TODO */
    private static final String SQL_INSERT="insert into mapa (name,user_id,year,view,map,description,source,insert_date) values(?,?,?,true,ST_GeomFromText(?),?,?,CURRENT_DATE)";
    private static final String SQL_UPDATE="update mapa set name = ?, map = ? where id = ?";
    private static final String SQL_DELETE="delete from mapa where id = ?";
    private static final String SQL_READ="select id, name,user_id,year,view, ST_AsGeoJSON(map),description,source,insert_date from mapa where id = ?";
    private static final String SQL_READ_ALL="select id, name,user_id,year,view, ST_AsGeoJSON(map),description,source,insert_date from mapa";

    private Connection con;
    public Connection ObtenerConexion(){
       String usr = "postgres";
       String pwd = "adminadmin";
       String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://tt2-2021-b023.ci6bwbdlosva.us-west-1.rds.amazonaws.com:5432/openhm";
        
//         String usr = "postgres";
//        String pwd = "postgres";
//        String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://localhost:5432/postgres";
        //?sslmode=required
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,usr,pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MapaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void create(MapaDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = con.prepareStatement(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getName());
            cs.setInt(2, dto.getEntidad().getUser_id());
            cs.setInt(3, dto.getEntidad().getYear());
            cs.setString(5, dto.getEntidad().getMap());
            cs.setString(6, dto.getEntidad().getDescription());
            cs.setString(7, dto.getEntidad().getSource());
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
    
    
    public void update(MapaDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null; //Callablestatement es para stock procedures
        try {
            cs = con.prepareStatement(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getMap());
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
    
    public void delete(MapaDTO dto) throws SQLException{
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
    
    public MapaDTO read(MapaDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ);
            cs.setInt(1, dto.getEntidad().getId());
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (MapaDTO) resultados.get(0);
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
            MapaDTO dto = new MapaDTO();
            dto.getEntidad().setId(rs.getInt("id"));
            dto.getEntidad().setName(rs.getString("name"));
            dto.getEntidad().setUser_id(rs.getInt("user_id"));
            dto.getEntidad().setYear(rs.getInt("year"));
            dto.getEntidad().setView(rs.getBoolean("view"));
            dto.getEntidad().setMap(rs.getString("ST_AsGeoJSON"));
            dto.getEntidad().setDescription(rs.getString("description"));
            dto.getEntidad().setSource(rs.getString("source"));
            dto.getEntidad().setInsert_date(rs.getString("insert_date"));
            
            resultados.add(dto);
        }
        return resultados;
    }
    
    public static void main(String[] args) {
        MapaDAO dao = new MapaDAO();
        MapaDTO dto = new MapaDTO();
        Mapa entidad = new Mapa();
        entidad.setId(2);
        dto.setEntidad(entidad);
        try {
            
            //dto = dao.read(dto);
            //System.out.println(dto.getEntidad().getMap());
            System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(MapaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

