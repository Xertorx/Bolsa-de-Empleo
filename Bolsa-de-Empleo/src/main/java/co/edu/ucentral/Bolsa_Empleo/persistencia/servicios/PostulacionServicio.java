package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Postulacion;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.PostulacionRepositorio;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.OfertaEmpleoRepositorio;  // Corregido aquí
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Transactional
@Service
public class PostulacionServicio {
    @Autowired
    private PostulacionRepositorio postulacionRepositorio;
    @Autowired
    private OfertaEmpleoRepositorio ofertaEmpleoRepositorio;  // Corregido aquí

    public boolean registrarPostulacion(Candidato candidato, Long idOferta) {
        OfertaEmpleo oferta = ofertaEmpleoRepositorio.findById(idOferta).orElse(null);  // Corregido aquí
        if (oferta == null) {
            return false;
        }

        // Verificar si ya existe una postulación para este candidato en esta oferta
        if (postulacionRepositorio.existsByCandidatoAndOferta(candidato, oferta)) {
            return false;
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setCandidato(candidato);
        postulacion.setOferta(oferta);
        postulacion.setFechaPostulacion(LocalDate.now());

        postulacionRepositorio.save(postulacion);
        return true;
    }
    // Método para obtener las postulaciones de un candidato
    public List<Postulacion> obtenerPostulacionesPorCandidato(Candidato candidato) {
        return postulacionRepositorio.findByCandidato(candidato);  // Asumir que existe un método en el repositorio para esto
    }

    // Método para verificar si existe una postulación para un candidato y una oferta
    public boolean existePostulacion(Candidato candidato, OfertaEmpleo oferta) {
        return postulacionRepositorio.existsByCandidatoAndOferta(candidato, oferta);
    }

    // Método para guardar la postulación
    public void guardar(Postulacion postulacion) {
        postulacionRepositorio.save(postulacion);
    }

}
