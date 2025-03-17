package co.edu.ucentral.Bolsa_Empleo.controlador;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.OfertaEmpleoServicio;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.PostulacionServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ofertas")
public class OfertaEmpleoControlador {
    @Autowired
    private PostulacionServicio postulacionServicio;

    @Autowired
    private OfertaEmpleoServicio ofertaServicio;

    // Mostrar Formulario
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaOferta(Model model, HttpSession session, RedirectAttributes ra) {
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        if (empresa == null) {
            ra.addFlashAttribute("error", "Debes iniciar sesión como empresa para publicar ofertas.");
            return "redirect:/login";
        }
        model.addAttribute("oferta", new OfertaEmpleo());
        return "OfertaEmpleo";
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    // Guardar Oferta
    @PostMapping("/guardar")
    public String guardarOferta(@ModelAttribute("oferta") OfertaEmpleo oferta, HttpSession session, RedirectAttributes ra) {
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        if (empresa == null) {
            ra.addFlashAttribute("error", "No tienes sesión como empresa.");
            return "redirect:/login";
        }
        oferta.setEmpresa(empresa);
        ofertaServicio.publicarOferta(oferta);
        ra.addFlashAttribute("mensaje", "Oferta publicada con éxito.");
        return "redirect:/ofertas/lista";
    }

    //----------------------------------------------------------------------------------------------------------------------
    // Lista todas las ofertas activas
    @GetMapping("/lista")
    public String listarOfertas(Model model) {
        List<OfertaEmpleo> ofertasActivas = ofertaServicio.listarOfertasActivas();
        model.addAttribute("ofertas", ofertasActivas);
        return "MostrarOfertas"; // Nombre de la vista: MostrarOfertas.html
    }

    //----------------------------------------------------------------------------------------------------------------------
    // Muestra el formulario para editar una oferta existente
    @GetMapping("/editar/{id}")
    public String editarOferta(@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes ra) {
        Optional<OfertaEmpleo> ofertaOpt = ofertaServicio.obtenerOfertaPorId(id);
        if (ofertaOpt.isPresent()) {
            OfertaEmpleo oferta = ofertaOpt.get();
            Empresa empresa = (Empresa) session.getAttribute("empresa");
            if (empresa != null && oferta.getEmpresa().getId().equals(empresa.getId())) {
                model.addAttribute("oferta", oferta);
                return "EditarOferta";
            } else {
                ra.addFlashAttribute("error", "No tienes permisos para editar esta oferta.");
                return "redirect:/ofertas/lista";
            }
        } else {
            ra.addFlashAttribute("error", "Oferta no encontrada.");
            return "redirect:/ofertas/lista";
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
    // Actualiza la oferta después de editarla
    @PostMapping("/actualizar")
    public String actualizarOferta(@ModelAttribute("oferta") OfertaEmpleo oferta, HttpSession session, RedirectAttributes ra) {
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        if (empresa == null || !oferta.getEmpresa().getId().equals(empresa.getId())) {
            ra.addFlashAttribute("error", "No tienes permisos para actualizar esta oferta.");
            return "redirect:/ofertas/lista";
        }
        ofertaServicio.actualizarOferta(oferta);
        ra.addFlashAttribute("mensaje", "Oferta actualizada con éxito.");
        return "redirect:/ofertas/lista";
    }

    //----------------------------------------------------------------------------------------------------------------------
    // Elimina una oferta de empleo
    @GetMapping("/eliminar/{id}")
    public String eliminarOferta(@PathVariable("id") Long id, HttpSession session, RedirectAttributes ra) {
        Optional<OfertaEmpleo> ofertaOpt = ofertaServicio.obtenerOfertaPorId(id);
        if (ofertaOpt.isPresent()) {
            OfertaEmpleo oferta = ofertaOpt.get();
            Empresa empresa = (Empresa) session.getAttribute("empresa");
            if (empresa != null && oferta.getEmpresa().getId().equals(empresa.getId())) {
                ofertaServicio.eliminarOferta(id);
                ra.addFlashAttribute("mensaje", "Oferta eliminada con éxito.");
            } else {
                ra.addFlashAttribute("error", "No tienes permisos para eliminar esta oferta.");
            }
        } else {
            ra.addFlashAttribute("error", "Oferta no encontrada.");
        }
        return "redirect:/ofertas/lista";
    }


    @PostMapping("/postular/{id}")
    public String postular(@PathVariable("id") Long idOferta, HttpSession session, RedirectAttributes redirectAttributes) {
        Candidato candidato = (Candidato) session.getAttribute("candidato");

        if (candidato == null) {
            redirectAttributes.addFlashAttribute("mensajeError", "Debes iniciar sesión para postularte.");
            return "redirect:/login";
        }

        boolean exito = postulacionServicio.registrarPostulacion(candidato, idOferta);

        if (exito) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Tu postulación ha sido registrada exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Ya te has postulado a esta oferta.");
        }

        return "redirect:/"; // Redirige al usuario a la página principal
    }
}

