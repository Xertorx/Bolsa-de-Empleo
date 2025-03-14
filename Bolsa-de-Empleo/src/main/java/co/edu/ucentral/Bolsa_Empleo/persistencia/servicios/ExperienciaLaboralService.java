package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.ExperienciaLaboral;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Usuarios;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.ExperienciaLaboralRepository;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.TipoUsuarioRepository;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExperienciaLaboralService {

    private final Map<Long, List<ExperienciaLaboral>> experienciaTemporal = new HashMap<>();

    public void agregarExperiencia(Long usuarioId, ExperienciaLaboral experiencia) {
        experienciaTemporal.putIfAbsent(usuarioId, new ArrayList<>());
        experienciaTemporal.get(usuarioId).add(experiencia);
    }

    public List<ExperienciaLaboral> obtenerExperiencias(Long usuarioId) {
        return experienciaTemporal.getOrDefault(usuarioId, new ArrayList<>());
    }

    public void eliminarExperiencias(Long usuarioId) {
        experienciaTemporal.remove(usuarioId);
    }

    public void guardarEnBD(Long usuarioId, Usuarios usuario, ExperienciaLaboralRepository repository) {
        List<ExperienciaLaboral> experiencias = experienciaTemporal.get(usuarioId);
        if (experiencias != null) {
            experiencias.forEach(e -> e.setUsuario(usuario));
            repository.saveAll(experiencias);
            experienciaTemporal.remove(usuarioId); // Limpiar después de guardar
        }
    }

    public void guardarTodas(List<ExperienciaLaboral> experiencias) {
    }
}
