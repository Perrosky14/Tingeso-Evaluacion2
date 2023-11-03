package tingeso.estudianteservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.estudianteservice.entity.EstudianteEntity;
import tingeso.estudianteservice.service.EstudianteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService;

    @GetMapping()
    public ResponseEntity<List<EstudianteEntity>> obtenerEstudiantes() {
        List<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEntity> obtenerEstudiante(@PathVariable("id") Long id) {
        Optional<EstudianteEntity> estudiante = estudianteService.obtenerPorId(id);
        if(estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<EstudianteEntity> guardarEstudiante(@RequestBody EstudianteEntity estudiante) {
        if (estudianteService.validateEstudiante(estudiante)) {
            return ResponseEntity.ok(estudianteService.guardarEstudiante(estudiante));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteEntity> cambiarEstudiante(@PathVariable Long id, @RequestBody EstudianteEntity estudiante) {
        if(estudianteService.obtenerPorId(id).isPresent()) {
            if(estudianteService.validateEstudiante(estudiante)) {
                estudiante.setId(id);
                return ResponseEntity.ok(estudianteService.guardarEstudiante(estudiante));
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EstudianteEntity> eliminarEstudiante(@PathVariable Long id) {
        if(estudianteService.eliminarEstudiante(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
