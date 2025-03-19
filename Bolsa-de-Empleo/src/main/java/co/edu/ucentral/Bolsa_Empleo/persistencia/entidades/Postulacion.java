package co.edu.ucentral.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "postulaciones")  // Nombre de la tabla en la base de datos
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private EstadoPostulacion estado = EstadoPostulacion.PENDIENTE;

    public enum EstadoPostulacion {
        PENDIENTE, ACEPTADA, RECHAZADA
    }



    @ManyToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "oferta_id", nullable = false)
    private OfertaEmpleo oferta;

    private LocalDate fechaPostulacion;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public OfertaEmpleo getOferta() {
        return oferta;
    }

    public void setOferta(OfertaEmpleo oferta) {
        this.oferta = oferta;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }
}
