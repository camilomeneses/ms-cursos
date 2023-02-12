package dev.camilo.mscursos.repositories;

import dev.camilo.mscursos.models.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {
}
