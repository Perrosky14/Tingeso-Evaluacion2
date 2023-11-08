package tingeso.matriculaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.matriculaservice.entity.MatriculaEntity;
import tingeso.matriculaservice.model.EstudianteEntity;
import tingeso.matriculaservice.service.MatriculaService;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    @Autowired
    MatriculaService matriculaService;

    @GetMapping()
    public ResponseEntity<ArrayList<MatriculaEntity>> obtenerMatriculas() {
        ArrayList<MatriculaEntity> matriculas = matriculaService.obtenerMatriculas();
        return ResponseEntity.ok(matriculas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaEntity> obtenerMatriculaPorId(@PathVariable Long id) {
        Optional<MatriculaEntity> matricula = matriculaService.obtenerPorId(id);
        if(matricula.isPresent()) {
            return ResponseEntity.ok(matricula.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<MatriculaEntity> obtenerMatriculaPorEstudianteAsociado(@PathVariable Long id) {
        Optional<MatriculaEntity> matricula = matriculaService.obtenerPorEstudianteAsociado(id);
        if(matricula.isPresent()) {
            return ResponseEntity.ok(matricula.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<MatriculaEntity> guardarMatricula(@RequestBody MatriculaEntity matricula) {
        EstudianteEntity estudiante = matriculaService.findByIdEstudiante(matricula.getIdEstudiante());
        if(estudiante != null) {
            if(matriculaService.validateMatricula(matricula)) {
                return ResponseEntity.ok(matriculaService.guardarMatricula(matricula));
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaEntity> cambiarMatricula(@PathVariable Long id, @RequestBody MatriculaEntity matricula) {
        if(matriculaService.obtenerPorId(id).isPresent()) {
            EstudianteEntity estudiante = matriculaService.findByIdEstudiante(matricula.getIdEstudiante());
            if(estudiante != null) {
                if(matriculaService.validateMatricula(matricula)) {
                    matricula.setId(id);
                    return ResponseEntity.ok(matriculaService.guardarMatricula(matricula));
                }
                return ResponseEntity.unprocessableEntity().build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MatriculaEntity> eliminarMatricula(@PathVariable Long id) {
        if(matriculaService.eliminarMatricula(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}