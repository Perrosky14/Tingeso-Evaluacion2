package tingeso.matriculaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Matriculas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Integer monto;
    private Boolean pagado;
    private LocalDate fechaPagado;
    private Long idEstudiante;
}
