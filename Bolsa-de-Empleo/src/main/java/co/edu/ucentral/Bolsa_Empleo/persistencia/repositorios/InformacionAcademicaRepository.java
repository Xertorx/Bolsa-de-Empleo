package co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.ExperienciaLaboral;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.InformacionAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InformacionAcademicaRepository extends JpaRepository<InformacionAcademica, Long> {
}
