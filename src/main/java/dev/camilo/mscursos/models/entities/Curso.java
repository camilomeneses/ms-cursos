package dev.camilo.mscursos.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@AllArgsConstructor
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotBlank
  private String nombre;

  /* Un solo curso puede tener muchos usuarios */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CursoUsuario> cursoUsuarios;

  /* Inicializamos el List de cursoUsuarios */
  public Curso(){
    cursoUsuarios = new ArrayList <>();
  }

  /* Metodos para agregar y quitar usuario de Curso uno por uno */
  public void addCursoUsuario(CursoUsuario cursoUsuario){
    cursoUsuarios.add( cursoUsuario );
  }
  public void removeCursoUsuario(CursoUsuario cursoUsuario){
    /**
     * esta eliminacion necesita el metodo equals en
     * CursoUsuario para eliminar haciendo una comparacion por id
     */
    cursoUsuarios.remove( cursoUsuario );
  }
}
