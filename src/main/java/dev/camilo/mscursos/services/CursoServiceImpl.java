package dev.camilo.mscursos.services;

import dev.camilo.mscursos.clients.UsuarioClientRest;
import dev.camilo.mscursos.models.Usuario;
import dev.camilo.mscursos.models.entities.Curso;
import dev.camilo.mscursos.models.entities.CursoUsuario;
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

  private final UsuarioClientRest usuarioClient;
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

  @Override
  @Transactional
  public Optional <Usuario> asignarUsuario( Usuario usuario, Long cursoId ) {

    /*Verificamos la existencia del curso en el repositorio*/
    Optional <Curso> cursoDB = repository.findById( cursoId );
    if(cursoDB.isPresent()){
      /*Verificamos la existencia del usuario en el ms-usuarios */
      Usuario usuarioMs = usuarioClient.detalle( usuario.getId() );

      /* Instanciamos el curso del repo y agregamos la relacion del usuario en la
      * tabla curso_usuarios */
      Curso curso = cursoDB.get();
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId( usuarioMs.getId() );
      curso.addCursoUsuario( cursoUsuario );

      /* Guardamos el curso con el nuevo cambio y retornamos el usuario traido desde
      * ms-usuarios, sino existe se devolvera un optional vacio, para posteriormente
      * en el controllador ser manejado la excepcion FeignException*/
      repository.save( curso );
      return Optional.of( usuarioMs );
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional <Usuario> crearUsuario( Usuario usuario, Long cursoId ) {

    /*Verificamos la existencia del curso en el repositorio*/
    Optional <Curso> cursoDB = repository.findById( cursoId );
    if(cursoDB.isPresent()){
      /* Llevamos el usuario hacia el ms-usuarios para su creacion */
      Usuario usuarioNuevoMs = usuarioClient.crear( usuario );

      /* Instanciamos el curso del repo y agregamos la relacion del usuario en la
       * tabla curso_usuarios */
      Curso curso = cursoDB.get();
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId( usuarioNuevoMs.getId() );
      curso.addCursoUsuario( cursoUsuario );

      /* Guardamos el curso con el nuevo cambio y retornamos el usuario traido desde
       * ms-usuarios, sino existe se devolvera un optional vacio, para posteriormente
       * en el controllador ser manejado la excepcion FeignException*/
      repository.save( curso );
      return Optional.of( usuarioNuevoMs );
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional <Usuario> desasignarUsuario( Usuario usuario, Long cursoId ) {

    /*Verificamos la existencia del curso en el repositorio*/
    Optional <Curso> cursoDB = repository.findById( cursoId );
    if(cursoDB.isPresent()){
      /*Verificamos la existencia del usuario en el ms-usuarios */
      Usuario usuarioMs = usuarioClient.detalle( usuario.getId() );

      /* Instanciamos el curso del repo y quitamos la relacion del usuario en la
       * tabla curso_usuarios */
      Curso curso = cursoDB.get();
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId( usuarioMs.getId() );
      curso.removeCursoUsuario( cursoUsuario );

      /* Guardamos el curso con el nuevo cambio y retornamos el usuario traido desde
       * ms-usuarios, sino existe se devolvera un optional vacio, para posteriormente
       * en el controllador ser manejado la excepcion FeignException*/
      repository.save( curso );
      return Optional.of( usuarioMs );
    }
    return Optional.empty();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional <Curso> porIdConUsuarios( Long id ) {

    /*Verificamos la existencia del curso en el repositorio */
    Optional <Curso> cursoDB = repository.findById( id );
    if(cursoDB.isPresent()){
      Curso curso = cursoDB.get();
      /* Verificamos que el curso desde la persistencia tenga poblado los usuarios
      * en la tabla curso_usuarios, si esta poblado sacamos un List con los ids de los
      * usuarios correspondientes */
      if(!curso.getCursoUsuarios().isEmpty()){
        List<Long> ids = curso.getCursoUsuarios()
            .stream()
            .map( cursoUsuario -> cursoUsuario.getUsuarioId() )
            .toList();

        /* realizamos la consulta Http Feign donde traemos los usuarios segun los ids
        * y luego seteamos los usuarios al curso consultado al comienzo */
        List <Usuario> usuarios = usuarioClient.obtenerAlumnosPorCurso( ids );
        curso.setUsuarios( usuarios );
      }
      /* devolvemos un Optional basado en la informacion del curso ya con los usuarios,
      * si no devolvemos un Optional vacio */
      return Optional.of( curso );
    }
    return Optional.empty();
  }
}
