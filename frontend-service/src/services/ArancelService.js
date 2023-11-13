import axios from "axios";

const ARANCEL_API_URL = "http://localhost:8080/arancel";

class ArancelService {

    obtenerArancel(idEstudiante) {
        return axios.get(ARANCEL_API_URL + "/estudiante/" + idEstudiante);
    }

    editarArancel(idArancel, arancel) {
        return axios.put(ARANCEL_API_URL + "/" + idArancel, arancel);
    }

    cantidadCuotasElegidas(idArancel, arancel) {
        return axios.put(ARANCEL_API_URL + "/cantidad-cuotas/" + idArancel, arancel);
    }

}

export default new ArancelService()