package co.Bolsa_Empleo.persistencia.servicios;

import co.Bolsa_Empleo.persistencia.entidades.Usuarios;
import co.Bolsa_Empleo.persistencia.repositorios.TipoUsuarioRepository;
import co.Bolsa_Empleo.persistencia.repositorios.UsuariosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public UsuariosService(UsuariosRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Transactional
    public Usuarios registrarUsuario(Usuarios usuarios) {

        Optional<Usuarios> usuarioExistente = usuarioRepository.findByEmail(usuarios.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El usuario con el correo " + usuarios.getEmail() + " ya existe.");
        } else {
            return usuarioRepository.save(usuarios);
        }
    }

}
