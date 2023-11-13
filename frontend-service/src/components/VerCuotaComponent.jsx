import React, { useState, useEffect } from "react";
import CuotaService from "../services/CuotaService";
import { useNavigate, useParams } from "react-router-dom";

const VerCuotaComponent = () => {
    const [cuota, setCuota] = useState();
    const [mostrarBotonPagar, setMostrarBotonPagar] = useState(true);
    const navigate = useNavigate();
    const { id } = useParams();

    const volverCuotas = (idArancel) => {
        navigate("/ver-cuotas/" + idArancel);
    }

    const pagarCuota = () => {
        const fechaActual = new Date().toISOString();

        setCuota({
            ...cuota,
            pagado: true,
            fechaPago: fechaActual
        });

        CuotaService.editarCuota(cuota.id, {
            ...cuota,
            pagado: true,
            fechaPago: fechaActual,
        }).then(() => {
            setMostrarBotonPagar(false);
        });
    }

    useEffect(() => {
        CuotaService.obtenerCuota(id).then((res) => {
            setCuota(res.data);
            setMostrarBotonPagar(!res.data.pagado);
        });
    }, [id]);

    return (
        <div>
            <br/>
            <div className="card col-md-6 offset-md-3">
                <h3 className="text-center">Datos de la Cuota</h3>
                <div className="card-body">
                    <div class="card-body">
                        <div className="row">
                            <div className="col-md-6">
                                <label>Número de Cuota:</label>
                                <div class="card">{cuota && cuota.numeroCuota}</div>
                            </div>
                            <div className="col-md-6">
                                <label>Monto:</label>
                                <div class="card">{cuota && cuota.monto}</div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <label>Descuento por Pruebas:</label>
                                <div class="card">{cuota && cuota.descuento}</div>
                            </div>
                            <div className="col-md-6">
                                <label>Meses de Atraso:</label>
                                <div class="card">{cuota && cuota.mesesAtraso}</div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <label>Interés por Atraso:</label>
                                <div class="card">{cuota && cuota.interesPorAtraso}</div>
                            </div>
                            <div className="col-md-6">
                                <label>Monto Final por Pagar:</label>
                                <div class="card">{cuota && cuota.monto + cuota.interesPorAtraso - cuota.descuento}</div>
                            </div>
                        </div>
                        {cuota && (
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Estado de Pago:</label>
                                    <div className={`card ${cuota.pagado ? 'text-success' : 'text-danger'}`}>
                                        {cuota.pagado ? 'Pagado' : 'No Pagado'}
                                    </div>
                                </div>
                                {cuota.pagado && (
                                    <div className="col-md-6">
                                        <label>Fecha de Pago:</label>
                                        <div className="card">{cuota.fechaPago}</div>
                                    </div>
                                )}
                            </div>
                        )}
                        <br/>
                        <button onClick={() => volverCuotas(cuota.idArancel)} className="btn btn-info">Volver</button>
                        {mostrarBotonPagar && (
                            <button style={{ marginLeft: "10px" }} onClick={() => pagarCuota()} className="btn btn-success">Pagar Cuota</button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default VerCuotaComponent;