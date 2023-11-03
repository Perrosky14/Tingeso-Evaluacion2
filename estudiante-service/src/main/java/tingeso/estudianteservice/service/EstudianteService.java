package tingeso.estudianteservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.estudianteservice.entity.EstudianteEntity;
import tingeso.estudianteservice.repository.EstudianteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

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

    public boolean eliminarEstudiante(Long id) {
        try {
            estudianteRepository.deleteById(id);
            return true;
        }catch (Exception err) {
            return false;
        }
    }

}