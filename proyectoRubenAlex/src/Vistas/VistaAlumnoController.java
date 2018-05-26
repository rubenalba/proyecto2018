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
    private Unidadformativa unidadSelectedStr;
    Unidadformativa uf;
    Unidadformativa ufSelected;
    Asignatura asig;
    private List<Asistencia> listaFaltas;
    
    
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
    		listaUFS.add(uf);
		}
    	AsignaturasAlumno.setItems(FXCollections.observableArrayList(listaUFS));
    	AsignaturasAlumno.valueProperty().addListener(new ChangeListener<Unidadformativa>() {

    		public void changed(ObservableValue<? extends Unidadformativa> observable, Unidadformativa oldValue, Unidadformativa newValue) {
				if (AsignaturasAlumno != null) {
					unidadSelectedStr = AsignaturasAlumno.getSelectionModel().getSelectedItem();
					for (Matricula matricula : listaMatriculas) {
			    		//System.out.println(u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa()).getNombreUf());
			    		uf = u.verUnidadformativaByID(matricula.getId().getIdUnidadFormativa());
			    		if (uf.getNombreUf().equals(unidadSelectedStr.getNombreUf())){
								asig = uf.getAsignatura();
								asig = as.verAsignaturaById(asig.getIdAsignatura());
								int idAsignatura = asig.getIdAsignatura();
								int idCiclo = asig.getCiclo().getIdCiclo();
								ufSelected = u.verUFByName(idCiclo, idAsignatura, unidadSelectedStr.getNombreUf());
								if (matricula.getNota() != null)
								NotaAsigAlumno.setText(matricula.getNota().toString());
								else NotaAsigAlumno.setText("No s'ha puntuat encara");
						}
					}
				}
    		}
    	});
	}


    public void listaFaltasUF(){
    	listaFaltas = ast.verAllAsistenciasAlumnoUF(alumno, ufSelected);
    	tablaAsistencias.setItems(FXCollections.observableArrayList(listaFaltas));
    	fechaFalta.setCellValueFactory(new PropertyValueFactory<Asistencia, String>("Hora"));
		justificadoFalta.setCellValueFactory(new PropertyValueFactory<Asistencia, String>("Justificado"));
    }

    public void actualizarNota(){
    	Matricula mat = mi.verMatriculaUFDNI(ufSelected, alumno);

    	double nota = Double.valueOf(NotaAsigAlumno.getText());
    	mat.setNota(nota);
    	mi.modificarNota(mat);
    }
}
