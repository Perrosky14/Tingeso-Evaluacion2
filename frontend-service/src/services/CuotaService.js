import axios from "axios";

const CUOTA_API_URL = "http://localhost:8080/cuota";

class CuotaService {

    obtenerCuotas(idArancel) {
        return axios.get(CUOTA_API_URL + "/arancel/" + idArancel);
    }

    obtenerCuota(idCuota) {
        return axios.get(CUOTA_API_URL + "/" + idCuota);
    }

    editarCuota(idCuota, cuota) {
        return axios.put(CUOTA_API_URL + "/" + idCuota, cuota);
    }

}

export default new CuotaService()