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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usuario
 */
public class UsuarioControlador extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "crear":
                        response.sendRedirect("crearUsuario.jsp");
                        break;
                    case "insertar":
                        insertar(request, response);
                        break;
                    case "seleccionar":
                        seleccionar(request, response);
                        break;
                    case "borrar":
                        borrar(request, response);
                        break;
                    default:
                        response.sendRedirect("index.html");
                }
            } else {
                response.sendRedirect("index.html");
            }
        } catch (IOException e) {
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);

            } catch (IOException | ServletException ex) {
                System.out.println("Error" + e.getMessage());
            }
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

    private void insertar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sesion = request.getSession();
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        MapaDAO mdao = new MapaDAO();
        
        dto.getEntidad().setName(request.getParameter("name"));
        dto.getEntidad().setPassword(request.getParameter("password"));
        dto.getEntidad().setEmail(request.getParameter("email"));
        /*
        TODO user, correo repetido
        */
//        if (!request.getParameter("userId").isEmpty() || !request.getParameter("userId").equals("0")) {
//            dto.getEntidad().setId(Integer.parseInt(request.getParameter("userId")));
//            try {
//                dao.update(dto);
//            } catch (SQLException ex) {
//                Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
//            }finally{
//                request.setAttribute("msj","Usuario actualizado");
//                request.setAttribute("dto", dto);
//                response.sendRedirect("menuUsuario.jsp");
//            }
//        } else {
        try {
            dao.create(dto);
            dto = dao.read(dto);
            if (dto != null) {
                //System.out.println(dto.getEntidad().getName());
                
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
                    String geojson = geojson(listaMapas);
                    mdto.getEntidad().setMap(geojson);
                    //System.out.println(mdto);
                    geojsonList.add(mdto);
                }
                
//                for (int i = 0; i < geojsonList.size(); i++) {
//                    System.out.println(geojsonList.get(i));
//                
//                }


                sesion.setAttribute("geojsonList",geojsonList);
                sesion.setAttribute("dto", dto);
                sesion.setAttribute("msj", "Bienvenido al sistema");
                //this.getServletConfig().getServletContext().getRequestDispatcher("/views/display.jsp").forward(request, response);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/display.jsp");
                dispatcher.forward(request, response);
                
                }else{
                
                sesion.setAttribute("dto", null);
                sesion.setAttribute("msj", "Credenciales Incorrectas");
                response.sendRedirect("index.html?error=1");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            
                //System.out.println(geojsonString);
                
                
                //response.sendRedirect("WEB-INF/display.jsp");
            
            
      //  }             
    }

    private void seleccionar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        dto.getEntidad().setId(Integer.parseInt(request.getParameter("userId")));
        try {
            dto = dao.read(dto);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            request.setAttribute("dto",dto);
            
            response.sendRedirect("menuUsuario.jsp");
        }
        
        
    }

    private void borrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        dto.getEntidad().setId(Integer.parseInt(request.getParameter("userId")));
        try {
            dao.delete(dto);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            request.setAttribute("msj","Usuario borrado");
            response.sendRedirect("menuUsuario.jsp");
        }
    }
    
    private String geojson(List<MapaDTO> listaMapas){
        // 
        String geojsonString = "{'type':'FeatureCollection','features':"
                + "[";
        for (MapaDTO mapa : listaMapas) {
            geojsonString += "{'type':'Feature','geometry':"+mapa.getEntidad().getMap()+","
                    + "'id':"+mapa.getEntidad().getId()+","
                    + "'properties':{"
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
}
