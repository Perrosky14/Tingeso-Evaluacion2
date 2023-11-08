package tingeso.matriculaservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tingeso.matriculaservice.entity.MatriculaEntity;

import java.util.Optional;

@Repository
public interface MatriculaRepository extends CrudRepository<MatriculaEntity, Long> {
    @Query("SELECT m FROM MatriculaEntity m WHERE m.idEstudiante = ?1")
    public Optional<MatriculaEntity> buscarPorEstudianteAsociado(Long idEstudiante);
}
