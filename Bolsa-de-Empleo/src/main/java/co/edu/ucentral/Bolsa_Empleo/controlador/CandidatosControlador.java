package co.edu.ucentral.Bolsa_Empleo.controlador;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.EmpresaServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.CandidatoServicio;
import java.util.Optional;

@Controller
public class CandidatosControlador {

    @Autowired
    private CandidatoServicio candidatoServicio;

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
                                   @RequestParam String rol, RedirectAttributes redirectAttributes) {
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
        } else if ("Empresa".equalsIgnoreCase(rol)) {
            Empresa empresa = new Empresa();
            empresa.setCorreo(correo);
            empresa.setContrasena(contrasena);
            empresa.setNombres(nombres);
            empresa.setApellidos(apellidos);
            empresa.setRol(rol);
            empresaServicio.registrarEmpresa(empresa);
        }

        redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado con éxito");
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String correo, @RequestParam String contrasena,
                                @RequestParam String rol, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if ("Candidato".equalsIgnoreCase(rol)) {
            Optional<Candidato> candidato = candidatoServicio.buscarPorCorreo(correo);
            if (candidato.isPresent() && candidato.get().getContrasena().equals(contrasena)) {
                session.setAttribute("candidato", candidato.get()); // Guardar el candidato
                System.out.println(candidato.get().getNombres());
                return "redirect:/";
            }
        } else if ("Empresa".equalsIgnoreCase(rol)) {
            Optional<Empresa> empresa = empresaServicio.buscarPorCorreo(correo);
            if (empresa.isPresent() && empresa.get().getContrasena().equals(contrasena)) {
                session.setAttribute("empresa", empresa); // Guardar la empresa
                return "redirect:/";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
        return "redirect:/auth/login";
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
