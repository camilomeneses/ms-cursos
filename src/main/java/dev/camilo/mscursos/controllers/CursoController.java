package dev.camilo.mscursos.controllers;

import dev.camilo.mscursos.models.Usuario;
import dev.camilo.mscursos.models.entities.Curso;
import dev.camilo.mscursos.services.CursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/" )
public class CursoController {

  private final CursoService service;

  @GetMapping
  public ResponseEntity <List <Curso>> listar() {

    return ResponseEntity.ok( service.listar() );
  }

  @GetMapping( "/{id}" )
  public ResponseEntity <?> detalle( @PathVariable Long id ) {

    Optional <Curso> cursoOptional = service.porId( id );
    if ( cursoOptional.isPresent() ) {
      return ResponseEntity.ok( cursoOptional.get() );
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity <?> crear( @Valid @RequestBody Curso curso, BindingResult result ) {

    if ( result.hasErrors() ) {
      return validar( result );
    }
    Curso cursoDB = service.guardar( curso );
    return ResponseEntity.status( HttpStatus.CREATED ).body( cursoDB );
  }

  @PutMapping( "/{id}" )
  public ResponseEntity <?> editar( @Valid @PathVariable Long id, @RequestBody Curso curso, BindingResult result ) {

    if ( result.hasErrors() ) {
      return validar( result );
    }
    Optional <Curso> cursoOptional = service.porId( id );
    if ( cursoOptional.isPresent() ) {
      Curso cursoDB = cursoOptional.get();
      cursoDB.setNombre( curso.getNombre() );
      return ResponseEntity.status( HttpStatus.CREATED ).body( service.guardar( cursoDB ) );
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping( "/{id}" )
  public ResponseEntity <?> eliminar( @PathVariable Long id ) {

    Optional <Curso> cursoOptional = service.porId( id );
    if ( cursoOptional.isPresent() ) {
      service.eliminar( id );
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  // metodos privados de controlador
  private static ResponseEntity <Map <String, String>> validar( BindingResult result ) {

    Map <String, String> errores = new HashMap <>();
    result.getFieldErrors().forEach( err -> {
      errores.put( err.getField(), "El campo ".concat( err.getField() ).concat( " " ).concat( err.getDefaultMessage() ) );
    } );
    return ResponseEntity.badRequest().body( errores );
  }

  // metodos para cliente ms-usuarios
  @PutMapping( "/asignar-usuario/{cursoId}" )
  public ResponseEntity <?> asignarUsuario( @RequestBody Usuario usuario, @PathVariable Long cursoId ) {

    /* Creamos la varible para traer el Optional del service*/
    Optional <Usuario> usuarioOptional;

    /* Teniendo en cuenta algun error de no encontrar el usuario por el id o exista un error por delay o otro motivo
    * con el ms-usuarios lanzara una exception personalizada  */
    try {
      usuarioOptional = service.asignarUsuario( usuario, cursoId );
    } catch ( FeignException e ) {
      return ResponseEntity.status( HttpStatus.NOT_FOUND )
          .body( Collections.singletonMap( "error", "No existe el usuario por el id o error en la comunicacion".concat( e.getMessage() ) ) );
    }

    /* si todo sale bien entonces, respondemos con el usuario asignado al curso sino con un estado no encontrado */
    if ( usuarioOptional.isPresent() ) {
      return ResponseEntity.status( HttpStatus.CREATED ).body( usuarioOptional.get() );
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping( "/crear-usuario/{cursoId}" )
  public ResponseEntity <?> crearUsuario( @RequestBody Usuario usuario, @PathVariable Long cursoId ) {

    /* Creamos la varible para traer el Optional del service*/
    Optional <Usuario> usuarioOptional;

    /* Teniendo en cuenta algun error por delay o otro motivo
     * con el ms-usuarios lanzara una exception personalizada  */
    try {
      usuarioOptional = service.crearUsuario( usuario, cursoId );
    } catch ( FeignException e ) {
      return ResponseEntity.status( HttpStatus.NOT_FOUND )
          .body( Collections.singletonMap( "error", "No se pudo crear el usuario o error en la comunicacion".concat( e.getMessage() ) ) );
    }

    /* si todo sale bien entonces, respondemos con el usuario asignado al curso sino con un estado no encontrado */
    if ( usuarioOptional.isPresent() ) {
      return ResponseEntity.status( HttpStatus.CREATED ).body( usuarioOptional.get() );
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping( "/desasignar-usuario/{cursoId}" )
  public ResponseEntity <?> desasignarUsuario( @RequestBody Usuario usuario, @PathVariable Long cursoId ) {

    /* Creamos la varible para traer el Optional del service*/
    Optional <Usuario> usuarioOptional;

    /* Teniendo en cuenta algun error de no encontrar el usuario por el id o exista un error por delay o otro motivo
     * con el ms-usuarios lanzara una exception personalizada  */
    try {
      usuarioOptional = service.desasignarUsuario( usuario, cursoId );
    } catch ( FeignException e ) {
      return ResponseEntity.status( HttpStatus.NOT_FOUND )
          .body( Collections.singletonMap( "error", "No existe el usuario por el id o error en la comunicacion".concat( e.getMessage() ) ) );
    }

    /* si todo sale bien entonces, respondemos con el usuario asignado al curso sino con un estado no encontrado */
    if ( usuarioOptional.isPresent() ) {
      return ResponseEntity.status( HttpStatus.OK ).body( usuarioOptional.get() );
    }
    return ResponseEntity.notFound().build();
  }
}
