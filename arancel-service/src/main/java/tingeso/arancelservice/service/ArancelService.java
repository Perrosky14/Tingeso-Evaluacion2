package tingeso.arancelservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tingeso.arancelservice.entity.ArancelEntity;
import tingeso.arancelservice.model.CuotaEntity;
import tingeso.arancelservice.model.EstudianteEntity;
import tingeso.arancelservice.repository.ArancelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArancelService {

    @Autowired
    ArancelRepository arancelRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<ArancelEntity> obtenerAranceles() {
        return (ArrayList<ArancelEntity>) arancelRepository.findAll();
    }

    public ArancelEntity guardarArancel(ArancelEntity arancel) {
        return arancelRepository.save(arancel);
    }

    public Optional<ArancelEntity> obtenerPorId(Long id) {
        return arancelRepository.findById(id);
    }

    public Optional<ArancelEntity> obtenerPorEstudianteAsociado(Long idEstudiante) {
        return arancelRepository.buscarPorEstudianteAsociado(idEstudiante);
    }

    public Boolean validateArancel(ArancelEntity arancel) {
        if(arancel.getMonto() <= 0) {
            return false;
        }else if(arancel.getDescuentos() < 0) {
            return false;
        }else if(arancel.getContado() == null) {
            return false;
        }else if(arancel.getPagado() == null) {
            return false;
        }else if(arancel.getCantCuotas() < 0) {
            return false;
        }else if(arancel.getFechaPago() == null) {
            if(arancel.getPagado()) {
                return false;
            }
        }else if(arancel.getInicializacion() == null) {
            return false;
        }else if(arancel.getIdEstudiante() < 0) {
            return false;
        }
        return true;
    }

    public Integer descuentoPorPagoAlContado(ArancelEntity arancel) {
        Integer monto = arancel.getMonto();

        Integer  descuentoACalcular = (monto*50)/100;

        return descuentoACalcular;
    }

    public Integer descuentoPorTipoDeColegioProcedencia(ArancelEntity arancel, String tipoColegioProcedencia) {
        Integer monto = arancel.getMonto();
        Integer descuentoACalcular = 0;

        if(tipoColegioProcedencia.equals("municipal")) {
            descuentoACalcular = (monto*20)/100;
        }else if(tipoColegioProcedencia.equals("subvencionado")) {
            descuentoACalcular = (monto*10)/100;
        }

        return descuentoACalcular;
    }

    public Integer descuentoPorAniosDesdeEgreso(ArancelEntity arancel, Integer aniosDesdeEgreso, LocalDate fecha) {
        Integer monto = arancel.getMonto();
        Integer descuentoACalcular = 0;
        Integer aniosPasadosDesdeEgreso = fecha.getYear() - aniosDesdeEgreso;

        if(aniosPasadosDesdeEgreso < 1) {
            descuentoACalcular = (monto*15)/100;
        }else if(aniosPasadosDesdeEgreso == 1 || aniosPasadosDesdeEgreso == 2) {
            descuentoACalcular = (monto*8)/100;
        }else if(aniosPasadosDesdeEgreso == 3 || aniosPasadosDesdeEgreso == 4) {
            descuentoACalcular = (monto*4)/100;
        }

        return descuentoACalcular;
    }

    public Integer maxCuotasParaArancel(String tipocolegioProcedencia) {
        Integer maxCuotas = 4;

        if (tipocolegioProcedencia.equals("municipal")) {
            maxCuotas = 10;
        }else if(tipocolegioProcedencia.equals("subvencionado")) {
            maxCuotas = 7;
        }

        return maxCuotas;
    }

    public ArrayList<CuotaEntity> buscarCuotas(Long idArancel) {
        try {
            ResponseEntity<ArrayList<CuotaEntity>> response = restTemplate.exchange(
                    "http://gateway-service:8080/cuota/arancel/" + idArancel,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<CuotaEntity>>() {}
            );

            if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            return response.getBody();
        }catch(HttpClientErrorException.NotFound ex) {
            return null;
        }
    }

    public Boolean createCuotas(Long idArancel, Integer cantCuotas, Integer montoFinal) {
        Integer montoCuota = montoFinal/cantCuotas;
        LocalDate fechaParaCuotas = LocalDate.now();
        for(Integer i = 0; i < cantCuotas; ++i) {
            CuotaEntity cuota = new CuotaEntity();
            cuota.setNumeroCuota(i+1);
            cuota.setMonto(montoCuota);
            if(fechaParaCuotas.getMonthValue() == 12) {
                fechaParaCuotas = fechaParaCuotas.withMonth(1);
                fechaParaCuotas = fechaParaCuotas.withYear(fechaParaCuotas.getYear()+1);
            }else {
                fechaParaCuotas = fechaParaCuotas.withMonth(fechaParaCuotas.getMonthValue()+1);
            }
            cuota.setDescuento(0);
            cuota.setMesesAtraso(0);
            cuota.setFechaVencimiento(fechaParaCuotas);
            cuota.setMesesAtraso(0);
            cuota.setPagado(false);
            cuota.setFechaPago(null);
            cuota.setIdArancel(idArancel);
            try {
                ResponseEntity<CuotaEntity> response = restTemplate.postForEntity(
                        "http://gateway-service:8080/cuota",
                        cuota,
                        CuotaEntity.class
                );

                if(response.getStatusCode() == HttpStatus.NOT_FOUND || response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                    return false;
                }
            }catch(HttpClientErrorException.NotFound ex) {
                return false;
            }
        }
        return true;
    }

    public EstudianteEntity findByIdEstudiante(Long idEstudiante) {
        try {
            ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                    "http://gateway-service:8080/estudiante/" + idEstudiante,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EstudianteEntity>() {}
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
        String url = "http://gateway-service:8080/cuota/" + id;
        try {
            restTemplate.delete(url);
            return true;
        } catch (HttpClientErrorException ex) {
            return false;
        }
    }

    public Boolean eliminarArancel(Long id) {
        try {
            arancelRepository.deleteById(id);
            return true;
        }catch(Exception err) {
            return false;
        }
    }

}
