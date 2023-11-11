import React, { useState, useEffect } from "react";
import MatriculaService from "../services/MatriculaService";
import { useNavigate, useParams } from "react-router-dom";

const VerMatriculaComponent = () => {
    const [matricula, setMatricula] = useState();
    const [mostrarBotonPagar, setMostrarBotonPagar] = useState(true);
    const navigate = useNavigate();
    const { id } = useParams();

    const volverEstudiante = () => {
        navigate("/ver-estudiante/" + id);
    }

    const pagarMatricula = () => {
        const fechaActual = new Date().toISOString();
    
        setMatricula({
          ...matricula,
          pagado: true,
          fechaPagado: fechaActual,
        });
    
        MatriculaService.editarMatricula(matricula.id, {
          ...matricula,
          pagado: true,
          fechaPagado: fechaActual,
        }).then(() => {
          setMostrarBotonPagar(false);
        });
    };

    useEffect(() => {
        MatriculaService.obtenerMatricula(id).then((res) => {
            setMatricula(res.data);
            setMostrarBotonPagar(!res.data.pagado);
        });
    }, [id]);

    return (
        <div>
            <br></br>
            <div className="card col-md-6 offset-md-3">
                <h3 className="text-center">Datos de la Matricula</h3>
                <div className="card-body">
                    <div class="card-body">
                        <div className="row">
                            <div className="col-md-6">
                                <label>ID de la Matricula:</label>
                                <div class="card">{matricula && matricula.id}</div>
                            </div>
                            <div className="col-md-6">
                                <label>Monto de la Matricula:</label>
                                <div class="card">{matricula && matricula.monto}</div>
                            </div>
                        </div>
                    </div>
                    {matricula && (
                        <>
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Estado de Pago:</label>
                                    <div className={`card ${matricula.pagado ? 'text-success' : 'text-danger'}`}>
                                        {matricula.pagado ? 'Pagado' : 'No Pagado'}
                                    </div>
                                </div>
                                {matricula.pagado && (
                                    <div className="col-md-6">
                                        <label>Fecha de Pago:</label>
                                        <div className="card">{matricula.fechaPagado}</div>
                                    </div>
                                )}
                            </div>
                        </>
                    )}
                    <br></br>
                    <button onClick={() => volverEstudiante()} className="btn btn-info">Volver</button>
                    {mostrarBotonPagar && (
                        <button style={{ marginLeft: "10px" }} onClick={() => pagarMatricula()} className="btn btn-success">Pagar Matrícula</button>
                    )}
                </div>
            </div>
        </div>
    )
}

export default VerMatriculaComponent;