package co.edu.ucentral.Bolsa_Empleo.controlador;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.ExperienciaLaboral;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.InformacionAcademica;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.ExperienciaLaboralRepository;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.InformacionAcademicaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/experiencia")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend
public class ExperienciaLaboralController {

    @Autowired
    private ExperienciaLaboralRepository repository;
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarExperiencia(@RequestBody List<ExperienciaLaboral> experiencia) {
        experiencia.forEach(info -> {

            repository.save(info);
        });

        return ResponseEntity.ok("Experiencia guardada con éxito.");
    }

    @GetMapping
    public List<ExperienciaLaboral> obtenerExperiencia() {
        return repository.findAll();
    }
}