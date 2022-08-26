/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.controlador;

import com.openhm.modelo.dao.UsuarioDAO;
import com.openhm.modelo.dto.UsuarioDTO;
import com.openhm.modelo.entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "menu":
                        response.sendRedirect("menuUsuario.jsp");
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
                        response.sendRedirect("menuUsuario.jsp");
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

    private void insertar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
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
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                request.setAttribute("msj","Usuario creado");
                request.setAttribute("dto", dto);
                response.sendRedirect("display.jsp");
            }
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

}
