package tingeso.cuotaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tingeso.cuotaservice.entity.CuotaEntity;
import tingeso.cuotaservice.model.ArancelEntity;
import tingeso.cuotaservice.repository.CuotaRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CuotaService {

    @Autowired
    CuotaRepository cuotaRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<CuotaEntity> obtenerCuotas() {
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
    }

    public CuotaEntity guardarCuota(CuotaEntity cuota) {
        return cuotaRepository.save(cuota);
    }

    public Optional<CuotaEntity> obtenerPorId(Long id) {
        return cuotaRepository.findById(id);
    }

    public ArrayList<CuotaEntity> obtenerPorArancelAsociado(Long idArancel) {
        return cuotaRepository.buscarPorArancelAsociado(idArancel);
    }

    public Boolean validateCuota(CuotaEntity cuota) {
        if(cuota.getNumeroCuota() <= 0) {
            return false;
        }else if(cuota.getMonto() <= 0) {
            return false;
        }else if(cuota.getFechaVencimiento() == null) {
            return false;
        }else if(cuota.getMesesAtraso() < 0) {
            return false;
        }else if(cuota.getPagado() == null) {
            return false;
        }else if(cuota.getFechaPago() == null) {
            if(cuota.getPagado()) {
                return false;
            }
        }
        return true;
    }

    public Integer calcularMesesAtraso(CuotaEntity cuota, LocalDate fechaActual) {
        Integer mesesAtraso = 0;

        Long diferenciaEnMeses = ChronoUnit.MONTHS.between(cuota.getFechaVencimiento(), fechaActual);
        if(diferenciaEnMeses > 0) {
            mesesAtraso = diferenciaEnMeses.intValue();

            Period periodo = Period.between(cuota.getFechaVencimiento(), fechaActual);
            if(periodo.getDays() > 0) {
                mesesAtraso = mesesAtraso + 1;
            }
        }

        return mesesAtraso;
    }

    public Integer calcularTasaInteresMesesAtraso(CuotaEntity cuota, Integer mesesAtraso) {
        Integer interes = 0;
        Integer monto = cuota.getMonto();

        if(mesesAtraso == 1) {
            interes = (monto*3)/100;
        }else if(mesesAtraso == 2) {
            interes = (monto*6)/100;
        }else if(mesesAtraso == 3) {
            interes = (monto*9)/100;
        }else if(mesesAtraso > 3) {
            interes = (monto*15)/100;
        }

        return interes;
    }

    public ArancelEntity findByIdArancel(Long idArancel) {
        try {
            ResponseEntity<ArancelEntity> response = restTemplate.exchange(
                    "http://gateway-service:8080/arancel/" + idArancel,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArancelEntity>() {}
            );

            if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            return response.getBody();
        }catch(HttpClientErrorException.NotFound ex) {
            return null;
        }
    }

    public Boolean eliminarCuota(Long id) {
        try {
            cuotaRepository.deleteById(id);
            return true;
        }catch(Exception err) {
            return false;
        }
    }

}
