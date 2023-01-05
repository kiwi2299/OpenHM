package com.openhm.controlador;

import com.openhm.modelo.dao.MapaDAO;
import com.openhm.modelo.dao.UsuarioDAO;
import com.openhm.modelo.dto.MapaDTO;
import com.openhm.modelo.dto.UsuarioDTO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;

/**
 *
 * @author kiwir
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
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        HttpSession sesion = request.getSession();
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("dto");
        if(dto == null){
            sesion.setAttribute("msj", "Sesión terminada");
            response.sendRedirect("index.html?error=2");
        }else{
            try {
                if (accion != null) {
                    switch (accion) {
                        case "getMapas":
                            getMapas(request, response);
                            break;
                        case "display":
                            display(request, response);
                            break;
                        case "getIndex":
                            getMapasIndex(request, response);
                            break;
                        case "mapa":
                            getMapa(request, response);
                            break;
                        case "crear":
                            crear(request, response);
                            break;
                        case "load":
                            load(request, response);
                            break;
                        case "geojson":
                            geojsonFile();
                            break;
                        case "editar":
                            editar(request, response);
                            break;
                        case "borrar":
                            borrar(request, response);
                            break;
                        case "draw":
                            draw2(request, response);
                            break;
                        case "verMapasUs":
                            verMapasUs(request, response);
                            break;
                        case "loader":
                            loader(request, response);
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

    private void crear(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        UsuarioDTO dto = new UsuarioDTO();
//        UsuarioDAO dao = new UsuarioDAO();
//        dto.getEntidad().setName(request.getParameter("userName"));
//        dto.getEntidad().setPassword(request.getParameter("userPass"));
//        dto.getEntidad().setEmail(request.getParameter("userEmail"));
        
        HttpSession sesion = request.getSession();
        String q = request.getParameter("geometry");
        if(q == null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Usuario?accion=menu");
                    dispatcher.forward(request, response);
        }else{
        
        
        q = q.replace("[", "");
        q = q.replace("]", "");
        String type = request.getParameter("type");
        String map = "";
        if(type.equals("MultiPolygon")){
            String[] div = split(q,'|');
            String parent = "";
            for (int i = 0; i < div.length; i++) {
                
                if(i>0){
                    parent += ",("+div[i]+")";
                }else{
                    parent += "("+div[i]+")";
                }
                   
            }
            System.out.println(parent);
            String[] se = split(parent,',');
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
            map = type+"(("+geo+"))";
        }else{
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
            //System.out.println(geo);

             
            if(type.equals("Point") || type.equals("LineString")){
                 map = type+"("+geo+")";
            }else if(type.equals("Polygon")){
                 map = type+"(("+geo+"))";
            }else if(type.equals("Circle")){
                System.out.println("todo");
            }
        }
            
        
        System.out.println(map);
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("dto");
        //System.out.println(dto.getEntidad().getName());
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setMap(map);
        mdto.getEntidad().setName(request.getParameter("name"));
        //System.out.println(request.getParameter("name"));
        mdto.getEntidad().setDescription(request.getParameter("desc"));
        mdto.getEntidad().setView("No visible");
        //mdto.getEntidad().setSource(request.getParameter("src"));
        
        
        mdto.getEntidad().setUser_id(dto.getEntidad().getId());
        if(request.getParameter("src").equals("")){
            mdto.getEntidad().setSource("https://www.davidrumsey.com/luna/servlet/detail/RUMSEY~8~1~201946~3000983:Map-Of-The-World-");
        }else{
            mdto.getEntidad().setSource(request.getParameter("src"));
        }
        if(request.getParameter("year").equals("")){
            mdto.getEntidad().setYear(1900);
        }else{
            String st = request.getParameter("year");
            st = st.trim();
            st = st.replaceAll("\\s+","");
            mdto.getEntidad().setYear(Integer.parseInt(st));
        }
        String mensaje = "";
        if(request.getParameter("id").equals("")){
            try {
                mdao.create(mdto);
                mensaje = "Mapa registrado";
            } catch (SQLException ex) {
                Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = ex.getMessage();
            } finally {
                request.setAttribute("msj_us",mensaje);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Usuario?accion=menu");
                dispatcher.forward(request, response);
            }
        }else{//update
            if(dto.getEntidad().getTipo().equals("admin")){
                System.out.println("admin aqio");
                mdto.getEntidad().setUser_id(Integer.parseInt(request.getParameter("user_id")));
                try {
                    mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
                    mdto.getEntidad().setView("Visible");
                    mdao.update(mdto);
                    mensaje = "Mapa actualizado";
                } catch (SQLException ex) {
                    Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
                    mensaje = ex.getMessage();
                } finally{
                    request.setAttribute("msj",mensaje);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Mapa?accion=display");
                    dispatcher.forward(request, response);
                }
            }else{
                try {
                
                    mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
                    MapaDTO newdto = mdto;
                    newdto = mdao.read(newdto);
                    if(newdto.getEntidad().getView().equals("Visible") || newdto.getEntidad().getView().equals("Eliminar")){
                        mdao.create(mdto);
                        mensaje = "Mapa registrado";
                    }else{
                        mdao.update(mdto);
                        mensaje = "Mapa actualizado";
                    }
                        
                } catch (SQLException ex) {
                    mensaje = ex.getMessage();
                } finally {
                    request.setAttribute("msj_us",mensaje);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Usuario?accion=menu");
                    dispatcher.forward(request, response);
                }
            }
            
        }
        }
        
    }
    
    private void load(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        UsuarioDTO dto = new UsuarioDTO();
//        UsuarioDAO dao = new UsuarioDAO();
//        dto.getEntidad().setName(request.getParameter("userName"));
//        dto.getEntidad().setPassword(request.getParameter("userPass"));
//        dto.getEntidad().setEmail(request.getParameter("userEmail"));
        
        HttpSession sesion = request.getSession();
        String q = request.getParameter("geometry");
        String type = request.getParameter("type");
        String map = "";
         System.out.println(q);
        if(type.equals("MultiPolygon")){
            //quitar corchetes
            int parse = 0;
            char[] newc = new char[q.length()];
            for (int i = 0; i < q.length(); i++) {
                newc[i] = q.charAt(i);
                //System.out.println(parse);
                if(newc[i] == '['){
                    parse++;
                    
                }else if(newc[i] == ']'){
                    
                    parse--;
                }
                if(parse == 0 && i>0){
                    newc[i] = '|';
                    System.out.println("Posicion "+i+" va |");
                }
                        
            }
            String b = new String(newc);
            System.out.println(b);
            /*String[] div = split(b,'|');
            for(String aux: div){
                System.out.println("hello");
                System.out.println(aux);
            }*/
            b = b.replace("[", "");
            b = b.replace("]", "");
            
            //preparar geom
            String[] div = split(b,'|');
            String parent = "";
            for (int i = 0; i < div.length; i++) {
                
                if(i>0){
                    parent += ",("+div[i]+")";
                }else{
                    parent += "("+div[i]+")";
                }
                   
            }
            System.out.println(parent);
            String[] se = split(parent,',');
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
            map = type+"(("+geo+"))";
        }else{
            q = q.replace("[", "");
            q = q.replace("]", "");
            //System.out.println(geo);
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
             
            if(type.equals("Point") || type.equals("LineString")){
                 map = type+"("+geo+")";
            }else if(type.equals("Polygon")){
                 map = type+"(("+geo+"))";
            }else if(type.equals("Circle")){
                System.out.println("todo");
            }
        }
        
        
        System.out.println(map);
        
        
         
        
        
        UsuarioDTO dto = (UsuarioDTO)sesion.getAttribute("dto");
        //System.out.println(dto.getEntidad().getName());
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setMap(map);
        mdto.getEntidad().setName(request.getParameter("name"));
        //System.out.println(request.getParameter("name"));
        mdto.getEntidad().setDescription(request.getParameter("desc"));
        mdto.getEntidad().setView("No visible");
        mdto.getEntidad().setSource(request.getParameter("src"));
        mdto.getEntidad().setUser_id(dto.getEntidad().getId());
        if(request.getParameter("year").equals("")){
            mdto.getEntidad().setYear(2023);
        }else{
            String st = request.getParameter("year");
            st = st.trim();
            st = st.replaceAll("\\s+","");
            mdto.getEntidad().setYear(Integer.parseInt(st));
        }
        String mensaje = "";
        try {
            mdao.load(mdto,4326);
            mensaje = "Mapa registrado";
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = ex.getMessage();
        } finally {
            request.setAttribute("msj_us",mensaje);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Mapa?accion=loader");
            dispatcher.forward(request, response);
        } 
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/edit.jsp");
            dispatcher.forward(request, response);
        }
        
    }

    private void borrar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {       
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO();
        mdto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
        try {
            mdao.delete(mdto);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("msj_us","Mapa borrado");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Usuario?accion=menu");
            dispatcher.forward(request, response);
        }
    }

    private void getMapa(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
    
    private void getMapas(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       
        MapaDAO mdao = new MapaDAO();
        //MapaDTO mdto = new MapaDTO();
        HttpSession sesion = request.getSession();
        try {
            List y = mdao.years();
            List geojsonList = new ArrayList();
            for (int i = 0; i < y.size(); i++) {
                MapaDTO mdto = new MapaDTO();
                int year = (int) y.get(i);
                mdto.getEntidad().setYear(year);
                List listaMapas = mdao.readYear(mdto);
                String geojson = geojson(listaMapas);
                mdto.getEntidad().setMap(geojson);
                geojsonList.add(mdto);
            }
            for (int i = 0; i < geojsonList.size(); i++) {
                System.out.println(geojsonList.get(i));
                
            }
            
            sesion.setAttribute("geojsonList",geojsonList);
            //System.out.println(geojsonString);
            //sesion.setAttribute("geojsonString",geojsonString);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/display.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void getMapasIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
        MapaDAO mdao = new MapaDAO();
        //MapaDTO mdto = new MapaDTO();
       
        String json = "{\"overlays\":[";
        int count = 1;        
        try {
            List y = mdao.years();
            
            for (int i = 0; i < y.size(); i++) {
                MapaDTO mdto = new MapaDTO();
                int year = (int) y.get(i);
                mdto.getEntidad().setYear(year);
                List listaMapas = mdao.readYear(mdto);
                String geojson = geojson(listaMapas);
                json+=" {\"title\":\""+year+"\",";
                if(count < y.size()){
                    json += "\"geojson\":"+geojson+"},";
                }else{
                    json += "\"geojson\":"+geojson+"}";
                }
                count++;
            }
            
            
          json += "]}";
        
        
            
           
           
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            //System.out.println(geojsonString);
            //sesion.setAttribute("geojsonString",geojsonString);
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    //generar string geojson con feature collection
     private String geojson(List<MapaDTO> listaMapas){
        // 
        int size = listaMapas.size();
        int i = 1;
        String geojsonString = "{\"type\":\"FeatureCollection\",\"features\":"
                + "[";
        for (MapaDTO mapa : listaMapas) {
            geojsonString += "{\"type\":\"Feature\",\"geometry\":"+mapa.getEntidad().getMap()+","
                    + "\"id\":"+mapa.getEntidad().getId()+","
                    + "\"properties\":{"
                        + "\"MAP_ID\":\""+mapa.getEntidad().getId()+"\","
                        + "\"COUNTRY_NAME\":\""+mapa.getEntidad().getName()+"\","
                        + "\"DESCRIPTION\":\""+mapa.getEntidad().getDescription()+"\","
                        + "\"SOURCE\":\""+mapa.getEntidad().getSource()+"\","
                        + "\"YEAR\":\""+mapa.getEntidad().getYear()+"\""
                        + "}";
            if(i < size){
                geojsonString += "},";
            }else{
                geojsonString += "}";
            }
            
            i++;
        }
        geojsonString += "]}";
        
        return geojsonString;
    }
    
    //Intentar crear un archivo para que ol consulte
    //no funciona
    private void geojsonFile(){
        
         String filename = "/geojson/mapa.geojson";
        
        ServletContext context = getServletContext();
        String path = this.getServletContext().getRealPath(filename);
        
        
        System.out.println(path);
        //InputStream is = context.getResourceAsStream(filename);
         try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
            
             try (FileWriter myWriter = new FileWriter(path)) {
                 myWriter.write("Contenido");
             }
            System.out.println("success");
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    private void loader(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession  sesion = request.getSession();
        sesion.setAttribute("msj_us", null);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/loader.jsp");
        dispatcher.forward(request, response);
    }
    
    private void draw2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession  sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        int year = 2023;
        String syear = request.getParameter("drawyear");
        if(syear != null){
            year = Integer.parseInt(syear);
            
        }
             
        try {
            System.out.println(year);
            System.out.println(mdao.countYear(year));
            sesion.setAttribute("listaYears",mdao.years());
            sesion.setAttribute("year",year);
            sesion.setAttribute("count",mdao.countYear(year));
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/draw2.jsp");
        dispatcher.forward(request, response);
    }

    private void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession  sesion = request.getSession();
        MapaDAO mdao = new MapaDAO();
        //MapaDTO mdto = new MapaDTO(); 
        //mdto.getEntidad().setYear(1888);
        UsuarioDAO dao;
        UsuarioDTO dto;
        dto = (UsuarioDTO) sesion.getAttribute("dto");
        dao = new UsuarioDAO();
        
        
        try {
            
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
                    if(listaMapas != null){
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
                sesion.setAttribute("msj", null);
                //this.getServletConfig().getServletContext().getRequestDispatcher("/views/display.jsp").forward(request, response);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/display.jsp");
                dispatcher.forward(request, response);
                //response.sendRedirect("WEB-INF/display.jsp");
            }else{
                
                sesion.setAttribute("dto", null);
                sesion.setAttribute("msj", null);
                response.sendRedirect("index.html?error=2");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
    }

    private void verMapasUs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession  sesion = request.getSession();;
        MapaDAO mdao = new MapaDAO();
        MapaDTO mdto = new MapaDTO(); 
        
        
        UsuarioDTO dto = new UsuarioDTO();
        int id = Integer.parseInt(request.getParameter("id"));
        dto.getEntidad().setId(id);
        mdto.getEntidad().setUser_id(id);
       
        
        
        
        try {
            
            List listaMapas = mdao.readAllUser(dto);
                //System.out.println(dto.getEntidad().getName());
                
                
                if(listaMapas != null){
                    String geojson = geojson(listaMapas);
                    mdto.getEntidad().setMap(geojson);
                    //System.out.println(mdto);
                    
                

                    sesion.setAttribute("geojsonList",geojson);
                }else{
                    String geojson = "{}";
                    sesion.setAttribute("geojsonList",geojson);
                }
                    
                
                
                    //System.out.println(year);
                    
                    
                    
                //System.out.println(geojsonString);
                //this.getServletConfig().getServletContext().getRequestDispatcher("/views/display.jsp").forward(request, response);
                
                //response.sendRedirect("WEB-INF/display.jsp");
            
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(MapaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/displayUser.jsp");
                dispatcher.forward(request, response);
        }
    }

    

}
