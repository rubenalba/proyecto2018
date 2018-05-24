package Vistas;
 import java.util.ArrayList;
import java.util.List;

import Modelo.AsignaturaInterface;
import Modelo.MatriculaInterface;
import Modelo.UnidadFormativaInterface;
import dao.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Matricula;
import pojos.Unidadformativa;

public class VistaAlumnoController {
	static MatriculaInterface mi = DAO.getMatriculaInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
    @FXML
    private TextField DNIAlumno;

    @FXML
    private TextField ApellidosAlumno;

    @FXML
    private TextField Email;

    @FXML
    private ChoiceBox<String> AsignaturasAlumno;

    @FXML
    private TextField NotaAsigAlumno;

    @FXML
    private TextField NombreAlumno;

    @FXML
    private Button mostrarFaltas;

    private Alumnos alumno;
    private List<Matricula> listaMatriculas;
    private List<String> listaUFS = new ArrayList<String>();
    private String unidadSelectedStr;
    Unidadformativa uf;
    Asignatura asig;
    @FXML
    public void initialize(){
    	VistaIniciController vistainici = new VistaIniciController();
    	alumno = vistainici.getAlumnoMarcado();
    	DNIAlumno.setText(alumno.getDni());
    	ApellidosAlumno.setText(alumno.getApellidos());
    	Email.setText(alumno.getEmail());
    	NombreAlumno.setText(alumno.getNombre());
    	listaMatriculas = mi.matriculasAlumno(alumno);
    	System.out.println(listaMatriculas.size());
    	for (Matricula matricula : listaMatriculas) {
    		//System.out.println(u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa()).getNombreUf());
    		uf = u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa());
    		System.out.println(uf.getNombreUf());
    		listaUFS.add(uf.getNombreUf());
		}
    	AsignaturasAlumno.setItems(FXCollections.observableArrayList(listaUFS));
    	AsignaturasAlumno.valueProperty().addListener(new ChangeListener<String>() {

    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (AsignaturasAlumno != null) {
					unidadSelectedStr = AsignaturasAlumno.getValue();
					for (Matricula matricula : listaMatriculas) {
						uf = u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa());
						if (uf.getNombreUf().equals(unidadSelectedStr)){
							asig = uf.getAsignatura();
							System.out.println(asig.getNombreAsignatura());						}
					}
				}
    		}
    	});
	}


    public void listaFaltasUF(){

    }
}
