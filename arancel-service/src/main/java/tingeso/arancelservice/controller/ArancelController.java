package tingeso.arancelservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.arancelservice.entity.ArancelEntity;
import tingeso.arancelservice.model.EstudianteEntity;
import tingeso.arancelservice.service.ArancelService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/arancel")
public class ArancelController {

    @Autowired
    ArancelService arancelService;

    @GetMapping()
    public ResponseEntity<ArrayList<ArancelEntity>> obtenerAranceles() {
        ArrayList<ArancelEntity> aranceles = arancelService.obtenerAranceles();
        return ResponseEntity.ok(aranceles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArancelEntity> obtenerArancelPorId(@PathVariable Long id) {
        Optional<ArancelEntity> arancel = arancelService.obtenerPorId(id);
        if(arancel.isPresent()) {
            if(!arancel.get().getInicializacion()) {
                if(arancel.get().getContado()) {
                    arancel.get().setDescuentos(arancel.get().getMonto()-arancelService.descuentoPorPagoAlContado(arancel.get()));
                }else {
                    EstudianteEntity estudiante = arancelService.findByIdEstudiante(arancel.get().getIdEstudiante());
                    Integer descuentoPorColegio = arancelService.descuentoPorTipoDeColegioProcedencia(arancel.get(), estudiante.getTipoColegioProcedencia());
                    Integer descuentoPorEgreso = arancelService.descuentoPorAniosDesdeEgreso(arancel.get(), estudiante.getAnioEgreso(), LocalDate.now());
                    Integer descuento = arancel.get().getMonto() - (descuentoPorColegio + descuentoPorEgreso);
                    arancel.get().setDescuentos(descuento);
                    arancel.get().setCantCuotas(arancelService.maxCuotasParaArancel(estudiante.getTipoColegioProcedencia()));
                }
            }
            return ResponseEntity.ok(arancel.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<ArancelEntity> obtenerArancelPorEstudiante(@PathVariable Long id) {
        Optional<ArancelEntity> arancel = arancelService.obtenerPorEstudianteAsociado(id);
        if (arancel.isPresent()) {
            if(!arancel.get().getInicializacion()) {
                if(arancel.get().getContado()) {
                    arancel.get().setDescuentos(arancel.get().getMonto()-arancelService.descuentoPorPagoAlContado(arancel.get()));
                }else {
                    EstudianteEntity estudiante = arancelService.findByIdEstudiante(arancel.get().getIdEstudiante());
                    Integer descuentoPorColegio = arancelService.descuentoPorTipoDeColegioProcedencia(arancel.get(), estudiante.getTipoColegioProcedencia());
                    Integer descuentoPorEgreso = arancelService.descuentoPorAniosDesdeEgreso(arancel.get(), estudiante.getAnioEgreso(), LocalDate.now());
                    Integer descuento = arancel.get().getMonto() - (descuentoPorColegio + descuentoPorEgreso);
                    arancel.get().setDescuentos(descuento);
                    arancel.get().setCantCuotas(arancelService.maxCuotasParaArancel(estudiante.getTipoColegioProcedencia()));
                }
            }
            return ResponseEntity.ok(arancel.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<ArancelEntity> guardarArancel(@RequestBody ArancelEntity arancel) {
        EstudianteEntity estudiante = arancelService.findByIdEstudiante(arancel.getIdEstudiante());
        if(estudiante != null) {
            if (arancelService.validateArancel(arancel)){
                return ResponseEntity.ok(arancelService.guardarArancel(arancel));
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArancelEntity> cambiarArancel(@PathVariable Long id, @RequestBody ArancelEntity arancel) {
        if(arancelService.obtenerPorId(id).isPresent()) {
            EstudianteEntity estudiante = arancelService.findByIdEstudiante(arancel.getIdEstudiante());
            if (estudiante != null) {
                if(arancelService.validateArancel(arancel)) {
                    arancel.setId(id);
                    return ResponseEntity.ok(arancelService.guardarArancel(arancel));
                }
                return ResponseEntity.unprocessableEntity().build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/cantidad-cuotas/{id}")
    public ResponseEntity<ArancelEntity> cantidadCuotas(@PathVariable Long id, @RequestBody ArancelEntity arancel) {
        if(arancelService.obtenerPorId(id).isPresent()) {
            EstudianteEntity estudiante = arancelService.findByIdEstudiante(arancel.getIdEstudiante());
            if(estudiante != null) {
                Integer diferencia = arancel.getMonto() - arancel.getDescuentos();
                if(arancelService.validateArancel(arancel) && arancelService.createCuotas(id, arancel.getCantCuotas(), diferencia)) {
                    arancel.setId(id);
                    return ResponseEntity.ok(arancelService.guardarArancel(arancel));
                }
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArancelEntity> eliminarArancel(@PathVariable Long id) {
        if(arancelService.eliminarArancel(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
