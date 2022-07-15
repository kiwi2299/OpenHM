package com.openhm.controlador;


import com.openhm.modelo.dao.UserDAO;
import com.openhm.modelo.dto.UserDTO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String handler(Model model, HttpServletRequest request) {
        LoginController lg = new LoginController();
        HttpSession sesion = request.getSession();
        model.addAttribute("page", "home");
        if (sesion.getAttribute("user") == null) {

            model.addAttribute("menu", "index.jsp");

        } else{
            //UserDTO adto = (UserDTO) sesion.getAttribute("user");
             model.addAttribute("menu", "display.jsp");
//            PersonalDTO pdto = new PersonalDTO();
//            pdto.setEntidad(adto.getEntidad().getIdPersonal());
//            if(pdto.getEntidad().getIdPuesto().getNombrePuesto().equals("Administrador")){
//                model.addAttribute("menu", "menuAdmin.jsp");
//            }else if(pdto.getEntidad().getIdPuesto().getNombrePuesto().equals("Cocinero")){
//                 model.addAttribute("menu", "menuCocinero.jsp");
//            }else if(pdto.getEntidad().getIdPuesto().getNombrePuesto().equals("Mesero")){
//                 model.addAttribute("menu", "menuMesero.jsp");
//            }
            
        }

        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, RedirectAttributes atributos) {
        UserDTO dto = new UserDTO();
        dto.getEntidad().setName(request.getParameter("user"));
        dto.getEntidad().setPassword(request.getParameter("pass"));
        UserDAO dao = new UserDAO();
        try {
            dto = dao.read(dto);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (dto != null) {
            HttpSession sesion = request.getSession(true);
            sesion.setAttribute("user", dto);
        } else {
            atributos.addFlashAttribute("msg", "Usuario o contraseña incorrectos");
        }
        return "redirect:/";
    }
    
    @RequestMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession sesion){
        if(sesion.getAttribute("user") != null){
            sesion.invalidate();
        }
        
        return "redirect:/";
    }
}
