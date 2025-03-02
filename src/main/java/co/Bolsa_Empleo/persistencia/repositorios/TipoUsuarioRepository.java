package co.Bolsa_Empleo.persistencia.repositorios;

import co.Bolsa_Empleo.persistencia.entidades.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
}
