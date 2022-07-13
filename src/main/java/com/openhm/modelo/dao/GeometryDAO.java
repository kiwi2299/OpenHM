/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.modelo.dao;


import com.openhm.modelo.dto.GeometryDTO;
import com.openhm.modelo.entidades.Geometry;
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

public class GeometryDAO {
//    private static final String SQL_INSERT="{call spInsertarCategoria(?,?)}";
//    private static final String SQL_UPDATE="{call spActualizarCategoria(?,?,?)}";
//    private static final String SQL_DELETE="{call spEliminarCategoria(?)}";
//    private static final String SQL_READ="{call spSeleccionarCategoria(?)}";
//    private static final String SQL_READ_ALL="{call spSeleccionarTodo()}";
    
    private static final String SQL_INSERT="insert into pruebagis (name, geom) values(?,?)";
    private static final String SQL_UPDATE="update pruebagis set name = ?, geom = ? where id = ?";
    private static final String SQL_DELETE="delete from pruebagis where id = ?";
    private static final String SQL_READ="select * from pruebagis where id = ?";
    private static final String SQL_READ_ALL="select * from pruebagis";
//private static final String SQL_UPDATE = "update Evento set nombreEvento = ?, sede = ?, duracion= ?, fechaInicio = ?, fechaTermino = ? where  IdEvento = ?";
//    private static final String SQL_DELETE = "delete from Evento where idEvento = ?";
//    private static final String SQL_SELECT_ALL = "select * from Evento";
//    private static final String SQL_SELECT = "select * from Evento where idEvento = ?";
    private Connection con;
    public Connection ObtenerConexion(){
       String usr = "postgres";
       String pwd = "postgres";
       String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        
//         String usr = "ecearivvtixipv";
//        String pwd = "76a9b556592cf93833352d30ca2a94228441d0f80f76a08736a66db72c397f28";
//        String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://ec2-3-220-98-137.compute-1.amazonaws.com:5432/dfdfl4fv7smte5";
        //?sslmode=required
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,usr,pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeometryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void create(GeometryDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = con.prepareStatement(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getGeom());
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
    
    
    public void update(GeometryDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null; //Callablestatement es para stock procedures
        try {
            cs = con.prepareStatement(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getName());
            cs.setString(2, dto.getEntidad().getGeom());
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
    
    public void delete(GeometryDTO dto) throws SQLException{
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
    
    public GeometryDTO read(GeometryDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ);
            cs.setInt(1, dto.getEntidad().getId());
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (GeometryDTO) resultados.get(0);
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
            GeometryDTO dto = new GeometryDTO();
            dto.getEntidad().setId(rs.getInt("id"));
            dto.getEntidad().setName(rs.getString("name"));
            dto.getEntidad().setGeom((PGgeometry)rs.getObject("geom"));
            resultados.add(dto);
        }
        return resultados;
    }
    
    public static void main(String[] args) {
        GeometryDAO dao = new GeometryDAO();
        GeometryDTO dto = new GeometryDTO();
        Geometry entidad = new Geometry();
        entidad.setId(0);
        dto.setEntidad(entidad);
        try {
            
            dto = dao.read(dto);
            System.out.println(dto.getEntidad().getGeom());
            System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(GeometryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

