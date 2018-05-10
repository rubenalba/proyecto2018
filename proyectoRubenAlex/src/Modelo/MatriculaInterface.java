package Modelo;


import pojos.Alumnos;
import pojos.MatriculaId;
import pojos.Unidadformativa;

/**
 * 
 * @author rubenalba
 * @version 1.0
 */
public interface MatriculaInterface {
	public void addNota (MatriculaId id, Alumnos alumnos, Unidadformativa unidadformativa, Double nota);
	public void modificarNota (MatriculaId id, Double nota);
	public Boolean validarNota(Double nota);

}
