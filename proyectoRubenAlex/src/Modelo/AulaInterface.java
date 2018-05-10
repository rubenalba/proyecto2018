package Modelo;

import java.util.List;

import pojos.Aula;

/**
 * 
 * @author rubenalba
 * @version 1.0  
 */
public interface AulaInterface {
	public Integer addAula (int numAula);
	public void eliminarAula (int numAula);
	public void modificarAula (int numAula);
	public Aula verAulaByID (int numAula);
	public List <Aula> verAllAulas (int numAula);
}
