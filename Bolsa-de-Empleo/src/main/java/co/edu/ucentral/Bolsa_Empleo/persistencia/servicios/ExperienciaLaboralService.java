package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.ExperienciaLaboral;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.ExperienciaLaboralRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ExperienciaLaboralService {

    @Autowired
    private ExperienciaLaboralRepository experienciaRepository;

    public ExperienciaLaboral guardarExperiencia(ExperienciaLaboral experiencia) {
        return experienciaRepository.save(experiencia);
    }
}