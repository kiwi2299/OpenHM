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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Usuario
 */
public class LoginControlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        cerrarSession(request, response);
                    default:
                        response.sendRedirect("index.html");
                }
            } else {
                response.sendRedirect("index.html");
            }
        } catch (Exception e) {
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);

            } catch (Exception ex) {
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

    private void verificar(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession sesion;
        UsuarioDAO dao;
        UsuarioDTO dto;
        dto = this.obtenerUsuario(request);
        dao = new UsuarioDAO();
        dto = dao.read(dto);
        //System.out.println(dto.getEntidad().getName());
        if (dto != null) {
            sesion = request.getSession();
            sesion.setAttribute("dto", dto);
            request.setAttribute("msje", "Bienvenido al sistema");
            //this.getServletConfig().getServletContext().getRequestDispatcher("/views/display.jsp").forward(request, response);
            response.sendRedirect("display.jsp");
        }else{
            request.setAttribute("msje", "Credenciales Incorrectas");
            request.getRequestDispatcher("index.html").forward(request, response);
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
        
        dto.getEntidad().setName(request.getParameter("userName"));
        dto.getEntidad().setPassword(request.getParameter("userPass"));
        
        return dto;
    }

}
