package Modelo;

import java.util.List;
/**
 * @author rubenalba
 * @version 1.0
 */
import pojos.Asignatura;
import pojos.Profesor;
import pojos.Unidadformativa;

public interface UnidadFormativaInterface {
	public void addUnidadFormativa (String idUnidadFormativa, Asignatura asignatura, Profesor profesor, int horas);
	public void eliminarUnidadFormativa (String idUnidadFormativa);
	public void modificarUnidadFormativa(Unidadformativa unidadFormativaModificada);
	public Unidadformativa verUnidadformativaByID (String idUnidadFormativa);
	public List <Unidadformativa> verAllUnidadFormativas();
}
