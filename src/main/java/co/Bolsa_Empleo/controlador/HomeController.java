package co.Bolsa_Empleo.controlador;

import co.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.Bolsa_Empleo.persistencia.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private UsuarioServicio usuarioServicio;
    // Página de inicio
    @GetMapping("/")
    public String mostrarInicio() {
        return "index"; // Redirige a index.html en la raíz de templates
    }

    // Página de login
    @GetMapping("/login")
    public String mostrarLogin(Model modelo) {
        return "login"; // Redirige a login.html en la raíz de templates
    }

    // Página de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model modelo) {
        return "registro"; // Redirige a registro.html en la raíz de templates
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String contrasena, @RequestParam String tipo, Model model) {
        if (usuario == null || contrasena == null || tipo == null) {
            model.addAttribute("error", "Todos los campos son obligatorios");
            return "login";
        }

        String tipoUsuario = tipo.toLowerCase(); // Convertir a minúsculas para evitar errores

        if ("empresa".equals(tipoUsuario) && usuarioServicio.autenticarEmpresa(usuario, contrasena)) {
            return "redirect:/empresa/dashboard";
        } else if ("candidato".equals(tipoUsuario) && usuarioServicio.autenticarCandidato(usuario, contrasena)) {
            return "redirect:/candidato/dashboard";
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String usuario, @RequestParam String contrasena, @RequestParam String tipo, Model model) {
        if (usuario == null || contrasena == null || tipo == null) {
            model.addAttribute("error", "Todos los campos son obligatorios");
            return "registro";
        }

        String tipoUsuario = tipo.toLowerCase();

        if ("empresa".equals(tipoUsuario)) {
            Empresa empresa = new Empresa();
            empresa.setUsuario(usuario);
            empresa.setContrasena(contrasena);
            usuarioServicio.registrarEmpresa(empresa);
        } else if ("candidato".equals(tipoUsuario)) {
            Candidato candidato = new Candidato();
            candidato.setUsuario(usuario);
            candidato.setContrasena(contrasena);
            usuarioServicio.registrarCandidato(candidato);
        } else {
            model.addAttribute("error", "Tipo de usuario inválido");
            return "registro";
        }

        return "redirect:/login";
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
    public String mostrarCurriculum() {
        return "Candidatos/curriculum";
    }
}
