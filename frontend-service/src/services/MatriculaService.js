import axios from "axios";

const MATRICULA_API_URL = "http://localhost:8080/matricula";

class MatriculaService {

    obtenerMatricula(idEstudiante) {
        return axios.get(MATRICULA_API_URL + "/estudiante/" + idEstudiante);
    }

    editarMatricula(idMatricula, matricula) {
        return axios.put(MATRICULA_API_URL + "/" + idMatricula, matricula);
    }

}

export default new MatriculaService()