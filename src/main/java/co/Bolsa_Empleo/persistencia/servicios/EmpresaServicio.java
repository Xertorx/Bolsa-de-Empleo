package co.Bolsa_Empleo.persistencia.servicios;

import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.Bolsa_Empleo.persistencia.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EmpresaServicio {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public Empresa registrarEmpresa(Empresa empresa) {
        return empresaRepositorio.save(empresa);
    }

    public Optional<Empresa> buscarPorCorreo(String correo) {
        return empresaRepositorio.findByCorreo(correo);
    }
}

