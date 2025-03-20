package co.edu.ucentral.Bolsa_Empleo.controlador;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Postulacion;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.PostulacionServicio;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.OfertaEmpleoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/postulaciones")
public class PostulacionControlador {

    @Autowired
    private PostulacionServicio postulacionServicio;

    @Autowired
    private OfertaEmpleoServicio ofertaEmpleoServicio;

    // Listar postulaciones de un candidato
    @GetMapping("/")
    public String listarPostulaciones(Model model, HttpSession session, RedirectAttributes ra) {
        Candidato candidato = (Candidato) session.getAttribute("candidato");
        if (candidato == null) {
            ra.addFlashAttribute("error", "Debe iniciar sesión para ver sus postulaciones.");
            return "redirect:/login";
        }

        List<Postulacion> postulaciones = postulacionServicio.obtenerPostulacionesPorCandidato(candidato);
        model.addAttribute("postulaciones", postulaciones);
        return "ListaPostulaciones";
    }

    // Postular a una oferta
    @PostMapping("/postular/{id}")
    public String postular(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        Candidato candidato = (Candidato) session.getAttribute("candidato");
        if (candidato == null) {
            ra.addFlashAttribute("mensajeError", "Debe iniciar sesión para postularse.");
            return "redirect:/login";
        }

        Optional<OfertaEmpleo> optionalOferta = ofertaEmpleoServicio.obtenerOfertaPorId(id);
        if (optionalOferta.isEmpty()) {
            ra.addFlashAttribute("mensajeError", "La oferta de empleo no existe.");
            return "redirect:/";
        }

        OfertaEmpleo oferta = optionalOferta.get();

        if (postulacionServicio.existePostulacion(candidato, oferta)) {
            ra.addFlashAttribute("mensajeError", "Ya ha aplicado a esta oferta anteriormente.");
            return "redirect:/";
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setCandidato(candidato);
        postulacion.setOferta(oferta);
        postulacion.setFechaPostulacion(LocalDate.now());

        postulacionServicio.guardar(postulacion);

        ra.addFlashAttribute("mensajeExito", "Se ha postulado correctamente a la oferta.");
        return "redirect:/";
    }
}
