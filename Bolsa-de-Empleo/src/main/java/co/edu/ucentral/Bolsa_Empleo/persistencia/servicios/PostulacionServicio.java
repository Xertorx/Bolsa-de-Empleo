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
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

        // Revisar si no vacntes
        if (oferta.getNumeroVacantes() <= 0) {
            return false; // No hay vacanted disponibles
        }

        // Verificar si ya existe una postulación para este candidato en esta oferta
        if (postulacionRepositorio.existsByCandidatoAndOferta(candidato, oferta)) {
            return false;
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setCandidato(candidato);
        postulacion.setOferta(oferta);
        postulacion.setFechaPostulacion(LocalDate.now());
        postulacion.setEstado("PENDIENTE");

        postulacionRepositorio.save(postulacion);
        // Reducir en 1 el num de vacantes
        oferta.setNumeroVacantes(oferta.getNumeroVacantes() - 1);
        ofertaEmpleoRepositorio.save(oferta);  // Guardar el cambio en la BD
        postulacionRepositorio.save(postulacion);

        return true;
    }

    // Método para obtener las postulaciones de un candidato
    public List<Postulacion> obtenerPostulacionesPorCandidato(Candidato candidato) {
        return postulacionRepositorio.findByCandidato(candidato);  // Método único implementado
    }

    // Método para verificar si existe una postulación para un candidato y una oferta
    public boolean existePostulacion(Candidato candidato, OfertaEmpleo oferta) {
        return postulacionRepositorio.existsByCandidatoAndOferta(candidato, oferta);
    }







    // Método para obtener postulaciones de una oferta específica
    public List<Postulacion> obtenerPostulacionesPorOferta(Long idOferta) {
        return postulacionRepositorio.findByOfertaId(idOferta);
    }


    // Método para obtener una postulación por ID
    public Optional<Postulacion> obtenerPostulacionPorId(Long id) {
        return postulacionRepositorio.findById(id); // ✅ Ahora implementado correctamente
    }

    // Método para actualizar el estado de una postulación
    public boolean actualizarEstadoPostulacion(Long id, String estado) {
        Optional<Postulacion> postulacionOpt = postulacionRepositorio.findById(id);
        if (postulacionOpt.isPresent()) {
            Postulacion postulacion = postulacionOpt.get();
            postulacion.setEstado(estado);
            postulacionRepositorio.save(postulacion);
            return true;
        }
        return false;
    }





    // Método para guardar la postulación
    public void guardar(Postulacion postulacion) {
        postulacionRepositorio.save(postulacion);




    }
    }




