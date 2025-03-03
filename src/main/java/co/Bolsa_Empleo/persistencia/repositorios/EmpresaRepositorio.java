package co.Bolsa_Empleo.persistencia.repositorios;

import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
    Empresa findByUsuario(String usuario);
}