package Vistas;

import java.net.URL;
import java.util.ResourceBundle;

import Modelo.ProfesorInterface;
import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojos.Alumnos;
import pojos.Profesor;
import pojos.Unidadformativa;

public class VistaListaAlumnosController implements Initializable {
	static ProfesorInterface p = DAO.getProfesorInterface();
    @FXML
    private TableView<Alumnos> tablaAlumnos;

    @FXML
    private TableColumn<Alumnos, String> AlumnosCol1;

    @FXML
    private Button notasBTN;
    
    private ObservableList<Alumnos>alumnosLista;
    
    private static Profesor profesorActivo;
    private static Unidadformativa ufMarcada;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		profesorActivo();
		ufActiva();
		cargarTabla();
	}

	private void cargarTabla() {
		alumnosLista = FXCollections.observableArrayList(p.misAlumnosByAsignatura(profesorActivo, ufMarcada));
		tablaAlumnos.setItems(alumnosLista);
		System.out.println("profe: " + profesorActivo.getDniProfesor() + "UF: " + ufMarcada.getIdUnidadFormativa());
		AlumnosCol1.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("Nombre"));
		for (Alumnos alumnos : alumnosLista) {
			System.out.println(alumnos.getNombre());
		}
	}
	public Profesor profesorActivo() {
		VistaIniciController v = new VistaIniciController();
		profesorActivo = v.getProfesorActivo();
		return profesorActivo;
	}
	public Unidadformativa ufActiva() {
		VistaIniciController v = new VistaIniciController();
		ufMarcada = v.getUnidadFormativa();
		return ufMarcada;
	}

}
