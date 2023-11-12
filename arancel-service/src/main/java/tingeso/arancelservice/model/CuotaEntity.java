package tingeso.arancelservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    private Long id;
    private Integer numeroCuota;
    private Integer monto;
    private LocalDate fechaVencimiento;
    private Integer mesesAtraso;
    private Boolean pagado;
    private LocalDate fechaPago;
    private Long idArancel;
}
