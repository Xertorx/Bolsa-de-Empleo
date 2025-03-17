package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.OfertaEmpleoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaEmpleoServicio {

    @Autowired
    private OfertaEmpleoRepositorio ofertaRepo;

    // Publicar nueva oferta de empleo
    public OfertaEmpleo publicarOferta(OfertaEmpleo oferta) {
        oferta.setFechaPublicacion(LocalDate.now());
        oferta.setActiva(true); // Se marca la oferta como activa cuando se publica
        return ofertaRepo.save(oferta);
    }

    // Obtener todas las ofertas activas
    public List<OfertaEmpleo> listarOfertasActivas() {
        return ofertaRepo.findByActivaTrue();
    }

    // Obtener ofertas por empresa
    public List<OfertaEmpleo> listarOfertasPorEmpresa(Long empresaId) {
        return ofertaRepo.findByEmpresa_Id(empresaId);
    }

    // Obtener una oferta por su ID
    public Optional<OfertaEmpleo> obtenerOfertaPorId(Long id) {
        return ofertaRepo.findById(id);
    }

    // Actualizar una oferta existente
    public OfertaEmpleo actualizarOferta(OfertaEmpleo oferta) {
        return ofertaRepo.save(oferta);
    }

    // Eliminar una oferta
    public void eliminarOferta(Long id) {
        ofertaRepo.deleteById(id);
    }

    // Filtrar ofertas por categoría y ciudad
    public List<OfertaEmpleo> filtrarOfertas(String categoria, String ciudad) {
        if ((categoria == null || categoria.isEmpty()) && (ciudad == null || ciudad.isEmpty())) {
            return ofertaRepo.findByActivaTrue(); // Si no se filtra, se muestran todas las ofertas activas
        } else if (categoria == null || categoria.isEmpty()) {
            return ofertaRepo.findByCiudadIgnoreCase(ciudad); // Filtrar por ciudad
        } else if (ciudad == null || ciudad.isEmpty()) {
            return ofertaRepo.findByCategoriaIgnoreCase(categoria); // Filtrar por categoría
        } else {
            return ofertaRepo.findByCategoriaIgnoreCaseAndCiudadIgnoreCase(categoria, ciudad); // Filtrar por ambas
        }
    }

    // Obtener categorías disponibles
    public List<String> obtenerCategoriasDisponibles() {
        return ofertaRepo.findDistinctCategorias();
    }

    // Obtener ciudades disponibles
    public List<String> obtenerCiudadesDisponibles() {
        return ofertaRepo.findDistinctCiudades();
    }
}
