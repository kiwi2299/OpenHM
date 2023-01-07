package com.openhm.controlador;

import com.openhm.modelo.dao.MapaDAO;
import com.openhm.modelo.dao.UsuarioDAO;
import com.openhm.modelo.dto.MapaDTO;
import com.openhm.modelo.dto.UsuarioDTO;
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
 * @author kiwir
 */
public class AdminControlador extends HttpServlet {

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
        HttpSession sesion = request.getSession();
        
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("dto");
        if(dto==null){
            
            response.sendRedirect("index.html?error=2");
        }else
            try {
            if (accion != null) {
                switch (accion) {
                    case "validar":
                        validar(request, response);
                        break;
                    case "aceptar":
                        aceptar(request, response);
                        break;
                    case "rechazar":
                        rechazar(request, response);
                        break;
                    case "verMapas":
                        verMapas(request, response);
                        break;
                    case "menu":
                        menu(request, response);
                        break;
                    case "borrar":
                        borrar(request, response);
                        break;
                    case "solBorrar":
                        solBorrar(request, response);
                        break;
                    case "activar":
                        activar(request, response);
                        break;
                    case "desactivar":
                        desactivar(request, response);
                        break;
                    default:
                        response.sendRedirect("index.html");
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
    
    private void menu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession sesion = request.getSession();
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("dto");
                
        if(dto.getEntidad().getTipo().equals("admin")){
            try {
                List listaUsuarios = dao.readAll();
                //sesion.setAttribute("msj", null);
                request.setAttribute("msj_admin", null); 
                sesion.setAttribute("listaUsuarios",listaUsuarios);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/menuAdmin.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(AdminControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/display.jsp");
            dispatcher.forward(request, response);
        }
        
    }

    private void validar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
           // mdto = mdao.read(mdto);
            mdto.getEntidad().setView("En Proceso");
            mdao.updateValidar(mdto);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("msj_us","Mapa en Proceso de Validación");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Usuario?accion=menu");
            dispatcher.forward(request, response);
        } 
    }

    private void aceptar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("user");
        UsuarioDAO dao = new UsuarioDAO();
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            
            mdto.getEntidad().setView("Visible");
            mdao.updateValidar(mdto);
            List listaMapas = mdao.readAllUser(dto);
            sesion.setAttribute("listaMapas",listaMapas);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sesion.setAttribute("msj_admin", "Mapa aceptado");    
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/mapasUsuario.jsp");
            dispatcher.forward(request, response);
        } 
    }

    private void rechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("user");
        UsuarioDAO dao = new UsuarioDAO();
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            
            mdto.getEntidad().setView("No visible");
            mdao.updateValidar(mdto);
            List listaMapas = mdao.readAllUser(dto);
            sesion.setAttribute("listaMapas",listaMapas);
            
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sesion.setAttribute("msj_admin", "Mapa rechazado");    
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/mapasUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void verMapas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        UsuarioDTO dto = new UsuarioDTO();
        int id = Integer.parseInt(request.getParameter("id"));
        dto.getEntidad().setId(id);
        dto.getEntidad().setName(request.getParameter("name"));
        try {
            //dto pasar nombre de usuario también
            List listaMapas = mdao.readAllUser(dto);
            //sesion.setAttribute("msj", null);
            sesion.setAttribute("msj_admin", null); 
            sesion.setAttribute("user",dto);
            sesion.setAttribute("listaMapas",listaMapas);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/mapasUsuario.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void solBorrar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {  
        HttpSession sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        String msj = "";
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("dto");
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            mdto = mdao.read(mdto);
            if(mdto.getEntidad().getView().equals("Visible")){
                mdto.getEntidad().setView("Eliminar");
                mdao.updateValidar(mdto);
                List listaMapas = mdao.readAllUser(dto);
                sesion.setAttribute("listaMapas",listaMapas);
                msj = "Mapa en proceso de eliminación";
            }else if(mdto.getEntidad().getView().equals("Eliminar")){
                mdto.getEntidad().setView("Visible");
                mdao.updateValidar(mdto);
                List listaMapas = mdao.readAllUser(dto);
                sesion.setAttribute("listaMapas",listaMapas);
                msj = "Eliminación cancelada";
            }
            //mdao.delete(mdto);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("msj_us",msj);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Usuario?accion=menu");
            dispatcher.forward(request, response);
        }
    }
    
    private void borrar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {  
        HttpSession sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        String msj = "";
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("user");
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            mdao.delete(mdto);
            List listaMapas = mdao.readAllUser(dto);
            sesion.setAttribute("listaMapas",listaMapas);
            msj = "Mapa eliminado";
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("msj_admin",msj);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/mapasUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }
    private void desactivar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {  
        HttpSession sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        String msj = "";
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("user");
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            mdto.getEntidad().setView("No visible");
            mdao.updateValidar(mdto);
            List listaMapas = mdao.readAllUser(dto);
            sesion.setAttribute("listaMapas",listaMapas);
            msj = "Mapa retirado";
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("msj_admin",msj);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/mapasUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void activar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {  
        HttpSession sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        String msj = "";
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("user");
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            mdto.getEntidad().setView("Visible");
            mdao.updateValidar(mdto);
            List listaMapas = mdao.readAllUser(dto);
            sesion.setAttribute("listaMapas",listaMapas);
            msj = "Mapa activado";
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("msj_admin",msj);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/mapasUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }
}
