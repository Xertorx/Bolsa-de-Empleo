package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Service
public class RecaptchaService {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String recaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        // Asegurar que los datos se envían como application/x-www-form-urlencoded
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("secret", recaptchaSecret);
        requestParams.add("response", recaptchaResponse);
        // Generar headers para la peticion post que se esta realizando a google
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //Mapear los request params y los headers cargados en el httpEntity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);
        //Ejecutar el post con los valores cargados
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                RECAPTCHA_VERIFY_URL, HttpMethod.POST, requestEntity, Map.class);
        //Obtener el body de la respuesta
        Map<String, Object> response = responseEntity.getBody();
        //verificar la respuesta y devolver un sucess para el controlador
        if (response != null) {
            System.out.println("Respuesta de reCAPTCHA:  " + response);
            return Boolean.TRUE.equals(response.get("success")); // Evita NullPointerException
        }

        return false;
    }
}
