package co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.EmailDTO;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import jakarta.mail.MessagingException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEmailInterface  {
    public void senMail(EmailDTO email) throws MessagingException;

    void enviarOfertasEmpleo(String destinatario, List<OfertaEmpleo> ofertas) throws MessagingException;

}
