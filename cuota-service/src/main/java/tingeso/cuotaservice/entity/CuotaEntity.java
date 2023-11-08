package tingeso.cuotaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Cuotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Integer numeroCuota;
    private Integer monto;
    private LocalDate fechaVencimiento;
    private Integer mesesAtraso;
    private Boolean pagado;
    private LocalDate fechaPago;
    private Long idArancel;
}
