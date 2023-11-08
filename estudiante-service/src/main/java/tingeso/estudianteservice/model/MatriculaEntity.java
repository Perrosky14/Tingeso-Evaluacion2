package tingeso.estudianteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaEntity {
    private Long id;
    private Integer monto;
    private Boolean pagado;
    private LocalDate fechaPagado;
    private Long idEstudiante;
}
