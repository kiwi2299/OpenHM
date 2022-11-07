package com.openhm.modelo.dao;

import com.openhm.modelo.dto.MapaDTO;
import com.openhm.modelo.dto.UsuarioDTO;
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

public class MapaDAO {
//
    private static final String SQL_INSERT="insert into mapa (name,user_id,year,view,map,description,source,insert_date,update_date) values(?,?,?,?,ST_GeomFromText(?),?,?,CURRENT_DATE,NULL)";
    private static final String SQL_UPDATE="update mapa SET name=?, user_id=?, year=?, view=?, map=ST_GeomFromText(?), description=?, source=?, update_date=CURRENT_DATE where id=?";
    private static final String SQL_UPDATE_VALIDAR="update mapa SET view='En Proceso' where id=?";
    private static final String SQL_DELETE="delete from mapa where id = ?";
    private static final String SQL_READ="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date, update_date from mapa where id = ?";
    private static final String SQL_READ_ALL="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa";
    private static final String SQL_READ_YEAR="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa where year = ?";
    private static final String SQL_READ_YEAR_USER="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa where year = ? and user_id = ?";
    private static final String SQL_READ_ALL_USER="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa where user_id = ?";
    private static final String SQL_YEARS="select year from mapa group by year order by year";
    

    private Connection con;
    public Connection ObtenerConexion(){       
//         String usr = "postgres";
//        String pwd = "postgres";
//        String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://localhost:5432/postgres";
        //?sslmode=required
        
        //heroku
        String usr = "ctkofwkznexjio";
       String pwd = "5b65ec04731aa5e62263934fc82cf236f9f2f6be3ffe5e73d7bfcacb9ed2cead";
       String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://ec2-3-219-19-205.compute-1.amazonaws.com:5432/db924bd23if0r2";
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
            cs.setString(4, dto.getEntidad().getView());
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
        //"update mapa SET name=?, user_id=?, year=?, view=?, map=ST_GeomFromText(?), description=?, source=?, update_date=CURRENT_DATE where id=?";
        PreparedStatement cs = null; //Callablestatement es para stock procedures
        try {
            cs = con.prepareStatement(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getName());
            cs.setInt(2, dto.getEntidad().getUser_id());
            cs.setInt(3, dto.getEntidad().getYear());
            cs.setString(4, dto.getEntidad().getView());
            cs.setString(5, dto.getEntidad().getMap());
            cs.setString(6, dto.getEntidad().getDescription());
            cs.setString(7, dto.getEntidad().getSource());
            cs.setInt(8, dto.getEntidad().getId());
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
    
    public void updateValidar(MapaDTO dto) throws SQLException{
        ObtenerConexion();
        //"update mapa SET name=?, user_id=?, year=?, view=?, map=ST_GeomFromText(?), description=?, source=?, update_date=CURRENT_DATE where id=?";
        PreparedStatement cs = null; //Callablestatement es para stock procedures
        try {
            cs = con.prepareStatement(SQL_UPDATE_VALIDAR);
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

     public List readYear(MapaDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ_YEAR);
            cs.setInt(1, dto.getEntidad().getYear());
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
     
     
     public List readYearUser(MapaDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ_YEAR_USER);
            cs.setInt(1, dto.getEntidad().getYear());
            cs.setInt(1, dto.getEntidad().getUser_id());
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
     
    public List readAllUser(UsuarioDTO dto) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        try {
            cs = con.prepareStatement(SQL_READ_ALL_USER);
            cs.setInt(1, dto.getEntidad().getId());
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
    
    /**
     * Obtiene una lista de todos los años únicos presentes en la base de datos
     * @return 
     * @throws java.sql.SQLException
     */ 
     public List years() throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        List resultados = new ArrayList();
        try {
            cs = con.prepareStatement(SQL_YEARS);
            
            rs = cs.executeQuery();
            while (rs.next()) {                          
                resultados.add(rs.getInt("year"));
            }
             
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
            dto.getEntidad().setView(rs.getString("view"));
            dto.getEntidad().setMap(rs.getString("ST_AsGeoJson"));
            dto.getEntidad().setDescription(rs.getString("description"));
            dto.getEntidad().setSource(rs.getString("source"));
            dto.getEntidad().setInsert_date(rs.getString("insert_date"));
            
            resultados.add(dto);
        }
        return resultados;
    }
    
    public static void main(String[] args) {
        MapaDAO dao = new MapaDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setId(3);
        Mapa entidad = new Mapa();
        //entidad.setYear(1888);
        //dto.setEntidad(entidad);
        try {
            
            //dto = dao.read(dto);
            //System.out.println(dto.getEntidad().getMap());
            System.out.println(dao.readAllUser(dto));
        } catch (SQLException ex) {
            Logger.getLogger(MapaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

