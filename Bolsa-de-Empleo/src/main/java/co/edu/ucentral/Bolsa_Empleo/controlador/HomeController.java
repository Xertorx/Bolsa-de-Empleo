package co.edu.ucentral.Bolsa_Empleo.controlador;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.OfertaEmpleoServicio;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {

    @Autowired
    private OfertaEmpleoServicio ofertaServicio;

    @GetMapping({"/", "/index"})
    public String mostrarIndex(Model model) {
        // Obtén las ofertas activas desde la base de datos
        List<OfertaEmpleo> ofertas = ofertaServicio.listarOfertasActivas();
        // Agrega la lista al modelo con el nombre "ofertas"
        model.addAttribute("ofertas", ofertas);
        return "index"; // Se renderiza index.html
    }

    @GetMapping({"/registrar/codigo"})
    public String prueba2() {
        return "Candidatos/codigo";
    }

    @GetMapping({"/registrar/datosPersonales"})
    public String prueba3() {
        return "Candidatos/datosPersonales";
    }

    @GetMapping({"/postulacion/curriculum"})
    public String formulario(HttpSession session, Model model) {
        model.addAttribute("", session.getAttribute("candidato"));
        System.out.println(session.getAttribute("candidato").getClass());
        return "Candidatos/curriculum";
    }
}
