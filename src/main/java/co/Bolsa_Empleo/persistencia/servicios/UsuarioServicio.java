package co.Bolsa_Empleo.persistencia.servicios;

import co.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.Bolsa_Empleo.persistencia.entidades.Empresa;
import co.Bolsa_Empleo.persistencia.repositorios.CandidatoRepositorio;
import co.Bolsa_Empleo.persistencia.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private CandidatoRepositorio candidatoRepositorio;

    public boolean autenticarEmpresa(String usuario, String contrasena) {
        Empresa empresa = empresaRepositorio.findByUsuario(usuario);
        return empresa != null && empresa.getContrasena().equals(contrasena);
    }

    public boolean autenticarCandidato(String usuario, String contrasena) {
        Candidato candidato = candidatoRepositorio.findByUsuario(usuario);
        return candidato != null && candidato.getContrasena().equals(contrasena);
    }

    public void registrarEmpresa(Empresa empresa) {
        empresaRepositorio.save(empresa);
    }

    public void registrarCandidato(Candidato candidato) {
        candidatoRepositorio.save(candidato);
    }
}