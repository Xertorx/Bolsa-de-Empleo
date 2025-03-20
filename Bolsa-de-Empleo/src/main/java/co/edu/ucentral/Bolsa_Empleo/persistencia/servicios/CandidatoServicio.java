package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.CandidatoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CandidatoServicio {
    @Autowired
    private CandidatoRepositorio candidatoRepositorio;

    public Candidato registrarCandidato(Candidato candidato) {
        return candidatoRepositorio.save(candidato);
    }

    public Optional<Candidato> buscarPorCorreo(String correo) {
        return candidatoRepositorio.findByCorreo(correo);
    }
    public Optional<Candidato> obtenerPorId(Long id) {
        return candidatoRepositorio.findById(id);
    }



}


