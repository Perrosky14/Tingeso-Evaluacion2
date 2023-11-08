package tingeso.matriculaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    private Long id;
    private String rut;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private String tipoColegioProcedencia;
    private String nombreColegio;
    private Integer anioEgreso;
}
