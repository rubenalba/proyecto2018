package Vistas;
 import java.util.ArrayList;
import java.util.List;

import Modelo.AsignaturaInterface;
import Modelo.MatriculaInterface;
import Modelo.UnidadFormativaInterface;
import dao.DAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Matricula;
import pojos.Unidadformativa;

public class VistaAlumnoController {
	static MatriculaInterface mi = DAO.getMatriculaInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
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
    private TextField FaltasAsigAlumno;

    private Alumnos alumno;
    private List<Matricula> listaMatriculas;
    private List<String> listaUFS = new ArrayList<String>();

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
    		Unidadformativa uf = u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa());
    		System.out.println(uf.getNombreUf());
    		listaUFS.add(uf.getNombreUf());
		}
    	AsignaturasAlumno.setItems(FXCollections.observableArrayList(listaUFS));
    }
}
