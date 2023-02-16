package dev.camilo.mscursos.clients;

import dev.camilo.mscursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-usuarios", url = "${ms.usuarios.url}")
public interface UsuarioClientRest {

  @GetMapping("/{id}")
  Usuario detalle( @PathVariable Long id );

  @PostMapping("/")
  Usuario crear( @RequestBody Usuario usuario );

  @GetMapping("/usuarios-por-curso")
  List<Usuario> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
