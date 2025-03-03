package co.Bolsa_Empleo.persistencia.repositorios;

import co.Bolsa_Empleo.persistencia.entidades.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {
    Optional<Candidato> findByCorreo(String correo);
}
