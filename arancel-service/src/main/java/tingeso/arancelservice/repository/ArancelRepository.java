package tingeso.arancelservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tingeso.arancelservice.entity.ArancelEntity;

import java.util.Optional;

@Repository
public interface ArancelRepository extends CrudRepository<ArancelEntity, Long> {
    @Query("SELECT a FROM ArancelEntity a WHERE a.idEstudiante = ?1")
    public Optional<ArancelEntity> buscarPorEstudianteAsociado(Long idEstudiante);
}
