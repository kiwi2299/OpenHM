package com.openhm.controlador;

import com.openhm.modelo.dao.MapaDAO;
import com.openhm.modelo.dao.UsuarioDAO;
import com.openhm.modelo.dto.MapaDTO;
import com.openhm.modelo.dto.UsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;


/**
 *
 * @author kiwir
 */
public class LoginControlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        cerrarSession(request, response);
                        break;
                    case "verMapa":
                        verMapa(request, response);
                        break;
                    case "buscar":
                        buscar(request, response);
                        break;
                    case "ver":
                        ver(request, response);
                        break;
                    default:
                        response.sendRedirect("index.html");
                }
            } else {
                response.sendRedirect("index.html");
            }
        } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
                
                
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void verificar(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession  sesion = request.getSession();;
        MapaDAO mdao = new MapaDAO();
        //MapaDTO mdto = new MapaDTO(); 
        //mdto.getEntidad().setYear(1888);
        UsuarioDAO dao;
        UsuarioDTO dto;
        dto = this.obtenerUsuario(request);
        dao = new UsuarioDAO();
        
        
            dto = dao.read(dto);
            
            if (dto != null) {
                System.out.println(dto.getEntidad().getTipo());
                
               /* List listaMapas = mdao.readYear(mdto);
                if(!listaMapas.isEmpty()){
                    String geojsonString = geojson(listaMapas);
                    sesion.setAttribute("geojsonString",geojsonString);
                    sesion.setAttribute("size",listaMapas.size());

                    sesion.setAttribute("listaMapas",listaMapas);
                }*/
                List y = mdao.years();
                List geojsonList = new ArrayList();
                for (int i = 0; i < y.size(); i++) {
                    MapaDTO mdto = new MapaDTO();
                    int year = (int) y.get(i);
                    //System.out.println(year);
                    mdto.getEntidad().setYear(year);
                    List listaMapas = mdao.readYear(mdto);
                    
                    if(listaMapas != null){
                        System.out.println("año "+year+" "+listaMapas.size());
                        String geojson = geojson(listaMapas);
                        mdto.getEntidad().setMap(geojson);
                        //System.out.println(mdto);
                        geojsonList.add(mdto);
                    }
                    
                }
                
//                for (int i = 0; i < geojsonList.size(); i++) {
//                    System.out.println(geojsonList.get(i));
//                
//                }


                sesion.setAttribute("geojsonList",geojsonList);
                //System.out.println(geojsonString);
                
                sesion.setAttribute("dto", dto);
                //sesion.setAttribute("msj", null);
                //this.getServletConfig().getServletContext().getRequestDispatcher("/views/display.jsp").forward(request, response);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/display.jsp");
                dispatcher.forward(request, response);
                //response.sendRedirect("WEB-INF/display.jsp");
            }else{
                
                sesion.setAttribute("dto", null);
                sesion.setAttribute("msj", null);
                response.sendRedirect("index.html?error=1");
            }
        
        
            
        
            
    }

    private void cerrarSession(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession sesion = request.getSession();
        sesion.setAttribute("dto", null);
        sesion.invalidate();
        response.sendRedirect("index.html");
        
    }

    private UsuarioDTO obtenerUsuario(HttpServletRequest request) {
        UsuarioDTO dto = new UsuarioDTO();
        
        dto.getEntidad().setName(request.getParameter("name"));
        dto.getEntidad().setPassword(request.getParameter("password"));
        
        return dto;
    }
    
    //generar string geojson con feature collection
    private String geojson(List<MapaDTO> listaMapas){
        // 
        String geojsonString = "{'type':'FeatureCollection','features':"
                + "[";
        for (MapaDTO mapa : listaMapas) {
            geojsonString += "{'type':'Feature','geometry':"+mapa.getEntidad().getMap()+","
                    + "'id':"+mapa.getEntidad().getId()+","
                    + "'properties':{"
                        + "\"MAP_ID\":\""+mapa.getEntidad().getId()+"\","
                        + "'COUNTRY_NAME':'"+mapa.getEntidad().getName()+"',"
                        + "'DESCRIPTION':'"+mapa.getEntidad().getDescription()+"',"
                        + "'SOURCE':'"+mapa.getEntidad().getSource()+"',"
                        + "'YEAR':'"+mapa.getEntidad().getYear()+"'"
                        + "}"
                    + "},";
        }
        geojsonString += "]}";
        
        return geojsonString;
    }

    private void verMapa(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession  sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        List y = mdao.years();
                List geojsonList = new ArrayList();
                for (int i = 0; i < y.size(); i++) {
                    MapaDTO mdto = new MapaDTO();
                    int year = (int) y.get(i);
                    //System.out.println(year);
                    mdto.getEntidad().setYear(year);
                    List listaMapas = mdao.readYear(mdto);
                    
                    if(listaMapas != null){
                        System.out.println("año "+year+" "+listaMapas.size());
                        String geojson = geojson(listaMapas);
                        mdto.getEntidad().setMap(geojson);
                        //System.out.println(mdto);
                        geojsonList.add(mdto);
                    }
                    
                }

        sesion.setAttribute("geojsonList",geojsonList);
        sesion.setAttribute("msj",null);
        //System.out.println(geojsonString);
        sesion.setAttribute("dto", null);        
        //this.getServletConfig().getServletContext().getRequestDispatcher("/views/display.jsp").forward(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/display.jsp");
        dispatcher.forward(request, response);
    }
    
    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession  sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        
        try {
            String search = request.getParameter("search");
            search = search.trim();
            List listaMapas = mdao.search(search);
            sesion.setAttribute("listaMapas",listaMapas);
            sesion.setAttribute("search",search);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/busqueda.jsp");
                dispatcher.forward(request, response);
        }
    }
    
    private void ver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        HttpSession sesion = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        mdto.getEntidad().setId(id);
        
        
        try {
            mdto = mdao.read(mdto);
            List geojsonList = new ArrayList();
            geojsonList.add(mdto);
            String geojson = geojson(geojsonList);
           // System.out.println(geojson);
            sesion.setAttribute("geojson",geojson);
            sesion.setAttribute("mdto",mdto);
            //System.out.println(geojsonString);
            //sesion.setAttribute("geojsonString",geojsonString);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view.jsp");
            dispatcher.forward(request, response);
        }
        
    }

    
}
