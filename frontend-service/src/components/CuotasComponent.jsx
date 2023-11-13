import React, { useState, useEffect } from "react";
import CuotaService from "../services/CuotaService";
import { useNavigate, useParams } from "react-router-dom";

const CuotasComponent = () => {
    const [cuotas, setCuotas] = useState([]);
    const navigate = useNavigate();
    const { id } = useParams();

    const volverArancel = () => {
        navigate("/ver-arancel/" + id);
    };

    const verCuota = (idCuota) => {
        navigate("/ver-cuota/" + idCuota);
    };

    useEffect(() => {
        CuotaService.obtenerCuotas(id).then((res) => {
            setCuotas(res.data);
        });
    });

    return (
        <div>
            <h2 className="text-center">Lista de Cuotas</h2>
            <br/>
            <div className="row">
                <button className="btn btn-primary" style={{ width: "186px" }} onClick={volverArancel}>Volver al Arancel</button>
            </div>
            <br/>
            <div className="row">
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th> Número de Cuota</th>
                            <th>Monto</th>
                            <th>Fecha de vencimiento</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {cuotas.map(cuota =>
                            <tr key={cuota.id}>
                                <td>{cuota.numeroCuota}</td>
                                <td>{cuota.monto}</td>
                                <td>{cuota.fechaVencimiento}</td>
                                <td>
                                    <button onClick={() => verCuota(cuota.id)} className="btn btn-info">Ver</button>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    )

}

export default CuotasComponent;