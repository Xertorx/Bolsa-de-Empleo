package co.edu.ucentral.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "oferta_empleo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfertaEmpleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos generales de la oferta
    private String titulo;           // Título o nombre del puesto
    @Column(length = 2000)
    private String descripcion;      // Descripción detallada del cargo

    private Integer numeroVacantes;  // Número de vacantes disponibles

    // Ubicación
    private String pais;
    private String ciudad;
    private String direccionOferta;

    // Contrato y jornada
    private String duracionContrato; // Ej: "6 meses", "1 año"
    private String horario;         // Ej: "8am - 5pm"

    // Requisitos
    @Column(length = 1000)
    private String requisitosMinimos;   // Nivel de estudios, experiencia, idiomas, etc.
    @Column(length = 1000)
    private String requisitosDeseables; // Requisitos deseables (opcionales)

    // Otros datos
    private Double salario;
    private String categoria;           // Clasificación o categoría del empleo
    private LocalDate fechaPublicacion;
    private Boolean activa;             // true si está disponible

    // Relación con la Empresa que publica la oferta
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}
