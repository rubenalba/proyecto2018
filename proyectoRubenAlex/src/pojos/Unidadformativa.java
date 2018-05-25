package pojos;
// Generated 22-may-2018 18:23:59 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Unidadformativa generated by hbm2java
 */
public class Unidadformativa implements java.io.Serializable {

	private int idUnidadFormativa;
	private Asignatura asignatura;
	private Profesor profesor;
	private String nombreUf;
	private int horas;
	private Set<Matricula> matriculas = new HashSet<Matricula>(0);
	private Set<Asistencia> asistencias = new HashSet<Asistencia>(0);

	public Unidadformativa() {
	}

	public Unidadformativa(int idUnidadFormativa, Asignatura asignatura, Profesor profesor, String nombreUf,
			int horas) {
		this.idUnidadFormativa = idUnidadFormativa;
		this.asignatura = asignatura;
		this.profesor = profesor;
		this.nombreUf = nombreUf;
		this.horas = horas;
	}

	public Unidadformativa(int idUnidadFormativa, Asignatura asignatura, Profesor profesor, String nombreUf, int horas,
			Set<Matricula> matriculas, Set<Asistencia> asistencias) {
		this.idUnidadFormativa = idUnidadFormativa;
		this.asignatura = asignatura;
		this.profesor = profesor;
		this.nombreUf = nombreUf;
		this.horas = horas;
		this.matriculas = matriculas;
		this.asistencias = asistencias;
	}

	public int getIdUnidadFormativa() {
		return this.idUnidadFormativa;
	}

	public void setIdUnidadFormativa(int idUnidadFormativa) {
		this.idUnidadFormativa = idUnidadFormativa;
	}

	public Asignatura getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public String getNombreUf() {
		return this.nombreUf;
	}

	public void setNombreUf(String nombreUf) {
		this.nombreUf = nombreUf;
	}

	public int getHoras() {
		return this.horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public Set<Matricula> getMatriculas() {
		return this.matriculas;
	}

	public void setMatriculas(Set<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public Set<Asistencia> getAsistencias() {
		return this.asistencias;
	}

	public void setAsistencias(Set<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}
	 @Override
	    public String toString() {
	        return this.nombreUf;
	    }
}
