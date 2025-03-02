package co.Bolsa_Empleo.persistencia.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuario {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "nombre")
    private String nombre;



}

