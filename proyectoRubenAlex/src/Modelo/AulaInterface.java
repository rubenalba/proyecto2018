package Modelo;

import java.util.List;

import pojos.Aula;

/**
 *
 * @author rubenalba
 * @version 1.0
 */
public interface AulaInterface {
	public void addAula(Aula Aula);
	public void eliminarAula (int numAula);
	public void modificarAula (Aula aula);
	public Aula verAulaByID (int numAula);
	public List<Aula> verAllAulas();
}
