/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhm.controlador;

import com.openhm.modelo.entidades.Geometry;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 *
 * @author Usuario
 */
@WebServlet(name = "ServletGis", urlPatterns = {"/ServletGis"})
public class ServletGis extends HttpServlet {

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
        
         Map<String, Object> params = new HashMap<>();
        params.put("dbtype", "postgis");
        params.put("host", "localhost");
        params.put("port", 5432);
        params.put("schema", "public");
        params.put("database", "postgres");
        params.put("user", "postgres");
        params.put("passwd", "postgres");

        DataStore dataStore = DataStoreFinder.getDataStore(params);
        if (dataStore == null) {
                System.out.println("Could not connect - check parameters");
        }
        
        
        String inputTypeName = "pruebagis";
        SimpleFeatureType inputType = dataStore.getSchema(inputTypeName);
        int attributeCount = inputType.getAttributeCount();
        System.out.println(attributeCount);
        
        
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(inputTypeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = source.getFeatures();
       
       
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletGis</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletGis at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            //String paramses = null;
            List<Object> attributes = null;
            
            List<Geometry> listGeom = null;
            FeatureIterator<SimpleFeature> iterator = features.features();
            while(iterator.hasNext()){
                Geometry geom = null;
                SimpleFeature feature = iterator.next();
                attributes = feature.getAttributes();
                String id = feature.getID();
                int foo = Integer.parseInt(id);
                geom.setId(foo);
                System.out.println(id);
                out.println("<h1>" + id + "</h1>");
                
                geom.setName(attributes.get(0).toString());
                //geom.setGeom(attributes.get(1).toString());
                for (int i = 0; i < attributes.size(); i++) {
                    Object arg = attributes.get(i);
                    System.out.println(arg.toString());
                    out.println("<h1>" + arg.toString() + "</h1>");
                    //paramses += "param"+i+"='"+arg.toString()+"'&";
                }
                listGeom.add(geom);
            }
            
            //List lista = dao.readAll();
            request.setAttribute("listaGeom", listGeom);
            RequestDispatcher rd = request.getRequestDispatcher("display.jsp");
            rd.forward(request, response);
            
            //RequestDispatcher rd = request.getRequestDispatcher("pruebas.html?params='"+paramses+"'");
            //response.sendRedirect("pruebas.html?"+paramses);
            //rd.forward(request, response);
            
        }
        
        
            //request.setAttribute("listaDeCategorias", attributes);
           
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

}
