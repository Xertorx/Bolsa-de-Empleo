package co.Bolsa_Empleo.persistencia.servicios;

import co.Bolsa_Empleo.persistencia.entidades.TipoUsuario;
import co.Bolsa_Empleo.persistencia.repositorios.TipoUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioService(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    public List<TipoUsuario> listarTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }

}
