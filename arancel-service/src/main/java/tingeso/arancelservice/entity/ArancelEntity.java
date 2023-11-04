package tingeso.arancelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Aranceles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArancelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
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
