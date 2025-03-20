package co.edu.ucentral.Bolsa_Empleo.controlador;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.*;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.CandidatoRepositorio;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.EmpresaRepositorio;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.IEmailInterface;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class CandidatosControlador {

    @Autowired
    private CandidatoServicio candidatoServicio;
    @Autowired
    private RecaptchaService rp;
    @Autowired
    private IEmailInterface email;
    @Autowired
    private TareaProgamaCorreoServicio progamar;
    @Autowired
    private OfertaEmpleoServicio ofertaServicio;

    @Autowired
    private CandidatoRepositorio candidatoRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;
    @Autowired
    private EmpresaServicio empresaServicio;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String correo, @RequestParam String contrasena,
                                   @RequestParam String nombres, @RequestParam String apellidos,
                                   @RequestParam String rol,
                                   @RequestParam(required = false) boolean recibirOfertas,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session ) {
        System.out.println("Correo recibido: " + correo);
        System.out.println("Contraseña recibida: " + contrasena);
        System.out.println("Nombres recibidos: " + nombres);
        System.out.println("Apellidos recibidos: " + apellidos);
        System.out.println("Rol recibido: " + rol);

        if (candidatoServicio.buscarPorCorreo(correo).isPresent() || empresaServicio.buscarPorCorreo(correo).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya existe el usuario");
            return "redirect:/registro";
        }

        if ("Candidato".equalsIgnoreCase(rol)) {
            Candidato candidato = new Candidato();
            candidato.setCorreo(correo);
            candidato.setContrasena(contrasena);
            candidato.setNombres(nombres);
            candidato.setApellidos(apellidos);
            candidato.setRol(rol);
            candidatoServicio.registrarCandidato(candidato);
            session.setAttribute("candidato", candidato);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado con éxito");
            enviarCorreoBienvenida(correo, nombres, "");

            if (recibirOfertas) {
                List<OfertaEmpleo> ofertasActivas = ofertaServicio.listarOfertasActivas();
                progamar.programarEnvioCorreo(() -> {
                    try {
                        email.enviarOfertasEmpleo(correo,ofertasActivas);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }, 300000);
            }
            return "redirect:/registro/datosPersonales";
        } else if ("Empresa".equalsIgnoreCase(rol)) {
            Empresa empresa = new Empresa();
            empresa.setCorreo(correo);
            empresa.setContrasena(contrasena);
            empresa.setNombres(nombres);
            empresa.setApellidos(apellidos);
            empresa.setRol(rol);
            empresaServicio.registrarEmpresa(empresa);
            session.setAttribute("empresa", empresa);
            return "redirect:/registro/datosPersonales";
        }
        return "redirect:/registro";
    }



    private void enviarCorreoBienvenida(String destinatario, String nombre, String mensaje) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setDestinatario(destinatario);
        emailDTO.setAsunto("Bienvenido a Bolsa de Empleo");
        emailDTO.setMensaje(mensaje);

        try {
            email.senMail(emailDTO);
            System.out.println("Correo de bienvenida enviado a " + destinatario);

        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }



    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String correo, @RequestParam String contrasena,
                                @RequestParam String rol, HttpSession session,
                                RedirectAttributes redirectAttributes,@RequestParam("g-recaptcha-response") String recaptchaResponse,
                                Model model) {
        boolean captchaValido = rp.validateCaptcha(recaptchaResponse);

        if (!captchaValido) {
            model.addAttribute("error", "Verificación reCAPTCHA fallida. Inténtalo de nuevo.");
            return "login";
        }else if ("Candidato".equalsIgnoreCase(rol)) {
            Optional<Candidato> candidato = candidatoServicio.buscarPorCorreo(correo);
            if (candidato.isPresent() && candidato.get().getContrasena().equals(contrasena)) {
                session.setAttribute("candidato", candidato.get()); // Guardar el candidato
                System.out.println(candidato.get().getNombres());
                return "redirect:/";
            }
        } else if ("Empresa".equalsIgnoreCase(rol)) {
            Optional<Empresa> empresa = empresaServicio.buscarPorCorreo(correo);
            if (empresa.isPresent() && empresa.get().getContrasena().equals(contrasena)) {
                session.setAttribute("empresa", empresa.get());
                return "redirect:/paginaempresa";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
        return "redirect:/login";
    }

    @GetMapping("/registro/datosPersonales")
    public String paginaDatosPersonales(Model model, HttpSession session) {
        Candidato candidato = (Candidato) session.getAttribute("candidato");
        Empresa empresa = (Empresa ) session.getAttribute("empresa");
        if (candidato != null){
            model.addAttribute("objeto", candidato);
        }else if (empresa !=null){
            model.addAttribute("objeto", empresa);
        }


        return "Candidatos/datosPersonales";
    }
    @PostMapping("/registro/datosPersonales/registrar")
    public String registrarDatos(@Valid @ModelAttribute("objeto") RegistroDTO registroDTO,
                                 BindingResult result, HttpSession session) throws IOException {
        if (result.hasErrors()) {
            return "Candidatos/datosPersonales"; // Retorna a la misma vista con errores
        }

        // Obtener los objetos de la sesión
        Candidato candidatoExistente = (Candidato) session.getAttribute("candidato");
        Empresa empresaExistente = (Empresa) session.getAttribute("empresa");

        if (candidatoExistente != null) {
            // Actualizar datos del candidato
            candidatoExistente.setNombres(registroDTO.getNombres());
            candidatoExistente.setApellidos(registroDTO.getApellidos());
            candidatoExistente.setTipo_identificacion(registroDTO.getTipo_identificacion());
            candidatoExistente.setNumero_identificacion(registroDTO.getNumero_identificacion());
            candidatoExistente.setDireccion(registroDTO.getDireccion());
            candidatoExistente.setTelefono(registroDTO.getTelefono());
            // Otros datos que necesites actualizar...

            candidatoRepositorio.save(candidatoExistente);
        } else if (empresaExistente != null) {
            empresaExistente.setNombres(registroDTO.getNombres());
            empresaExistente.setApellidos(registroDTO.getApellidos());
            empresaExistente.setTipo_identificacion(registroDTO.getTipo_identificacion());
            empresaExistente.setNumero_identificacion(registroDTO.getNumero_identificacion());
            empresaExistente.setDireccion(registroDTO.getDireccion());
            empresaExistente.setTelefono(registroDTO.getTelefono());

            empresaRepositorio.save(empresaExistente); 
        }

        // Redirigir a la siguiente página según el tipo de usuario
        if (session.getAttribute("candidato") != null) {
            return "redirect:/postulacion/curriculum";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/paginacandidato")
    public String paginaCandidato() {
        return "paginacandidato";
    }

    @GetMapping("/paginaempresa")
    public String paginaEmpresa() {
        return "paginaempresa";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, SessionStatus status) {
        session.invalidate();
        status.setComplete();
        return "redirect:/";
    }

}
