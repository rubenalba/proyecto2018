package Modelo;

import java.util.Date;
import java.util.List;

import pojos.Franjas;
import pojos.Profesor;

/**
 *
 * @author rubenalba
 * @version 1.0
 */
public interface FranjaInterface {
 public void addFranja (Franjas franja);
 public void eliminarFranja (String idFranja);
 public void modificarFranja(Franjas franjaModificada);
 public List<Franjas> verAlFranjas();
 public Franjas verFranjaByID(String idFranja);
}
