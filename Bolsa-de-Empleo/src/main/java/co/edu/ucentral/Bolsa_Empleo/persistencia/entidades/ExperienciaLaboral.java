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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "responsabilidades")
    private String responsabilidades;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "usuario_codigo", referencedColumnName = "codigo", nullable = false)
    private Usuarios usuario;  // Relación con Usuarios
}
