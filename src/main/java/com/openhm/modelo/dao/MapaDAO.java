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
    private static final String SQL_INSERT_LOAD="insert into mapa (name,user_id,year,view,map,description,source,insert_date,update_date) values(?,?,?,?,ST_Transform(ST_SetSRID(ST_GeomFromText(?),?),3857),?,?,CURRENT_DATE,NULL)";
    private static final String SQL_UPDATE="update mapa SET name=?, user_id=?, year=?, view=?, map=ST_GeomFromText(?), description=?, source=?, update_date=CURRENT_DATE where id=?";
    private static final String SQL_UPDATE_VALIDAR="update mapa SET view=? where id=?";
    private static final String SQL_DELETE="delete from mapa where id = ?";
    private static final String SQL_READ="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date, update_date from mapa where id = ?";
    private static final String SQL_READ_ALL="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa";
    private static final String SQL_READ_YEAR="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa where (year = ? AND view = 'Visible') or (year = ? and view = 'Eliminar')";
    private static final String SQL_READ_YEAR_USER="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa where year = ? and user_id = ?";
    private static final String SQL_READ_ALL_USER="select id, name,user_id,year,view, ST_AsGeoJson(map),description,source,insert_date from mapa where user_id = ? order by view";
    private static final String SQL_YEARS="select year from mapa group by year order by year";
    private static final String SQL_COUNT_YEAR="select count(id) from mapa where (year=? and view = 'Visible') or (year = ? and view = 'Eliminar')";
    private static final String SQL_SEARCH="select m.id, m.name,u.name as user_id,year,view, ST_AsGeoJson(map),description,source,insert_date, update_date from mapa as m inner join usuario as u on user_id = u.id where LOWER(m.name) like LOWER(?) or LOWER(description) like LOWER(?) or cast(year as character varying) like ? or LOWER(source) like LOWER(?)";
    
    

    private Connection con;
    public Connection ObtenerConexion(){       
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
    
    public void load(MapaDTO dto, int srid) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = con.prepareStatement(SQL_INSERT_LOAD);
            cs.setString(1, dto.getEntidad().getName());
            cs.setInt(2, dto.getEntidad().getUser_id());
            cs.setInt(3, dto.getEntidad().getYear());
            cs.setString(4, dto.getEntidad().getView());
            cs.setString(5, dto.getEntidad().getMap());
            cs.setInt(6, srid);
            cs.setString(7, dto.getEntidad().getDescription());
            cs.setString(8, dto.getEntidad().getSource());
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
            cs.setString(1, dto.getEntidad().getView());
            cs.setInt(2, dto.getEntidad().getId());
            
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
            cs.setInt(2, dto.getEntidad().getYear());
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
     * @return List de int
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
    
     /**
     * Obtiene la cantidad de mapas de un determinado año
     * @param year
     * @return int
     * @throws java.sql.SQLException
     */
    public int countYear(int year) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        int resultados = 0;
        
        try {
            cs = con.prepareStatement(SQL_COUNT_YEAR);
            cs.setInt(1, year);
            cs.setInt(2, year);
            rs = cs.executeQuery();
            while (rs.next()) {                          
                resultados = rs.getInt("count");
            }
             
            if (resultados != 0) {
                return resultados;
            }else{
                return 0;
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
     * Busca el parámetro 
     * @param search
     * @return List
     * @throws java.sql.SQLException
     */
    public List search(String search) throws SQLException{
        ObtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs =null;
        
       
        try {
            cs = con.prepareStatement(SQL_SEARCH);
            cs.setString(1, "%"+search+"%");
            cs.setString(2, "%"+search+"%");
            cs.setString(3, "%"+search+"%");
            cs.setString(4, "%"+search+"%");
            rs = cs.executeQuery();
            List resultados = obtenerResultadosBusqueda(rs);
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
    
    private List obtenerResultadosBusqueda(ResultSet rs) throws SQLException{
        List resultados = new ArrayList();
        while (rs.next()) {            
            MapaDTO dto = new MapaDTO();
            dto.getEntidad().setId(rs.getInt("id"));
            dto.getEntidad().setName(rs.getString("name"));
            dto.getEntidad().setUserName(rs.getString("user_id"));
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
        MapaDTO dto = new MapaDTO();
        dto.getEntidad().setId(66);
       // Mapa entidad = new Mapa();
        //entidad.setYear(1888);
        //dto.setEntidad(entidad);
        try {
            
            dto = dao.read(dto);
            System.out.println(dto);
            //System.out.println(dao.search("me"));
        } catch (SQLException ex) {
            Logger.getLogger(MapaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

