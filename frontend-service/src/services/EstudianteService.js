import axios from "axios";

const ESTUDIANTE_API_URL = "http://localhost:8080/estudiante";

class EstudianteService {
    
    obtenerEstudiantes() {
        return axios.get(ESTUDIANTE_API_URL);
    }

    obtenerEstudiante(idEstudiante) {
        return axios.get(ESTUDIANTE_API_URL + '/' + idEstudiante);
    }

    guardarEstudiante(estudiante) {
        return axios.post(ESTUDIANTE_API_URL, estudiante)
    }

    editarEstudiante(idEstudiante, estudiante) {
        return axios.put(ESTUDIANTE_API_URL + '/' + idEstudiante, estudiante)
    }

    eliminarEstudiante(idEstudiante) {
        return axios.delete(ESTUDIANTE_API_URL + '/' + idEstudiante)
    }

}

export default new EstudianteService()