package dev.camilo.mscursos.services;

import dev.camilo.mscursos.models.Usuario;
import dev.camilo.mscursos.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

  List<Curso> listar();
  Optional<Curso> porId(Long id);
  Curso guardar(Curso curso);
  void eliminar(Long id);

  // Para comunicacion Feign con ms-usuarios
  Optional<Usuario> asignarUsuario( Usuario usuario, Long cursoId);
  Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> desasignarUsuario(Usuario usuario, Long cursoId);
}
