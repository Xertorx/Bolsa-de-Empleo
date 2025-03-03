package co.Bolsa_Empleo.persistencia.repositorios;

import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCorreo(String correo);
}
