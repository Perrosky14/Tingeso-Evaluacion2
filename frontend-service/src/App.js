import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import EstudiantesComponent from './components/EstudiantesComponent';
import CrearEstudianteComponent from './components/CrearEstudianteComponent';
import VerEstudianteComponent from './components/VerEstudianteComponent';
import EditarEstudianteComponent from './components/EditarEstudianteComponent';

function App() {
  return (
    <div>
      <Router>
        <HeaderComponent />
        <div className="container">
          <Routes>
            <Route path="/estudiantes" element={<EstudiantesComponent/>}/>
            <Route path="/agregar-estudiante" element={<CrearEstudianteComponent/>}/>
            <Route path="/ver-estudiante/:id" element={<VerEstudianteComponent/>}/>
            <Route path="/editar-estudiante/:id" element={<EditarEstudianteComponent/>}/>
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;
