package pojos;
// Generated 30/05/2018 19:29:46 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Alumnos generated by hbm2java
 */
public class Alumnos implements java.io.Serializable {

	private String dni;
	private Aula aula;
	private String nombre;
	private String apellidos;
	private String email;
	private Set<Matricula> matriculas = new HashSet<Matricula>(0);
	private Set<Asistencia> asistencias = new HashSet<Asistencia>(0);
	private String total;

	public Alumnos() {
	}

	public Alumnos(String dni, Aula aula, String nombre, String apellidos, String email) {
		this.dni = dni;
		this.aula = aula;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	public Alumnos(String dni, Aula aula, String nombre, String apellidos, String email, Set<Matricula> matriculas,
			Set<Asistencia> asistencias) {
		this.dni = dni;
		this.aula = aula;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.matriculas = matriculas;
		this.asistencias = asistencias;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Aula getAula() {
		return this.aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	/**
	 * Retorna el nombre completo del alumno
	 * @return
	 */
	public String getNombreCompleto(){
		String nom = apellidos + ", " + nombre;
		return nom;
	}
	/**
	 * Indica el valor total de faltas de asistencia del alumno en la uf concreta
	 * @param total, porcentaje de faltas
	 */
	public void setTotal(int total){
		this.total = String.valueOf(total)+"%";
	}
	/**
	 * retorna el total de faltas de una uf concreta
	 * @return
	 */
	public String getFaltasUF(){
		return total;
	}
	/**
	 * Retorna el dni, seguido del nombre y apellidos del alumno
	 */
	 @Override
	    public String toString() {
	        return this.dni + " - " + this.nombre + " " + this.apellidos;
	 }

}
