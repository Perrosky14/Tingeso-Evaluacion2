import React, { useState, useEffect } from 'react';
import EstudianteService from "../services/EstudianteService";
import { useNavigate } from 'react-router-dom';

const EstudiantesComponent = () => {
  const [estudiantes, setEstudiantes] = useState([]);
  const navigate = useNavigate();

  const volverPaginaPrincipal = () => {
    navigate("/")
  }

  const agregarEstudiante = () => {
    navigate("/agregar-estudiante");
  };

  const verEstudiante = (idEstudiante) => {
    navigate('/ver-estudiante/' + idEstudiante);
  };

  useEffect(() => {
    EstudianteService.obtenerEstudiantes().then((res) => {
      setEstudiantes(res.data);
    });
  }, []);

  return (
    <div>
      <h2 className="text-center">Lista de Estudiantes</h2>
      <br></br>
      <div className="row">
        <button className='btn btn-primary' style={{ width: '186px' }} onClick={volverPaginaPrincipal}>Volver Pagina Principal</button>
        <button className="btn btn-primary" style={{ marginLeft: "10px", width: '161px' }} onClick={agregarEstudiante}>Agregar Estudiante</button>
      </div>
      <br></br>
      <div className="row">
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Rut</th>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>Fecha de nacimiento</th>
              <th>Año de Egreso</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {estudiantes.map(estudiante =>
              <tr key={estudiante.id}>
                <td>{estudiante.rut}</td>
                <td>{estudiante.primerNombre}</td>
                <td>{estudiante.primerApellido}</td>
                <td>{estudiante.fechaNacimiento}</td>
                <td>{estudiante.anioEgreso}</td>
                <td>
                  <button onClick={() => verEstudiante(estudiante.id)} className="btn btn-info">Ver</button>
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default EstudiantesComponent;