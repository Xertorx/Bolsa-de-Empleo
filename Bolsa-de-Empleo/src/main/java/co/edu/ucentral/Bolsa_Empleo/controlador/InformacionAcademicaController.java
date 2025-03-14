package co.edu.ucentral.Bolsa_Empleo.controlador;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.ExperienciaLaboral;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.InformacionAcademica;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.InformacionAcademicaRepository;
import co.edu.ucentral.Bolsa_Empleo.persistencia.servicios.ExperienciaLaboralService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/academica")
@CrossOrigin(origins = "*")
public class InformacionAcademicaController {

    @Autowired
    private InformacionAcademicaRepository repository;

    @PostMapping
    public List<InformacionAcademica> guardarInformacionAcademica(@RequestBody List<InformacionAcademica> infoAcademica) {
        return repository.saveAll(infoAcademica);
    }

    @GetMapping
    public List<InformacionAcademica> obtenerInformacionAcademica() {
        return repository.findAll();
    }
}