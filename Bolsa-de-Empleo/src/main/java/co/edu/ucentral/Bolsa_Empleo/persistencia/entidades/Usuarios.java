package co.edu.ucentral.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "tipo_identificacion")
    private String tipo_identificacion;

    @Column(name = "numero_identificacion")
    private Integer numero_identificacion;

    @Column(name = "telefono")
    private String telefono;

    @Getter
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "codigo", nullable = false)
    private TipoUsuario tipo_usuario;


}

