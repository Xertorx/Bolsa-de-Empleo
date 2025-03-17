package co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PostulacionRepositorio extends JpaRepository<Postulacion, Long> {

    // Método para obtener postulaciones por candidato
    List<Postulacion> findByCandidato(Candidato candidato);

    // Método para verificar si existe una postulación de un candidato a una oferta
    boolean existsByCandidatoAndOferta(Candidato candidato, OfertaEmpleo oferta);
}






