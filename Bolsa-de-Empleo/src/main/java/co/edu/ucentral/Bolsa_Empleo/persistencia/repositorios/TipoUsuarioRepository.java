package co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
}
