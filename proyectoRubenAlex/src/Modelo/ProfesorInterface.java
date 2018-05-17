package Modelo;

import java.util.List;

import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.Aula;
import pojos.Profesor;

/**
 * @author rubenalba
 * @version 1.0
 */
public interface ProfesorInterface {
	public void addProfesor (Profesor profesor);
	public void eliminarProfesor (String dniProfesor);
	public void modificarProfesor(Profesor profesorModificado);
	public Profesor verProfesorByDni (String dniProfesor);
	public List <Profesor> verProfesores();
	public List<String> asignaturasImpartidas();
	public List<String> UFSimpartidas(String asignatura);
	public Profesor verProfesorByUser(String userProfesor);
}
