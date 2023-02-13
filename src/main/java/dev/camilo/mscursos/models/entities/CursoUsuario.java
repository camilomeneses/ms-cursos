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

  /**
   * el metodo equals nos sirve para eliminar un usuario en el curso pero por el
   * usuarioId
   * @param obj el usuario que viene en el @RequestBody
   * @return boolean
   */
  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) return true;
    if ( obj == null || getClass() != obj.getClass() ) return false;
    CursoUsuario that = ( CursoUsuario ) obj;
    return Objects.equals( usuarioId, that.usuarioId );
  }

}
