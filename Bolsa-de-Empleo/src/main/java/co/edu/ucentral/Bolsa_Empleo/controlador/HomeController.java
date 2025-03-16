package co.edu.ucentral.Bolsa_Empleo.controlador;


import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class HomeController {

    @GetMapping({"/"})
    public String prueba() {
        return "index"; // nombre del archivo login.html
    }
    @GetMapping({"/registrar/codigo"})
    public String prueba2() {
        return "Candidatos/codigo"; // nombre del archivo login.html
    }
    @GetMapping({"/registrar/datosPersonales"})
    public String prueba3() {
        return "Candidatos/datosPersonales"; // nombre del archivo login.html
    }
    @GetMapping({"/postulacion/curriculum"})
    public String formulario(HttpSession session, Model model) {
        model.addAttribute("", session.getAttribute("candidato"));
        System.out.println(session.getAttribute("candidato").getClass());
        return "Candidatos/curriculum"; // nombre del archivo login.html
    }


}