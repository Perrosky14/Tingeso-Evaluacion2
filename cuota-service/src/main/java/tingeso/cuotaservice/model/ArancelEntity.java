package tingeso.cuotaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArancelEntity {
    private Long id;

    private Integer monto;
    private Integer descuentos;
    private Boolean contado;
    private Boolean pagado;
    private Integer cantCuotas;
    private LocalDate fechaPago;
    private Boolean inicializacion;
    private Long idEstudiante;
}
