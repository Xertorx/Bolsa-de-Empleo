package co.edu.ucentral.Bolsa_Empleo.controlador;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Usuarios;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.UsuariosRepository;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.TipoUsuarioService;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private final TipoUsuarioService tipoUsuarioService;

    public UsuariosController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @GetMapping({"/registrar"})
    public String registerPage(Model model) {
        model.addAttribute("usuarios", new Usuarios());
        model.addAttribute("tiposUsuario", tipoUsuarioService.listarTiposUsuario());
        return "Candidatos/registro"; // nombre del archivo login.html
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuarios") Usuarios usuarios,
                                   BindingResult result, RedirectAttributes redirectAttributes,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tiposUsuario", tipoUsuarioService.listarTiposUsuario());
            return "registro";
        }
        try {
            usuariosService.registrarUsuario(usuarios);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario Registrado Correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/registro";
    }



}
