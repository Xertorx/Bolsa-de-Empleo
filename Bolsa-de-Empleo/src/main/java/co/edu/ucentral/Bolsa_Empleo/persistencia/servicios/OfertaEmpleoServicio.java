package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Empresa;
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

    // Crea y publica una nueva oferta
    public OfertaEmpleo publicarOferta(OfertaEmpleo oferta) {
        oferta.setFechaPublicacion(LocalDate.now());
        oferta.setActiva(true);
        return ofertaRepo.save(oferta);
    }

    // Listado de ofertas activas (para mostrarlas a candidatos y público)
    public List<OfertaEmpleo> listarOfertasActivas() {
        return ofertaRepo.findByActivaTrue();
    }

    // Listado de ofertas de una empresa en particular
    public List<OfertaEmpleo> listarOfertasPorEmpresa(Empresa empresa) {
        return ofertaRepo.findByEmpresa(empresa);
    }

    // Obtiene una oferta por su ID
    public Optional<OfertaEmpleo> obtenerOfertaPorId(Long id) {
        return ofertaRepo.findById(id);
    }

    // Actualiza una oferta existente
    public OfertaEmpleo actualizarOferta(OfertaEmpleo oferta) {
        return ofertaRepo.save(oferta);
    }

    // Elimina una oferta por su ID
    public void eliminarOferta(Long id) {
        ofertaRepo.deleteById(id);
    }
}

