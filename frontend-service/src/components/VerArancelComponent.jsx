import React, { useState, useEffect } from "react";
import ArancelService from "../services/ArancelService";
import { useNavigate, useParams } from "react-router-dom";

const VerArancelComponent = () => {
    const [arancel, setArancel] = useState();
    const [mostrarBotonPagar, setMostrarBotonPagar] = useState(false);
    const [mostrarBotonPagarContado, setMostrarBotonPagarContado] = useState(true);
    const [mostrarArancelContado, setMostrarArancelContado] = useState(false);
    const [mostrarBotonPagarCuotas, setMostrarBotonPagarCuotas] = useState(true);
    const [mostrarBotonPactarCuotas, setMostarBotonPactarCuotas] = useState(false);
    const [mostrarBotonVerCuotas, setMostrarBotonVerCuotas] = useState(false);
    const [cuotasSeleccionadas, setCuotasSeleccionadas] = useState(1);
    const navigate = useNavigate();
    const { id } = useParams();

    const handleChangeCuotas = (event) => {
        setCuotasSeleccionadas(event.target.value);
    };

    const volverEstudiante = () => {
        navigate("/ver-estudiante/" + id);
    };

    const elegirTipoPago = (tipoPago) => {
        ArancelService.editarArancel(arancel.id, {
          ...arancel,
          inicializacion: false,
          contado: tipoPago,
        }).then(() => {
          setMostrarBotonPagarContado(false);
          setMostrarBotonPagarCuotas(false);
          setMostrarBotonPagar(tipoPago);
          setMostarBotonPactarCuotas(!tipoPago);
          window.location.reload();
        });
      };

    const pagarArancelContado = () => {
        const fechaActual = new Date().toISOString();

        setArancel({
            ...arancel,
            pagado: true,
            fechaPago: fechaActual
        });
        
        ArancelService.editarArancel(arancel.id, {
            ...arancel,
            pagado: true,
            fechaPago: fechaActual
        }).then(() => {
            setMostrarBotonPagar(false);
        });
    }

    const pactarCuotas = (cantCuotasElegidas) => {
        setArancel({
            ...arancel,
            cantCuotas: cantCuotasElegidas
        });

        ArancelService.cantidadCuotasElegidas(arancel.id, {
            ...arancel,
            cantCuotas: cantCuotasElegidas
        }).then(() => {
            setMostarBotonPactarCuotas(false);
            setMostrarBotonVerCuotas(true);
        });
    }

    const verCuotas = () => {
        navigate("/ver-cuotas/" + arancel.id);
    }

    useEffect(() => {
        ArancelService.obtenerArancel(id).then((res) => {
          setArancel(res.data);
    
          setMostrarBotonPagarContado(res.data.inicializacion);
          setMostrarBotonPagarCuotas(res.data.inicializacion);
          setMostrarBotonPagar(res.data.contado && !res.data.pagado && !res.data.inicializacion);
          setMostrarArancelContado(res.data.contado && res.data.pagado && !res.data.inicializacion);
          if(res.data.fechaPago === null) {
            setMostarBotonPactarCuotas(!res.data.contado && !res.data.inicializacion);
          }else {
            setMostrarBotonVerCuotas(!res.data.contado && !res.data.inicializacion);
          }
        });
    }, [id]);

    return (
        <div>
            <br></br>
            <div className="card col-md-6 offset-md-3">
                <h3 className="text-center">Datos del Arancel</h3>
                <div className="card-body">
                    <div class="card-body">
                        <div className="row">
                            <div className="col-md-6">
                                <label>ID del Arancel:</label>
                                <div class="card">{arancel && arancel.id}</div>
                            </div>
                            <div className="col-md-6">
                                <label>Monto total del Arancel:</label>
                                <div class="card">{arancel && arancel.monto}</div>
                            </div>
                        </div>
                    </div>
                    {arancel && mostrarBotonPagarContado && mostrarBotonPagarCuotas && (
                        <div>
                            <div class="card-body">
                                <div className="row">
                                    <div className="col-md-6">
                                        <label>Estado de Pago:</label>
                                        <div className={`card ${arancel.pagado ? 'text-success' : 'text-danger'}`}>{arancel.pagado ? 'Pagado' : 'No Pagado'}</div>
                                    </div>
                                    {arancel.pagado && (
                                        <div className="col-md-6">
                                            <label>Fecha de Pago:</label>
                                            <div className="card">{arancel.fechaPago}</div>
                                        </div>
                                    )}
                                </div>
                            </div>
                            <br/>
                            {mostrarBotonPagarContado && (
                                <button style={{ marginLeft: "10px" }} onClick={() => elegirTipoPago(true)} className="btn btn-success">Pagar al Contado</button>
                            )}
                            {mostrarBotonPagarCuotas && (
                                <button style={{ marginLeft: "10px" }} onClick={() => elegirTipoPago(false)} className="btn btn-success">Pagar en Cuotas</button>
                            )}
                        </div>
                    )}
                    {mostrarBotonPagar && (
                        <div>
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Descuentos del Estudiante:</label>
                                    <div class="card">{arancel && arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Monto final a pagar:</label>
                                    <div className="card">{arancel && arancel.monto - arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Estado de Pago:</label>
                                    <div className={`card ${arancel.pagado ? 'text-success' : 'text-danger'}`}>{arancel.pagado ? 'Pagado' : 'No Pagado'}</div>
                                </div>
                                {arancel.pagado && (
                                    <div className="col-md-6">
                                        <label>Fecha de Pago:</label>
                                        <div className="card">{arancel.fechaPago}</div>
                                    </div>
                                )}
                            </div>
                            <br/>
                            <button style={{ marginLeft: "10px" }} onClick={() => pagarArancelContado()} className="btn btn-success">Pagar Arancel</button>
                        </div>
                    )}
                    {mostrarArancelContado && (
                        <div>
                            <div className="row">
                            <div className="col-md-6">
                                    <label>Descuentos del Estudiante:</label>
                                    <div class="card">{arancel && arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Monto final pagado:</label>
                                    <div className="card">{arancel && arancel.monto - arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Estado de Pago:</label>
                                    <div className={`card ${arancel.pagado ? 'text-success' : 'text-danger'}`}>{arancel.pagado ? 'Pagado' : 'No Pagado'}</div>
                                </div>
                                {arancel.pagado && (
                                    <div className="col-md-6">
                                        <label>Fecha de Pago:</label>
                                        <div className="card">{arancel.fechaPago}</div>
                                    </div>
                                )}
                            </div> 
                        </div>
                    )}
                    {mostrarBotonPactarCuotas && (
                        <div>
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Descuentos del Estudiante:</label>
                                    <div class="card">{arancel && arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Monto final a pagar:</label>
                                    <div className="card">{arancel && arancel.monto - arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Cantidad máx de Cuotas:</label>
                                    <div className="card">{arancel && arancel.cantCuotas}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Cantidad de Cuotas:</label>
                                    <select className="form-control" value={cuotasSeleccionadas} onChange={handleChangeCuotas}>
                                        {[...Array(arancel.cantCuotas)].map((_, index) => (
                                            <option key={index + 1} value={index + 1}>
                                                {index + 1} cuota{index !== 0 && "s"}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <button style={{ marginLeft: "10px" }} onClick={() => pactarCuotas(cuotasSeleccionadas)} className="btn btn-primary">Pactar Cuotas</button>
                        </div>
                    )}
                    {mostrarBotonVerCuotas && (
                        <div>
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Descuentos del Estudiante:</label>
                                    <div class="card">{arancel && arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Monto final a pagar:</label>
                                    <div className="card">{arancel && arancel.monto - arancel.descuentos}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Cuotas Pactadas:</label>
                                    <div className="card">{arancel && arancel.cantCuotas}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Estado de Pago:</label>
                                    <div className={`card ${arancel.pagado ? 'text-success' : 'text-danger'}`}>{arancel.pagado ? 'Pagado' : 'No Pagado'}</div>
                                </div>
                                {arancel.pagado && (
                                    <div className="col-md-6">
                                        <label>Fecha de Pactación:</label>
                                        <div className="card">{arancel.fechaPago}</div>
                                    </div>
                                )}
                            </div>
                            <br/>
                            <button style={{ marginLeft: "10px" }} onClick={() => verCuotas()} className="btn btn-primary">Ver Cuotas</button>
                        </div>
                    )}
                    <div class="card-body">
                        <button onClick={() => volverEstudiante()} className="btn btn-info">Volver</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VerArancelComponent;