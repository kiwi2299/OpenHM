/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.controlador;

import com.openhm.modelo.dao.MapaDAO;
import com.openhm.modelo.dao.UsuarioDAO;
import com.openhm.modelo.dto.MapaDTO;
import com.openhm.modelo.dto.UsuarioDTO;
import com.openhm.modelo.entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;

/**
 *
 * @author Usuario
 */
public class MapaControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "get":
                        getMapa(request, response);
                        break;
                    case "crear":
                        crear(request, response);
                        break;
                    case "modificar":
                        modificar(request, response);
                    case "borrar":
                        borrar(request, response);
                    
                    default:
                        response.sendRedirect("menuCrearUsuario.jsp");
                }
            } else {
                response.sendRedirect("index.html");
            }
        } catch (IOException e) {
            
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

    private void crear(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        UsuarioDTO dto = new UsuarioDTO();
//        UsuarioDAO dao = new UsuarioDAO();
//        dto.getEntidad().setName(request.getParameter("userName"));
//        dto.getEntidad().setPassword(request.getParameter("userPass"));
//        dto.getEntidad().setEmail(request.getParameter("userEmail"));
        HttpSession sesion = request.getSession();
        String q = request.getParameter("geometry");
        
        String[] se = split(q,',');
        ArrayList<String> jn = new ArrayList<>();
        String aux = "";
        for (int i = 0; i < se.length; i++) {
            //System.out.println(se[i-1]);  
            aux += se[i]+" "; 
            if(i%2 == 1){
                jn.add(aux);
                aux = "";
            }       
        }
       
        String geo = join(jn,',');
        System.out.println(geo);
        String type = request.getParameter("type");
         String map = "";
        if(type.equals("Point")){
             map = "GEOMETRYCOLLECTION("+type+"("+geo+"))";
        }else{
             map = "GEOMETRYCOLLECTION("+type+"(("+geo+")))";
        }
            
        
        System.out.println(map);
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setMap(map);
        mdto.getEntidad().setName(request.getParameter("name"));
        try {
            mdao.create(mdto);
            List listaMapas = mdao.readAll();
            sesion.setAttribute("size",listaMapas.size());
            
            sesion.setAttribute("listaMapas",listaMapas);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect("display.jsp");
        }
    }

    private void modificar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        dto.getEntidad().setId(Integer.parseInt(request.getParameter("userId")));
        try {
            dto = dao.read(dto);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            request.setAttribute("dto",dto);
            
            response.sendRedirect("menuCrearUsuario.jsp");
        }
        
        
    }

    private void borrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        dto.getEntidad().setId(Integer.parseInt(request.getParameter("userId")));
        try {
            dao.delete(dto);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            request.setAttribute("msj","Usuario borrado");
            response.sendRedirect("menuCrearUsuario.jsp");
        }
    }

    private void getMapa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MapaDTO dto = new MapaDTO();
        MapaDAO dao = new MapaDAO();
        dto.getEntidad().setId(Integer.parseInt(request.getParameter("mapaId")));
        try {
            dto = dao.read(dto);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            request.setAttribute("mapaGeoJson",dto.getEntidad().getMap());
            response.sendRedirect("index.jsp");
        }
    }

}
