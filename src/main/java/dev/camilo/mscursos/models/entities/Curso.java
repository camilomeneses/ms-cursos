package dev.camilo.mscursos.models.entities;

import dev.camilo.mscursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "cursos" )
@Getter
@Setter
@AllArgsConstructor
public class Curso {

  @Id
  @GeneratedValue( strategy = GenerationType.SEQUENCE )
  private Long id;

  @NotBlank
  private String nombre;

  /**
   * @OnetoMany manejara la relacion de un curso para muchos usuarios
   * CursoUsuario tiene el [id] de relacion y [usuario_id]
   * @JoinColumn manejara [curso_id] la relacion foranea del curso en CursoUsuario
   */
  @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
  @JoinColumn( name = "curso_id" )
  private List <CursoUsuario> cursoUsuarios;

  /**
   * este atributo de usuarios no se va a pasar a la persistencia
   * por lo tanto se anota con @Transient
   */
  @Transient
  private List <Usuario> usuarios;

  /* Inicializamos los List */
  public Curso() {
    cursoUsuarios = new ArrayList <>();
    usuarios = new ArrayList <>();
  }

  /* Metodos para agregar y quitar usuario de Curso uno por uno */
  public void addCursoUsuario( CursoUsuario cursoUsuario ) {
    cursoUsuarios.add( cursoUsuario );
  }

  /**
   * esta eliminacion necesita el metodo equals en
   * CursoUsuario para eliminar haciendo una comparacion por id
   */
  public void removeCursoUsuario( CursoUsuario cursoUsuario ) {
    cursoUsuarios.remove( cursoUsuario );
  }
}
