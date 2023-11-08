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

    public EstudianteEntity findByIdEstudiante(Long idEstudiante) {
        try {
            ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                    "http://localhost:8080/estudiante/" + idEstudiante,
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

    public Boolean eliminarArancel(Long id) {
        try {
            arancelRepository.deleteById(id);
            return true;
        }catch(Exception err) {
            return false;
        }
    }

}
