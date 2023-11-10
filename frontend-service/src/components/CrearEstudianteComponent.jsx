import React, { useState } from "react";
import EstudianteService from "../services/EstudianteService";
import { useNavigate } from "react-router-dom";

const CrearEstudianteComponent = () => {
  const navigate = useNavigate();

  const [estudiante, setEstudiante] = useState({
    rut: "",
    primerNombre: "",
    segundoNombre: "",
    primerApellido: "",
    segundoApellido: "",
    fechaNacimiento: null,
    tipoColegioProcedencia: "",
    nombreColegio: "",
    anioEgreso: 0,
  });

  const cambiarCampo = (campo, valor) => {
    setEstudiante({
      ...estudiante,
      [campo]: valor,
    });
  };

  const guardarEstudiante = (e) => {
    e.preventDefault();
    EstudianteService.guardarEstudiante(estudiante).then((res) => {
      navigate("/estudiantes");
    });
  };

  const cancelar = () => {
    navigate("/estudiantes");
  };

  return (
    <div>
      <br></br>
      <div className="container">
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            <h3 className="text-center">Agregar Estudiante</h3>
            <div className="card-body">
              <form onSubmit={guardarEstudiante}>
                <div className="form-group">
                  <label>Rut:</label>
                  <input
                    placeholder="Rut estudiante"
                    name="rut"
                    className="form-control"
                    value={estudiante.rut}
                    onChange={(e) => cambiarCampo("rut", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Primer Nombre:</label>
                  <input
                    placeholder="Primer Nombre estudiante"
                    name="primerNombre"
                    className="form-control"
                    value={estudiante.primerNombre}
                    onChange={(e) => cambiarCampo("primerNombre", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Segundo Nombre:</label>
                  <input
                    placeholder="Segundo Nombre estudiante"
                    name="segundoNombre"
                    className="form-control"
                    value={estudiante.segundoNombre}
                    onChange={(e) => cambiarCampo("segundoNombre", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Primer Apellido:</label>
                  <input
                    placeholder="Primer Apellido estudiante"
                    name="primerApellido"
                    className="form-control"
                    value={estudiante.primerApellido}
                    onChange={(e) => cambiarCampo("primerApellido", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Segundo Apellido:</label>
                  <input
                    placeholder="Segundo Apellido estudiante"
                    name="segundoApellido"
                    className="form-control"
                    value={estudiante.segundoApellido}
                    onChange={(e) => cambiarCampo("segundoApellido", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Fecha de Nacimiento:</label>
                  <input
                    type="date"
                    placeholder="Fecha de Nacimiento"
                    name="fechaNacimiento"
                    className="form-control"
                    value={estudiante.fechaNacimiento}
                    onChange={(e) => cambiarCampo("fechaNacimiento", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Tipo de Colegio de Procedencia:</label>
                  <select
                    name="tipoColegioProcedencia"
                    className="form-control"
                    value={estudiante.tipoColegioProcedencia}
                    onChange={(e) => cambiarCampo("tipoColegioProcedencia", e.target.value)}
                    required
                  >
                    <option value="">Seleccionar tipo de colegio</option>
                    <option value="municipal">Municipal</option>
                    <option value="subvencionado">Subvencionado</option>
                    <option value="privado">Privado</option>
                  </select>
                </div>
                <div className="form-group">
                  <label>Nombre del Colegio:</label>
                  <input
                    placeholder="Nombre del Colegio estudiante"
                    name="nombreColegio"
                    className="form-control"
                    value={estudiante.nombreColegio}
                    onChange={(e) => cambiarCampo("nombreColegio", e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Año de Egreso:</label>
                  <input
                    type="number"
                    placeholder="Anio de Egreso estudiante"
                    name="anioEgreso"
                    className="form-control"
                    value={estudiante.anioEgreso}
                    onChange={(e) => cambiarCampo("anioEgreso", e.target.value)}
                    required
                  />
                </div>

                <button className="btn btn-success" type="submit">Guardar</button>
                <button className="btn btn-danger" onClick={cancelar} style={{ marginLeft: "10px" }}>Cancelar</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrearEstudianteComponent;