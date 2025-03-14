package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.InformacionAcademica;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.InformacionAcademicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformacionAcademicaService {

    @Autowired
    private InformacionAcademicaRepository infoAcademicaRepository;

    public InformacionAcademica guardarInformacion(InformacionAcademica informacion) {
        return infoAcademicaRepository.save(informacion);
    }
}