package tingeso.estudianteservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tingeso.estudianteservice.entity.EstudianteEntity;
import tingeso.estudianteservice.model.ArancelEntity;
import tingeso.estudianteservice.model.MatriculaEntity;
import tingeso.estudianteservice.repository.EstudianteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<EstudianteEntity> obtenerEstudiantes() {
        return (List<EstudianteEntity>) estudianteRepository.findAll();
    }

    public EstudianteEntity guardarEstudiante(EstudianteEntity estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Optional<EstudianteEntity> obtenerPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Boolean validateEstudiante(EstudianteEntity estudiante) {
        if(estudiante.getRut().equals("")) {
            return false;
        }else if(estudiante.getPrimerNombre().equals("")) {
            return false;
        }else if(estudiante.getSegundoNombre().equals("")) {
            return false;
        }else if(estudiante.getPrimerApellido().equals("")) {
            return false;
        }else if(estudiante.getSegundoApellido().equals("")) {
            return false;
        }else if(estudiante.getFechaNacimiento() == null) {
            return false;
        }else if(!(estudiante.getTipoColegioProcedencia().equals("municipal") || estudiante.getTipoColegioProcedencia().equals("privado") || estudiante.getTipoColegioProcedencia().equals("subvencionado"))) {
            return false;
        }else if(estudiante.getNombreColegio().equals("")) {
            return false;
        }else if(!(estudiante.getAnioEgreso() > 0 && estudiante.getAnioEgreso() > 1940 && estudiante.getAnioEgreso() < 2024)) {
            return false;
        }
        return true;
    }

    public ArancelEntity buscarArancel(Long idEstudiante) {
        try {
            ResponseEntity<ArancelEntity> response = restTemplate.exchange(
                    "http://gateway-service:8080/arancel/estudiante/" + idEstudiante,
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

    public MatriculaEntity buscarMatricula(Long idEstudiante) {
        try {
            ResponseEntity<MatriculaEntity> response = restTemplate.exchange(
                    "http://gateway-service:8080/matricula/estudiante/" + idEstudiante,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<MatriculaEntity>() {}
            );

            if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            return response.getBody();
        }catch(HttpClientErrorException.NotFound ex) {
            return null;
        }
    }

    public Boolean createArancel(Long idEstudiante) {
        ArancelEntity arancel = new ArancelEntity();
        arancel.setMonto(1500000);
        arancel.setDescuentos(0);
        arancel.setContado(true);
        arancel.setPagado(false);
        arancel.setCantCuotas(0);
        arancel.setFechaPago(null);
        arancel.setInicializacion(true);
        arancel.setIdEstudiante(idEstudiante);
        try {
            ResponseEntity<ArancelEntity> response = restTemplate.postForEntity(
                    "http://gateway-service:8080/arancel",
                    arancel,
                    ArancelEntity.class
            );

            if(response.getStatusCode() == HttpStatus.NOT_FOUND || response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                return false;
            }
            return true;
        }catch(HttpClientErrorException.NotFound ex) {
            return false;
        }
    }

    public Boolean createMatricula(Long idEstudiante) {
        MatriculaEntity matricula = new MatriculaEntity();
        matricula.setMonto(70000);
        matricula.setPagado(false);
        matricula.setFechaPagado(null);
        matricula.setIdEstudiante(idEstudiante);
        try {
            ResponseEntity<MatriculaEntity> response = restTemplate.postForEntity(
                    "http://gateway-service:8080/matricula",
                    matricula,
                    MatriculaEntity.class
            );

            if(response.getStatusCode() == HttpStatus.NOT_FOUND || response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                return false;
            }
            return true;
        }catch(HttpClientErrorException.NotFound ex) {
            return false;
        }
    }

    public Boolean eliminarArancel(Long id) {
        String url = "http://gateway-service:8080/arancel/" + id;
        try {
            restTemplate.delete(url);
            return true;
        } catch (HttpClientErrorException ex) {
            return false;
        }
    }

    public Boolean eliminarMatricula(Long id) {
        String url = "http://gateway-service:8080/matricula/" + id;
        try {
            restTemplate.delete(url);
            return true;
        } catch (HttpClientErrorException ex) {
            return false;
        }
    }

    public Boolean eliminarEstudiante(Long id) {
        try {
            estudianteRepository.deleteById(id);
            return true;
        }catch (Exception err) {
            return false;
        }
    }

}