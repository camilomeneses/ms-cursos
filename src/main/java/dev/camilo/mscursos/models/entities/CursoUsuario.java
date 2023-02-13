package dev.camilo.mscursos.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "curso_usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoUsuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "usuario_id", unique = true)
  private Long usuarioId;

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;
    CursoUsuario that = ( CursoUsuario ) o;
    return Objects.equals( id, that.id );
  }

}
