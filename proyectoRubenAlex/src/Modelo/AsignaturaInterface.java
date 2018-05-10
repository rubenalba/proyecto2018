package Modelo;

import java.util.List;

import pojos.Asignatura;

/**
 * Asignatura's Interface
 * @author rubenalba
 *@version 1.0
 */
public interface AsignaturaInterface {
	public void addAsignatura (int idAsignatura, String nombreAsignatura);
	public void eliminarAsignatura (int idAsignatura);
	public void modificarAsignatura (Asignatura asignaturaModificada);
	public List <Asignatura> verAllAsignaturas();
	
}
