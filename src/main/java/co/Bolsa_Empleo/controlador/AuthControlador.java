package co.Bolsa_Empleo.controlador;

import co.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.Bolsa_Empleo.persistencia.servicios.CandidatoServicio;
import co.Bolsa_Empleo.persistencia.servicios.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthControlador {

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
        return "redirect:/auth/login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String correo, @RequestParam String contrasena,
                                @RequestParam String rol, RedirectAttributes redirectAttributes) {
        if ("Candidato".equalsIgnoreCase(rol)) {
            Optional<Candidato> candidato = candidatoServicio.buscarPorCorreo(correo);
            if (candidato.isPresent() && candidato.get().getContrasena().equals(contrasena)) {
                return "redirect:/auth/paginacandidato";
            }
        } else if ("Empresa".equalsIgnoreCase(rol)) {
            Optional<Empresa> empresa = empresaServicio.buscarPorCorreo(correo);
            if (empresa.isPresent() && empresa.get().getContrasena().equals(contrasena)) {
                return "redirect:/auth/paginaempresa";
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
}


