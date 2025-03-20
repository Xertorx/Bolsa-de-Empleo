package co.edu.ucentral.Bolsa_Empleo.controlador;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.OfertaEmpleoServicio;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final OfertaEmpleoServicio ofertaServicio;
    @GetMapping({"/", "/index"})
    public String mostrarIndex(@RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "categoria", required = false) String categoria,
                               @RequestParam(name = "ciudad", required = false) String ciudad,
                               Model model) {

        // Obtener las ofertas filtradas o todas las activas si no hay filtros
        List<OfertaEmpleo> ofertas = ofertaServicio.filtrarOfertas(categoria, ciudad);
        model.addAttribute("ofertas", ofertas);

        // Cargar categorías y ciudades para los filtros
        model.addAttribute("categorias", ofertaServicio.obtenerCategoriasDisponibles());
        model.addAttribute("ciudades", ofertaServicio.obtenerCiudadesDisponibles());

        // Si se seleccionó una oferta, enviarla al modelo
        if (id != null) {
            ofertaServicio.obtenerOfertaPorId(id).ifPresent(oferta -> model.addAttribute("ofertaSeleccionada", oferta));
        }

        return "index"; // Renderiza index.html con los datos cargados
    }


    @GetMapping("/registrar/codigo")
    public String mostrarCodigoRegistro() {
        return "Candidatos/codigo";
    }

    @GetMapping("/registrar/datosPersonales")
    public String mostrarDatosPersonales() {
        return "Candidatos/datosPersonales";
    }

    @GetMapping("/postulacion/curriculum")
    public String formulario(HttpSession session, Model model) {
        Object candidato = session.getAttribute("candidato");
        if (candidato != null) {
            model.addAttribute("candidato", candidato);
            System.out.println("Candidato: " + candidato.getClass());
        } else {
            System.out.println("No hay candidato en sesión.");
        }
        return "Candidatos/curriculum";
    }
}
