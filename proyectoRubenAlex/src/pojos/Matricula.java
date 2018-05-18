package pojos;
// Generated 18-may-2018 15:35:07 by Hibernate Tools 4.0.1.Final

/**
 * Matricula generated by hbm2java
 */
public class Matricula implements java.io.Serializable {

	private MatriculaId id;
	private Alumnos alumnos;
	private Unidadformativa unidadformativa;
	private Double nota;

	public Matricula() {
	}

	public Matricula(MatriculaId id, Alumnos alumnos, Unidadformativa unidadformativa) {
		this.id = id;
		this.alumnos = alumnos;
		this.unidadformativa = unidadformativa;
	}

	public Matricula(MatriculaId id, Alumnos alumnos, Unidadformativa unidadformativa, Double nota) {
		this.id = id;
		this.alumnos = alumnos;
		this.unidadformativa = unidadformativa;
		this.nota = nota;
	}

	public MatriculaId getId() {
		return this.id;
	}

	public void setId(MatriculaId id) {
		this.id = id;
	}

	public Alumnos getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(Alumnos alumnos) {
		this.alumnos = alumnos;
	}

	public Unidadformativa getUnidadformativa() {
		return this.unidadformativa;
	}

	public void setUnidadformativa(Unidadformativa unidadformativa) {
		this.unidadformativa = unidadformativa;
	}

	public Double getNota() {
		return this.nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

}
