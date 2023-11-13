import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import PrincipalComponent from './components/PrincipalComponent';
import EstudiantesComponent from './components/EstudiantesComponent';
import CrearEstudianteComponent from './components/CrearEstudianteComponent';
import VerEstudianteComponent from './components/VerEstudianteComponent';
import EditarEstudianteComponent from './components/EditarEstudianteComponent';
import VerMatriculaComponent from './components/VerMatriculaComponent';
import VerArancelComponent from './components/VerArancelComponent';
import CuotasComponent from './components/CuotasComponent';
import VerCuotaComponent from './components/VerCuotaComponent';

function App() {
  return (
    <div>
      <Router>
        <HeaderComponent />
        <div className="container">
          <Routes>
            <Route path="/" element={<PrincipalComponent/>}/>
            <Route path="/estudiantes" element={<EstudiantesComponent/>}/>
            <Route path="/agregar-estudiante" element={<CrearEstudianteComponent/>}/>
            <Route path="/ver-estudiante/:id" element={<VerEstudianteComponent/>}/>
            <Route path="/editar-estudiante/:id" element={<EditarEstudianteComponent/>}/>
            <Route path="ver-matricula/:id" element={<VerMatriculaComponent/>}/>
            <Route path="/ver-arancel/:id" element={<VerArancelComponent/>}/>
            <Route path="/ver-cuotas/:id" element={<CuotasComponent/>}/>
            <Route path="/ver-cuota/:id" element={<VerCuotaComponent/>}/>
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;
