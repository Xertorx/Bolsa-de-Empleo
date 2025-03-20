package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.EmailDTO;
import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.OfertaEmpleo;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.IEmailInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailServiceImp implements IEmailInterface {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public EmailServiceImp(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }
    @Override
    public void senMail(EmailDTO email) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setTo(email.getDestinatario());
            helper.setSubject(email.getAsunto());
            Context context = new Context();
            context.setVariable("message",email.getMensaje());
            String contentHtml = templateEngine.process("email",context);
            helper.setText(contentHtml,true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Erro al enviar el correo"+ e.getMessage());
        }

    }

    public void enviarOfertasEmpleo(String destinatario,List<OfertaEmpleo> ofertas) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("Ofertas de Empleo Recientes");

            Context context = new Context();
            context.setVariable("ofertas", ofertas);

            String contentHtml = templateEngine.process("OfertasEmail", context);
            helper.setText(contentHtml, true);

            javaMailSender.send(message);
            System.out.println("Correo con ofertas enviado a " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo con ofertas de empleo", e);
        }
    }

}
