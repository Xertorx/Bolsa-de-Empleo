package co.edu.ucentral.Bolsa_Empleo.controlador;


import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.EmailDTO;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.IEmailInterface;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/send-email")
public class EmailController {

 @Autowired
    IEmailInterface emailInterface;

 @PostMapping
    private ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) throws MessagingException {
     emailInterface.senMail(emailDTO);
     return new ResponseEntity<>("correo enviado exitoxamente" , HttpStatus.OK);

 }



}
