package Vistas;
 import java.util.ArrayList;
import java.util.List;

import Modelo.AsignaturaInterface;
import Modelo.AsistenciaInterface;
import Modelo.MatriculaInterface;
import Modelo.UnidadFormativaInterface;
import dao.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.AsistenciaId;
import pojos.Matricula;
import pojos.Unidadformativa;

/**
 * Controlador de la vista que contiene la información de un alumno concreto
 * @author cfgs
 *
 */
public class VistaAlumnoController {
	static MatriculaInterface mi = DAO.getMatriculaInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static AsistenciaInterface ast = DAO.getAsistenciaInterface();
    @FXML
    private TextField DNIAlumno;

    @FXML
    private TextField ApellidosAlumno;

    @FXML
    private TextField Email;

    @FXML
    private ChoiceBox<Unidadformativa> AsignaturasAlumno;

    @FXML
    private TableView<Asistencia> tablaAsistencias;
    @FXML
    private TableColumn<Asistencia, String> fechaFalta;

    @FXML
    private TableColumn<Asistencia, String> justificadoFalta;

    @FXML
    private TextField NotaAsigAlumno;

    @FXML
    private TextField NombreAlumno;

    @FXML
    private Button mostrarFaltas;
    @FXML
    private Button calificar;
    @FXML
    private Button GuardarNota;

    private Alumnos alumno;
    private List<Matricula> listaMatriculas;
    private List<Unidadformativa> listaUFS = new ArrayList<Unidadformativa>();
    Unidadformativa uf;
    Unidadformativa ufSelected;
    Asignatura asig;
    private List<Asistencia> listaFaltas;
    private static Unidadformativa UFActiva;

    @FXML
    public void initialize(){
    	UFActiva = getUFMarcada();
    	VistaIniciController vistainici = new VistaIniciController();
    	alumno = vistainici.getAlumnoMarcado();

    	DNIAlumno.setText(alumno.getDni());
    	ApellidosAlumno.setText(alumno.getApellidos());
    	Email.setText(alumno.getEmail());
    	NombreAlumno.setText(alumno.getNombre());

    	listaMatriculas = mi.matriculasAlumno(alumno);

    	for (Matricula matricula : listaMatriculas) {
    		uf = u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa());
    		listaUFS.add(uf);
		}
    	AsignaturasAlumno.setItems(FXCollections.observableArrayList(listaUFS));

    	AsignaturasAlumno.valueProperty().addListener(new ChangeListener<Unidadformativa>() {

    		public void changed(ObservableValue<? extends Unidadformativa> observable, Unidadformativa oldValue, Unidadformativa newValue) {
				if (AsignaturasAlumno != null) {
					ufSelected = AsignaturasAlumno.getValue();
					Matricula mat = mi.verMatriculaUFDNI(ufSelected, alumno);

					if (mat.getNota() != null)
						NotaAsigAlumno.setText(mat.getNota().toString());
						else NotaAsigAlumno.setText("No s'ha puntuat encara");
				}
    		}
    	});
	}


    private Unidadformativa getUFMarcada() {
		VistaIniciController v = new VistaIniciController();
		UFActiva =  v.getUnidadFormativa();
		return UFActiva;
	}
    /**
     * Metodo para listar las faltas de asistencia de un alumno en una UF concreta
     */
    public void listaFaltasUF(){
    	listaFaltas = ast.verAllAsistenciasAlumnoUF(alumno, ufSelected);
    	tablaAsistencias.setItems(FXCollections.observableArrayList(listaFaltas));
    	fechaFalta.setCellValueFactory(new PropertyValueFactory<Asistencia, String>("Hora"));
		justificadoFalta.setCellValueFactory(new PropertyValueFactory<Asistencia, String>("Justificado"));
    }

    /**
     * Metodo para modificar la nota de una matricula
     */
    public void actualizarNota(){
    	Matricula mat = mi.verMatriculaUFDNI(ufSelected, alumno);
    	double nota = Double.valueOf(NotaAsigAlumno.getText());
    	mat.setNota(nota);
    	mi.modificarNota(mat);
    }
}
