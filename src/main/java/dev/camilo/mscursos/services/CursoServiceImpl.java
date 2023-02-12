package dev.camilo.mscursos.services;

import dev.camilo.mscursos.models.entities.Curso;
import dev.camilo.mscursos.repositories.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService{

  private final CursoRepository repository;
  @Override
  @Transactional(readOnly = true)
  public List <Curso> listar() {
    return ( List <Curso> ) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional <Curso> porId( Long id ) {
    return repository.findById( id );
  }

  @Override
  @Transactional
  public Curso guardar( Curso curso ) {
    return repository.save( curso );
  }

  @Override
  @Transactional
  public void eliminar( Long id ) {
    repository.deleteById( id );
  }
}
