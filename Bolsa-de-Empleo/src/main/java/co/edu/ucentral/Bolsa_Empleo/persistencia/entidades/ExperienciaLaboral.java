package co.edu.ucentral.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "experiencia_laboral") // Cambié el nombre para evitar conflictos con "usuarios"
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExperienciaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencia_experiencia")
    @SequenceGenerator(name = "sequencia_experiencia", sequenceName = "sequencia_experiencia", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "responsabilidades")
    private String responsabilidades;

    @Column(name = "fecha_inicio")
    private String fechaInicio;

    @Column(name = "fecha_finalizacion")
    private String fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "candidato_codigo", referencedColumnName = "id", nullable = false)
    private Candidato candidato;  // Relación con Candidato
}
