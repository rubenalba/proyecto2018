package Modelo;

import java.util.List;

import pojos.Alumnos;
import pojos.Asistencia;
import pojos.Aula;
import pojos.Matricula;
import pojos.Unidadformativa;

/**
 * Alumnos' Interface
 * @author rubenalba
 * @version 1.0
 */
public interface AlumnosInterface {
	public void addAlumno (Alumnos alumno);
	public void eliminarAlumno (String dni);
	public void modificarAlumno (Alumnos alumnoModificado);

	/*------------------------------------------
	 * MIRAR PARA HACER UN "VERASISTENCIA" POR ALUMNO EN CADA UF
	 */

	public List <Asistencia> verAsistencia(String dni);
	public Asistencia verAsistenciasByID (String dni);
	//revisar la lista de asistencias y el asistencia x id, creo que deberia ser por UF
	public List<Matricula> verMatriculas();
	public Matricula verMatriculaByUF(Unidadformativa uf);

	public Alumnos verAlumnobyName(String nombre);
	public Alumnos verAlumnobyDNI(String DNI);
}
