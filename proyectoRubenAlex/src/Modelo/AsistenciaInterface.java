package Modelo;

import java.util.List;

import pojos.Alumnos;
import pojos.Asistencia;
import pojos.AsistenciaId;
import pojos.Franjas;
import pojos.Unidadformativa;

/**
 *
 * @author rubenalba
 * @version 1.0
 */
public interface AsistenciaInterface {
	public void addAsistencia (Asistencia asistencia);
	public void eliminarAsistencia (AsistenciaId id);
	public void modificarAsistencia (Asistencia asistenciaModificada);
	public Asistencia verAsistenciaById (AsistenciaId id);
	public List<Asistencia> verAllAsistencias ();

}
