package co.edu.ucentral.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "postulaciones")  // Nombre de la tabla en la base de datos
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "estado", nullable = false)
    private String estado; // "PENDIENTE", "ACEPTADO" o "RECHAZADO"



    public Postulacion() {
        this.fechaPostulacion = LocalDate.now();
        this.estado = "PENDIENTE"; // Estado inicial
    }

    public Postulacion(OfertaEmpleo oferta, Candidato candidato) {
        this.oferta = oferta;
        this.candidato = candidato;
        this.fechaPostulacion = LocalDate.now();
        this.estado = "PENDIENTE";
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    @Override
    public String toString() {
        return "Postulacion{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", fechaPostulacion=" + fechaPostulacion +
                ", candidato=" + (candidato != null ? candidato.getId() : "null") +
                ", oferta=" + (oferta != null ? oferta.getId() : "null") +
                '}';
    }

}
