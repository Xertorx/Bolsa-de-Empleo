package co.edu.ucentral.Bolsa_Empleo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BolsaEmpleoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BolsaEmpleoApplication.class, args);
	}

}
