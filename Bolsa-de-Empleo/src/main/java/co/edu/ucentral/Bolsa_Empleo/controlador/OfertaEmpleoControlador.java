package co.edu.ucentral.Bolsa_Empleo.controlador;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Postulacion;
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
    public String listarOfertas(Model model, HttpSession session) {
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        if (empresa == null) {
            return "redirect:/login"; // Redirigir si no hay empresa en la sesión
        }

        List<OfertaEmpleo> ofertasActivas = ofertaServicio.listarOfertasPorEmpresa(empresa.getId());
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
        if (empresa == null || oferta == null || oferta.getId() == null) {
            ra.addFlashAttribute("error", "Error: Datos inválidos.");
            return "redirect:/ofertas/lista";
        }

        Optional<OfertaEmpleo> ofertaExistente = ofertaServicio.obtenerOfertaPorId(oferta.getId());
        if (ofertaExistente.isEmpty() || !ofertaExistente.get().getEmpresa().getId().equals(empresa.getId())) {
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
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        if (empresa == null) {
            ra.addFlashAttribute("error", "Error: Debes iniciar sesión.");
            return "redirect:/ofertas/lista";
        }

        Optional<OfertaEmpleo> ofertaOpt = ofertaServicio.obtenerOfertaPorId(id);
        if (ofertaOpt.isPresent()) {
            OfertaEmpleo oferta = ofertaOpt.get();
            if (!oferta.getEmpresa().getId().equals(empresa.getId())) {
                ra.addFlashAttribute("error", "No tienes permisos para eliminar esta oferta.");
                return "redirect:/ofertas/lista";
            }

            boolean eliminada = ofertaServicio.eliminarOferta(id);
            if (eliminada) {
                ra.addFlashAttribute("mensaje", "Oferta eliminada con éxito.");
            } else {
                ra.addFlashAttribute("error", "No se puede eliminar la oferta. Puede estar relacionada con postulaciones.");
            }
        } else {
            ra.addFlashAttribute("error", "Oferta no encontrada.");
        }
        return "redirect:/ofertas/lista";
    }


    @PostMapping("/postular/{id}")
    public String postular(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        Candidato candidato = (Candidato) session.getAttribute("candidato");

        if (candidato == null) {
            ra.addFlashAttribute("mensajeError", "Debes iniciar sesión para postularte.");
            return "redirect:/login";
        }

        Optional<OfertaEmpleo> optionalOferta = ofertaServicio.obtenerOfertaPorId(id);

        if (optionalOferta.isEmpty()) {
            ra.addFlashAttribute("mensajeError", "La oferta de empleo no existe.");
            return "redirect:/";
        }

        OfertaEmpleo oferta = optionalOferta.get();

        // Verificar si hay vacantes disponibles
        if (oferta.getNumeroVacantes() <= 0) {
            ra.addFlashAttribute("mensajeError", "No hay vacantes disponibles para esta oferta.");
            return "redirect:/ofertas/lista";
        }

        boolean exito = postulacionServicio.registrarPostulacion(candidato, id);

        if (exito) {
            ra.addFlashAttribute("mensajeExito", "Tu postulación ha sido registrada exitosamente.");
        } else {
            ra.addFlashAttribute("mensajeError", "No se pudo registrar tu postulación (puede que ya te hayas postulado o no haya vacantes).");
        }

        return "redirect:/";
    }

    // MEtodos para ver postulaciones

    @GetMapping("/postulaciones/{id}")
    public String verPostulaciones(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes ra) {
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        if (empresa == null) {
            ra.addFlashAttribute("error", "Debes iniciar sesión como empresa para ver postulaciones.");
            return "redirect:/login";
        }

        Optional<OfertaEmpleo> ofertaOpt = ofertaServicio.obtenerOfertaPorId(id);
        if (ofertaOpt.isEmpty() || !ofertaOpt.get().getEmpresa().getId().equals(empresa.getId())) {
            ra.addFlashAttribute("error", "No tienes permisos para ver esta oferta.");
            return "redirect:/ofertas/lista";
        }

        List<Postulacion> postulaciones = postulacionServicio.obtenerPostulacionesPorOferta(id);
        model.addAttribute("oferta", ofertaOpt.get());
        model.addAttribute("postulaciones", postulaciones);

        return "postulaciones"; // Asegúrate de que esta vista exista en templates/
    }

    // MÉTODO PARA ACEPTAR UNA POSTULACIÓN
    @PostMapping("/postulaciones/{id}/aceptar")
    public String aceptarPostulacion(@PathVariable Long id, RedirectAttributes ra) {
        Optional<Postulacion> postulacionOpt = postulacionServicio.obtenerPostulacionPorId(id);
        System.out.println(postulacionOpt.toString());

        if (postulacionOpt.isPresent()) {
            Postulacion postulacion = postulacionOpt.get();
            postulacion.setEstado("ACEPTADA");
            System.out.println(postulacion.toString());
            postulacionServicio.guardar(postulacion);
            ra.addFlashAttribute("mensaje", "Postulación aceptada.");
            return "redirect:/ofertas/postulaciones/{id}" + postulacion.getOferta().getId();
        }


        ra.addFlashAttribute("error", "Error al aceptar la postulación.");
        return "redirect:/ofertas/lista";
    }

    // MÉTODO PARA RECHAZAR UNA POSTULACIÓN
    @PostMapping("/postulaciones/{id}/rechazar")
    public String rechazarPostulacion(@PathVariable Long id, RedirectAttributes ra) {
        Optional<Postulacion> postulacionOpt = postulacionServicio.obtenerPostulacionPorId(id);

        if (postulacionOpt.isPresent()) {
            Postulacion postulacion = postulacionOpt.get();
            postulacion.setEstado("RECHAZADA");
            postulacionServicio.guardar(postulacion);
            ra.addFlashAttribute("mensaje", "Postulación rechazada.");
            return "redirect:/ofertas/postulaciones/{id}" + postulacion.getOferta().getId();
        }

        ra.addFlashAttribute("error", "Error al rechazar la postulación.");
        return "redirect:/ofertas/lista";
    }
}



