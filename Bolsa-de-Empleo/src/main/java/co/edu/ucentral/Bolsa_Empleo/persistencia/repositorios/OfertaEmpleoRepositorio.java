package co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaEmpleoRepositorio extends JpaRepository<OfertaEmpleo, Long> {

    // Ofertas activas (para mostrar públicamente)
    List<OfertaEmpleo> findByActivaTrue();

    // Ofertas publicadas por una empresa en particular
    List<OfertaEmpleo> findByEmpresa(Empresa empresa);
}
