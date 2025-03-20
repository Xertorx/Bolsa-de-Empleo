package co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfertaEmpleoRepositorio extends JpaRepository<OfertaEmpleo, Long> {

    List<OfertaEmpleo> findByActivaTrue();

    List<OfertaEmpleo> findByEmpresa_Id(Long empresaId);

    List<OfertaEmpleo> findByCategoriaIgnoreCaseAndCiudadIgnoreCase(String categoria, String ciudad);

    List<OfertaEmpleo> findByCiudadIgnoreCase(String ciudad);

    List<OfertaEmpleo> findByCategoriaIgnoreCase(String categoria);

    @Query("SELECT DISTINCT o.categoria FROM OfertaEmpleo o WHERE o.activa = true")
    List<String> findDistinctCategorias();

    @Query("SELECT DISTINCT o.ciudad FROM OfertaEmpleo o WHERE o.activa = true")
    List<String> findDistinctCiudades();
}
