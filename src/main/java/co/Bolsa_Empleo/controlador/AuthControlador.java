package co.Bolsa_Empleo.controlador;

import co.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.Bolsa_Empleo.persistencia.servicios.CandidatoServicio;
import co.Bolsa_Empleo.persistencia.servicios.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




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
        System.out.println("Contrasena recibida: " + contrasena);
        System.out.println("Nombres recibidos: " + nombres);
        System.out.println("Apellidos recibidos: " + apellidos);
        System.out.println("Rol recibido: " + rol);

        if ("Candidato".equalsIgnoreCase(rol)) {
            Candidato candidato = new Candidato();
            candidato.setCorreo(correo);
            candidato.setContrasena(contrasena); // Cambio: se eliminó la "ñ"
            candidato.setNombres(nombres);
            candidato.setApellidos(apellidos);
            candidato.setRol(rol);
            candidatoServicio.registrarCandidato(candidato);
        } else if ("Empresa".equalsIgnoreCase(rol)) {
            Empresa empresa = new Empresa();
            empresa.setCorreo(correo);
            empresa.setContrasena(contrasena); // Cambio: se eliminó la "ñ"
            empresa.setNombres(nombres);
            empresa.setApellidos(apellidos);
            empresa.setRol(rol);
            empresaServicio.registrarEmpresa(empresa);
        }

        // Agregar mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado con éxito");

        return "redirect:/auth/login";
    }
}

