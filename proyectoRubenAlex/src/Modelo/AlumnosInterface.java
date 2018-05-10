package Modelo;

import java.util.List;

import pojos.Alumnos;
import pojos.Asistencia;
import pojos.Aula;

/**
 * Alumnos' Interface
 * @author rubenalba
 * @version 1.0
 */
public interface AlumnosInterface {
	public String addAlumno (Alumnos alumno);
	public void eliminarAlumno (String dni);
	public void modificarAlumno (Alumnos alumnoModificado);
	public List <Asistencia> verAsistencia(String dni);
	public Asistencia verAsistenciasByID (String dni);
	public List verAlumnobyName(String nombre);
	
}
