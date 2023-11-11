import React from "react";
import { useNavigate } from "react-router-dom";

const PrincipalComponent = () => {
    const navigate = useNavigate();

    const verEstudiantes = () => {
        navigate("/estudiantes");
    }

    const subirPruebas = () => {
        navigate("/pruebas");
    }

    const verReporte = () => {
        navigate("/reporte");
    }

    return (
        <div>
            <br></br>
            <div className="container">
                <div className="row">
                    <div className="card col-md-6 offset-md-3 offset-md-3">
                        <h1 className="text-center">Registro de Pagos de Estudiantes</h1>
                        <div className="card-body text-center">
                            <button onClick={() => verEstudiantes()} style={{ width: '300px' }} className="btn btn-info btn-lg btn-block">Ver Estudiantes</button>
                            <br/>
                            <br/>
                            <button onClick={() => subirPruebas()} style={{ width: '300px' }} className="btn btn-info btn-lg btn-block">Subir Pruebas</button>
                            <br/>
                            <br/>
                            <button onClick={() => verReporte()} style={{ width: '300px' }} className="btn btn-info btn-lg btn-block">Ver Reporte</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PrincipalComponent;