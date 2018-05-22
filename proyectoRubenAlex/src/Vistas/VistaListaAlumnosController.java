package Vistas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Modelo.ProfesorInterface;
import dao.DAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
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
	private TableColumn<Alumnos, Boolean> Checkers;

	@FXML
	private CheckBox checkBox1;
	ObservableList<Alumnos> checkBoxList =FXCollections.observableArrayList();

	@FXML
	private Button notasBTN;

	private ObservableList<Alumnos>alumnosLista;

	private static Profesor profesorActivo;
	private static Unidadformativa ufMarcada;

	public List<Alumnos> listaNoAsistencia = new ArrayList<Alumnos>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		profesorActivo();
		ufActiva();
		cargarTabla();
		setCheckBox();
	}

	private void cargarTabla() {
		alumnosLista = FXCollections.observableArrayList(p.misAlumnosByAsignatura(profesorActivo, ufMarcada));
		tablaAlumnos.setItems(alumnosLista);
		System.out.println("profe: " + profesorActivo.getDniProfesor() + "UF: " + ufMarcada.getIdUnidadFormativa());
		AlumnosCol1.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("NombreCompleto"));
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

	@FXML
	private void closeWindow(ActionEvent event) {
		cerrarVentana(event);
	}
	private void cerrarVentana(ActionEvent event) {
		Node source = (Node)event.getSource();
		Stage stage= (Stage)source.getScene().getWindow();
		stage.close();
	}

	private void setCheckBox(){
		Checkers.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumnos, Boolean>, ObservableValue<Boolean>>() {
	        @Override
	        public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Alumnos, Boolean> features) {
	            return new SimpleBooleanProperty(features.getValue() != null);
	            }
	        });

		Checkers.setCellFactory(new ColumnCheckBox<Alumnos, Boolean>(){

			@Override
			public boolean checkValue(Alumnos element) {
				return true;
			}

			@Override
			public boolean checkEditable(Alumnos element) {
				return true;
			}

			@Override
			public void checkAction(int index, Alumnos element, boolean value) {
				if (value){
					//coger el alumno de esta posicion y guardarlo en una lista Alumnos falta
					//despues recorrer esa lista y por cada alumno generar una falta de asistencfia
					//para la unMarcada
					if (!listaNoAsistencia.contains(alumnosLista.get(index)))
					listaNoAsistencia.add(alumnosLista.get(index));

				}
				else {
					if (listaNoAsistencia.contains(alumnosLista.get(index)))
					listaNoAsistencia.remove(alumnosLista.get(index));
				}
			}
		});
	}
}
