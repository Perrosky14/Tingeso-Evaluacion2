package tingeso.cuotaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.cuotaservice.entity.CuotaEntity;
import tingeso.cuotaservice.model.ArancelEntity;
import tingeso.cuotaservice.service.CuotaService;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/cuota")
public class CuotaController {

    @Autowired
    CuotaService cuotaService;

    @GetMapping()
    public ResponseEntity<ArrayList<CuotaEntity>> obtenerCuotas() {
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCuotas();
        return ResponseEntity.ok(cuotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuotaEntity> obtenerCuotaPorId(@PathVariable("id") Long id) {
        Optional<CuotaEntity> cuota = cuotaService.obtenerPorId(id);
        if(cuota.isPresent()) {
            return ResponseEntity.ok(cuota.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/arancel/{id}")
    public ResponseEntity<ArrayList<CuotaEntity>> obtenerCuotasPorArancelAsociado(@PathVariable("id") Long id) {
        ArancelEntity arancel = cuotaService.findByIdArancel(id);
        if(arancel != null) {
            if(!arancel.getContado()) {
                ArrayList<CuotaEntity> cuotas = cuotaService.obtenerPorArancelAsociado(id);
                return ResponseEntity.ok(cuotas);
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<CuotaEntity> guardarCuota(@RequestBody CuotaEntity cuota) {
        ArancelEntity arancel = cuotaService.findByIdArancel(cuota.getIdArancel());
        if(arancel != null) {
            if(cuotaService.validateCuota(cuota)) {
                return ResponseEntity.ok(cuotaService.guardarCuota(cuota));
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuotaEntity> cambiarCuota(@PathVariable Long id, @RequestBody CuotaEntity cuota) {
        if(cuotaService.obtenerPorId(id).isPresent()) {
            ArancelEntity arancel = cuotaService.findByIdArancel(cuota.getIdArancel());
            if(arancel != null) {
                if(cuotaService.validateCuota(cuota)) {
                    cuota.setId(id);
                    return ResponseEntity.ok(cuotaService.guardarCuota(cuota));
                }
                return ResponseEntity.unprocessableEntity().build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CuotaEntity> eliminarCuota(@PathVariable Long id) {
        if(cuotaService.eliminarCuota(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
