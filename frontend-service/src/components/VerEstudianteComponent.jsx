import React, { useState, useEffect } from 'react';
import EstudianteService from "../services/EstudianteService";
import { useNavigate, useParams } from 'react-router-dom';

const VerEstudianteComponent = () => {
    const [estudiante, setEstudiante] = useState();
    const navigate = useNavigate();
    const { id } = useParams();

    const editarEstudiante = () => {
        navigate("/editar-estudiante/" + id);
    };

    const eliminarEstudiante = () => {
        const confirmacion = window.confirm("¿Estás seguro de que deseas eliminar este estudiante? (se eliminarán todos los datos relacionados con el estudiante)");

        if (confirmacion) {
            EstudianteService.eliminarEstudiante(id).then(() => {
                navigate("/estudiantes");
            });
        }
    };

    const volverListaEstudiantes = () => {
        navigate("/estudiantes");
    }

    const verMatricula = () => {
        navigate("/ver-matricula/" + id);
    }

    const verArancel = () => {
        navigate("/ver-arancel/" + id);
    }

    useEffect(() => {
        EstudianteService.obtenerEstudiante(id).then((res) => {
            setEstudiante(res.data);
        });
    }, [id]);

    return (
        <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center">Datos del Estudiante</h3>
                    <div className = "card-body">
                        <div class="card-body">
                            <label>Rut:</label>
                            <div class="card">{estudiante && estudiante.rut}</div>
                        </div>
                        <div class="card-body">
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Primer Nombre:</label>
                                    <div class="card">{estudiante && estudiante.primerNombre}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Segundo Nombre:</label>
                                    <div class="card">{estudiante && estudiante.segundoNombre}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Primer Apellido:</label>
                                    <div class="card">{estudiante && estudiante.primerApellido}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Segundo Apellido:</label>
                                    <div class="card">{estudiante && estudiante.segundoApellido}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Fecha de Nacimiento:</label>
                                    <div class="card">{estudiante && estudiante.fechaNacimiento}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Tipo de Colegio:</label>
                                    <div class="card">{estudiante && estudiante.tipoColegioProcedencia}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div className="row">
                                <div className="col-md-6">
                                    <label>Nombre del Colegio:</label>
                                    <div class="card">{estudiante && estudiante.nombreColegio}</div>
                                </div>
                                <div className="col-md-6">
                                    <label>Año de Egreso:</label>
                                    <div class="card">{estudiante && estudiante.anioEgreso}</div>
                                </div>
                            </div> 
                        </div>
                        <button onClick={() => volverListaEstudiantes()} className="btn btn-info">Volver</button>
                        <button style={{ marginLeft: "10px" }} onClick={() => editarEstudiante()} className="btn btn-info">Editar</button>
                        <button style={{ marginLeft: "10px" }} onClick={() => eliminarEstudiante()} className="btn btn-danger">Eliminar</button>
                        <br></br>
                        <br></br>
                        <button onClick={() => verMatricula()} className="btn btn-info">Ver Matricula</button>
                        <button style={{ marginLeft: "10px" }} onClick={() => verArancel()} className="btn btn-info">Ver Arancel</button>
                    </div>
                </div>
            </div>
    )
}

export default VerEstudianteComponent;