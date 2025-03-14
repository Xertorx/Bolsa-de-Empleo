package co.edu.ucentral.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "InformacionAcademica") // Cambié el nombre para evitar conflictos con "usuarios"
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InformacionAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String cargo;

    @Column(name = "especialidad")
    private String empresa;

    @Column(name = "institucion")
    private String responsabilidades;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "candidato_codigo", referencedColumnName = "id", nullable = false)
    private Candidato candidato;  // Relación con Usuarios
}
