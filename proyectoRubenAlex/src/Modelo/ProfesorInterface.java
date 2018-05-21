package Modelo;

import java.util.List;

import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.Aula;
import pojos.Profesor;
import pojos.Unidadformativa;

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
	public Profesor verProfesorByUser(String userProfesor);
	public List<String> asignaturasImpartidas(String usuarioActivo);
	public List<String> UFSimpartidas(String asignatura, String dni);
	public List<Asignatura> misAsignaturas(Profesor profesor);
	public List<Unidadformativa>misUFs (Profesor profesor, Asignatura idAsignatura);
	public List<Alumnos> misAlumnosByAsignatura(Profesor profesor, Unidadformativa uf);
}
