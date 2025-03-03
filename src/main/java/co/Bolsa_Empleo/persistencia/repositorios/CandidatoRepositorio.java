package co.Bolsa_Empleo.persistencia.repositorios;

import co.Bolsa_Empleo.persistencia.entidades.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {
    Candidato findByUsuario(String usuario);
}