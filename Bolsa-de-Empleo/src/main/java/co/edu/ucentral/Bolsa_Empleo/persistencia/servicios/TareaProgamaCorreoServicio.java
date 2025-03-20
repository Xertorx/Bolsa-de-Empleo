package co.edu.ucentral.Bolsa_Empleo.persistencia.servicios;

import co.edu.ucentral.Bolsa_Empleo.persistencia.entidades.Candidato;
import co.edu.ucentral.Bolsa_Empleo.persistencia.repositorios.CandidatoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TareaProgamaCorreoServicio {
    private final ThreadPoolTaskScheduler taskScheduler;

    public TareaProgamaCorreoServicio() {
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
    }

    public void programarEnvioCorreo(Runnable tarea, long retrasoMilisegundos) {
        taskScheduler.schedule(tarea, new java.util.Date(System.currentTimeMillis() + retrasoMilisegundos));
    }

}


