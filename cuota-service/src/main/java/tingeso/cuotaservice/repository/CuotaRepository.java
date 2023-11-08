package tingeso.cuotaservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tingeso.cuotaservice.entity.CuotaEntity;

import java.util.ArrayList;

@Repository
public interface CuotaRepository extends CrudRepository<CuotaEntity, Long> {
    @Query("SELECT c FROM CuotaEntity c WHERE c.idArancel = ?1")
    public ArrayList<CuotaEntity> buscarPorArancelAsociado(Long idArancel);
}
