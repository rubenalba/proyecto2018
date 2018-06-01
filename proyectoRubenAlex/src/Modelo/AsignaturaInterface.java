package Modelo;

import java.util.List;

import pojos.Asignatura;

/**
 * Asignatura's Interface
 * @author rubenalba
 *@version 1.0
 */
public interface AsignaturaInterface {
	public void addAsignatura (Asignatura assignatura);
	public void eliminarAsignatura (int idAsignatura);
	public void modificarAsignatura (Asignatura asignaturaModificada);
	public Asignatura verAsignaturaById(int idAsignatura);
	public List <Asignatura> verAllAsignaturas();
	public List<Asignatura> verAsignaturaByCurso(String curso);
	public Asignatura verAsignaturaByName(String ciclo, int i);
}
