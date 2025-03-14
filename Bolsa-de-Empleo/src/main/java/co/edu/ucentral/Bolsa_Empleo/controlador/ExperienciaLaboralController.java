package co.edu.ucentral.Bolsa_Empleo.controlador;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.ExperienciaLaboral;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.ExperienciaLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/experiencia")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend
public class ExperienciaLaboralController {

    @Autowired
    private ExperienciaLaboralRepository repository;

    @PostMapping
    public List<ExperienciaLaboral> guardarExperiencias(@RequestBody List<ExperienciaLaboral> experiencias) {
        return repository.saveAll(experiencias);
    }

    @GetMapping
    public List<ExperienciaLaboral> obtenerExperiencias() {
        return repository.findAll();
    }
}