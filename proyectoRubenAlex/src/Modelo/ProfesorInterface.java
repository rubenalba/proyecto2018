package Modelo;

import java.util.List;

import pojos.Alumnos;
import pojos.Asistencia;
import pojos.Aula;
import pojos.Profesor;

/**
 * @author rubenalba
 * @version 1.0
 */
public interface ProfesorInterface {
	public void addProfesor (String dniProfesor, String nombre, String idUnidadFormativa, String usuari, String password);
	public void eliminarProfesor (String dniProfesor);
	public void modificarProfesor(Profesor profesorModificado);
	public Profesor verProfesorByDni (String dniProfesor);
	public List <Profesor> verProfesores();

}
