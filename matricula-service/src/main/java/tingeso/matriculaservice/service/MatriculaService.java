package tingeso.matriculaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tingeso.matriculaservice.entity.MatriculaEntity;
import tingeso.matriculaservice.model.EstudianteEntity;
import tingeso.matriculaservice.repository.MatriculaRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<MatriculaEntity> obtenerMatriculas() {
        return (ArrayList<MatriculaEntity>) matriculaRepository.findAll();
    }

    public MatriculaEntity guardarMatricula(MatriculaEntity matricula) {
        return matriculaRepository.save(matricula);
    }

    public Optional<MatriculaEntity> obtenerPorId(Long id) {
        return matriculaRepository.findById(id);
    }

    public Optional<MatriculaEntity> obtenerPorEstudianteAsociado(Long idEstudiante) {
        return matriculaRepository.buscarPorEstudianteAsociado(idEstudiante);
    }

    public Boolean validateMatricula(MatriculaEntity matricula) {
        if(matricula.getMonto() <= 0) {
            return false;
        }else if(matricula.getPagado() == null) {
            return false;
        }else if(matricula.getFechaPagado() == null) {
            if (matricula.getPagado()) {
                return false;
            }
        }
        return true;
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

    public Boolean eliminarMatricula(Long id) {
        try {
            matriculaRepository.deleteById(id);
            return true;
        }catch(Exception err) {
            return false;
        }
    }

}
