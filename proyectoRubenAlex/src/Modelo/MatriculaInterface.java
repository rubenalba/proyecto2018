package Modelo;


import java.util.List;

import pojos.Alumnos;
import pojos.Matricula;
import pojos.MatriculaId;
import pojos.Unidadformativa;

/**
 *
 * @author rubenalba
 * @version 1.0
 */
public interface MatriculaInterface {
	public void matricularAlumno(Matricula matricula);
	public void addNota (MatriculaId id, Double nota);
	public void eliminarMatricula (MatriculaId id);
	public Boolean validarNota(Double nota);
	public Matricula verMatricula(MatriculaId id);
	public List<Matricula> matriculasAlumno(Alumnos alumno);
	public Matricula verMatriculaUFDNI(Unidadformativa UF, Alumnos alumno);
	public void modificarNota(Matricula mat);


}
