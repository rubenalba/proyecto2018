package pojos;
// Generated 30/05/2018 19:29:46 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Asignatura generated by hbm2java
 */
public class Asignatura implements java.io.Serializable {

	private int idAsignatura;
	private Ciclo ciclo;
	private String nombreAsignatura;
	private Set<Franjas> franjases = new HashSet<Franjas>(0);
	private Set<Unidadformativa> unidadformativas = new HashSet<Unidadformativa>(0);

	public Asignatura() {
	}

	public Asignatura(int idAsignatura, String nombreAsignatura) {
		this.idAsignatura = idAsignatura;
		this.nombreAsignatura = nombreAsignatura;
	}

	public Asignatura(int idAsignatura, Ciclo ciclo, String nombreAsignatura, Set<Franjas> franjases,
			Set<Unidadformativa> unidadformativas) {
		this.idAsignatura = idAsignatura;
		this.ciclo = ciclo;
		this.nombreAsignatura = nombreAsignatura;
		this.franjases = franjases;
		this.unidadformativas = unidadformativas;
	}

	public int getIdAsignatura() {
		return this.idAsignatura;
	}

	public void setIdAsignatura(int idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public Ciclo getCiclo() {
		return this.ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public String getNombreAsignatura() {
		return this.nombreAsignatura;
	}

	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}

	public Set<Franjas> getFranjases() {
		return this.franjases;
	}

	public void setFranjases(Set<Franjas> franjases) {
		this.franjases = franjases;
	}

	public Set<Unidadformativa> getUnidadformativas() {
		return this.unidadformativas;
	}

	public void setUnidadformativas(Set<Unidadformativa> unidadformativas) {
		this.unidadformativas = unidadformativas;
	}
	/**
	 * Muestra el nombre de la asignatura
	 */
	 @Override
	    public String toString() {
	        return this.nombreAsignatura;
}
}
