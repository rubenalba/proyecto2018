package Modelo;

import java.util.Date;
import java.util.List;

import pojos.Asignatura;
import pojos.Franjas;
import pojos.Horas;
import pojos.Profesor;

/**
 *
 * @author rubenalba
 * @version 1.0
 */
public interface FranjaInterface {
 public void addFranja (Franjas franja) throws Exception;
 public void eliminarFranja (int integer);
 public void modificarFranja(Franjas franjaModificada);
 public List<Franjas> verAlFranjas();
 public Franjas verFranjaByID(int idFranja);
 public Franjas verFranjaFalta(Horas Hora, Profesor profesor, String dia, Asignatura asignatura);
 public List<Franjas> verFranjaAsignatura(Profesor profesor, Asignatura asignatura);
 public List<Franjas>completarHorario(String dni, String dia);
}
