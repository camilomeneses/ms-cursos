package dev.camilo.mscursos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * clase POJO para traer la data que viene del ms-usuarios
 * es una representacion de un Usuario
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

  private Long id;
  private String nombre;
  private String email;
  private String password;
}
