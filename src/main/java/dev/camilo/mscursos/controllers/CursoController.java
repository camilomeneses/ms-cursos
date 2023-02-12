package dev.camilo.mscursos.controllers;

import dev.camilo.mscursos.models.entities.Curso;
import dev.camilo.mscursos.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CursoController {

  private final CursoService service;

  @GetMapping
  public ResponseEntity<List<Curso>> listar(){
    return ResponseEntity.ok(service.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle( @PathVariable Long id ){
    Optional <Curso> cursoOptional = service.porId( id );
    if(cursoOptional.isPresent()){
      return ResponseEntity.ok(cursoOptional.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Curso curso){
    Curso cursoDB = service.guardar( curso );
    return ResponseEntity.status( HttpStatus.CREATED ).body( cursoDB );
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Curso curso){
    Optional <Curso> cursoOptional = service.porId( id );
    if(cursoOptional.isPresent()){
      Curso cursoDB = cursoOptional.get();
      cursoDB.setNombre( curso.getNombre() );
      return ResponseEntity.status( HttpStatus.CREATED ).body( service.guardar( cursoDB ) );
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(Long id){
    Optional <Curso> cursoOptional = service.porId( id );
    if(cursoOptional.isPresent()){
      service.eliminar( id );
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
