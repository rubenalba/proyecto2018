package Vistas;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Modelo.AsignaturaInterface;
import Modelo.AsistenciaInterface;
import Modelo.FranjaInterface;
import Modelo.HorasInterface;
import Modelo.ProfesorInterface;
import Modelo.UnidadFormativaInterface;
import application.Main;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.AsistenciaId;
import pojos.Franjas;
import pojos.Horas;
import pojos.Profesor;
import pojos.Unidadformativa;
import dao.DAO;

public class VistaIniciController {
	public static final int PORCENTAJE = 25;
	public static final int COLUMNAS = 10;
	static ProfesorInterface pr = DAO.getProfesorInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static HorasInterface h = DAO.getHorasInterface();
	static FranjaInterface fr = DAO.getFranjaInterface();
	static AsistenciaInterface ast = DAO.getAsistenciaInterface();
	@FXML
	private Button BtnInfo;

	@FXML
	private TableColumn<String, String> ColHorario;

	@FXML
	private TableColumn<Franjas, String> ColFranja;

	@FXML
	private ListView<String> ListaCursos, ListaUfs;

	@FXML
	private Button BtnCerrarSession, BtnVolverConfig;

	@FXML
	private Button BtnAjustes;

	@FXML
	private Button addFranja;

	@FXML
    private Button addCursoBTN;

	@FXML
	private Button ConfirmFranjaCB;

	@FXML
	private ChoiceBox<?> idFranjaCB;

	@FXML
	private ChoiceBox<?> UFranjaCB;

	@FXML
	private TableView<Asignatura> tablaCursos;

	@FXML
	private TableColumn<Asignatura, String> ColCursos;

	@FXML
	private TableView<Unidadformativa> TablaUFs;

	@FXML
	private TableColumn<Unidadformativa, String> ColUF;


	@FXML
	private TableView<Alumnos> tablaPruebas;

	@FXML
	private TableColumn<Alumnos, String> ColNom;

	//TABLA ALUMNOS ASISTENCIA
	@FXML
	private TableView<Alumnos> tablaAlumnos;
	@FXML
	private TableColumn<Alumnos, Boolean> Checkers;
	@FXML
	private TableColumn<Alumnos, String> ColAlumnos;
	public List<Alumnos> listaNoAsistencia = new ArrayList<Alumnos>();
	ObservableList<Alumnos>alumnosLista;
	@FXML
	private TextField TextHoraAsistencia;
    @FXML
    private DatePicker DiaAsistenciaSelect;
    @FXML
    private Button BtnGenerarAsistencia;
    Calendar calendario = new GregorianCalendar();
    int hora, minutos, segundos;
	@FXML
	private TableColumn<Alumnos, String> ColAsistencia;

    //-----------------------------------------------
	@FXML
	private AnchorPane VentanaPrincipal;
	@FXML
	private AnchorPane VentanaAlumnos;

	@FXML
	private Button volverBTN;
	LocalDate today = LocalDate.now();




	boolean franjaVisible = true;

	private static Profesor profesorActivo;
	private static Asignatura asignaturaMarcada;

	private static Unidadformativa UFMarcada;

	@FXML
	public void initialize() {
		profesorActivo=getProfesorActivo();
		//VentanaAlumnos.setVisible(false);
		//VentanaPrincipal.setVisible(true);
		cargarCursos();
	}
	private ObservableList<String> cursosList;


	public Profesor getProfesorActivo(){
		return profesorActivo;
	}

	public void setProfesorActivo(String usuarioActivo){
		profesorActivo = pr.verProfesorByUser(usuarioActivo);
	}

	public Unidadformativa getUnidadFormativa() {
		return UFMarcada;
	}

	@FXML
	public void añadirFranja(){
		setVisibleFranja(true);
	}

	@FXML
	public void confirmarAñadirFranja(){
		setVisibleFranja(false);
	}

	public void setVisibleFranja(boolean state){
		ConfirmFranjaCB.setVisible(state);
		idFranjaCB.setVisible(state);
		UFranjaCB.setVisible(state);
	}

	@FXML
	public void cerrarSesion(){
		Stage Actual = (Stage) BtnCerrarSession.getScene().getWindow();
		Actual.close();

		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("../Vistas/VistaLogin.fxml"));
			AnchorPane ventanaDos = (AnchorPane) loader.load();
			Stage ventana = new Stage();
			Scene sceneDos = new Scene(ventanaDos);
			sceneDos.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			ventana.setScene(sceneDos);
			ventana.show();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al cerrar sesion");
			alert.setContentText("No se ha podido desconectar correctamente, el programa se cerrara.");
			alert.showAndWait();
			Stage Actual2 = (Stage) BtnCerrarSession.getScene().getWindow();
			Actual2.close();
			e.printStackTrace();
		}
	}
	@FXML
	public void back() {
		VentanaAlumnos.setVisible(false);
		VentanaPrincipal.setVisible(true);
	}

	/*public void cargarCursos(){
		List <String> cursos = pr.asignaturasImpartidas(profesorActivo.getDniProfesor());
		ObservableList<String> cursosimpartidos = FXCollections.observableArrayList(cursos);
		ListaCursos.setItems(cursosimpartidos);
		ListaCursos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(
				ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Has seleccionado" + newValue);
				verUFAsignaturaSelected(newValue);
			}
		});
	}*/
	//NO TOCAR!!!!!!!!!
	public void cargarCursos() {

		List<Asignatura>misAsignaturas = pr.misAsignaturas(profesorActivo);
		ObservableList <Asignatura> cursos = FXCollections.observableArrayList(misAsignaturas);
		tablaCursos.setItems(cursos);
		ColCursos.setCellValueFactory(new PropertyValueFactory<Asignatura, String>("nombreAsignatura"));
		asignaturaMarcada = tablaCursos.getSelectionModel().getSelectedItem();
		tablaCursos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Asignatura>() {

			@Override
			public void changed(ObservableValue<? extends Asignatura> observable, Asignatura oldValue, Asignatura newValue) {

				Asignatura asig =   new Asignatura();
				asig = tablaCursos.getSelectionModel().getSelectedItem();
				List <Unidadformativa> unidadformativa = pr.misUFs(profesorActivo, asig);
				ObservableList<Unidadformativa> uf = FXCollections.observableArrayList(unidadformativa);
				TablaUFs.setItems(uf);
				ColUF.setCellValueFactory(new PropertyValueFactory<Unidadformativa, String>("nombreUf"));
			}
		});
				TablaUFs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Unidadformativa>() {

					@Override
					public void changed(ObservableValue<? extends Unidadformativa> observable, Unidadformativa oldValue, Unidadformativa newValue) {
						if (newValue != null){
						UFMarcada = TablaUFs.getSelectionModel().getSelectedItem();
						VentanaPrincipal.setVisible(false);
						VentanaAlumnos.setVisible(true);

						setCheckBox();
						}









						/*alumnosLista = FXCollections.observableArrayList(p.misAlumnosByAsignatura(profesorActivo, ufMarcada));
						tablaAlumnos.setItems(alumnosLista);
						System.out.println("profe: " + profesorActivo.getDniProfesor() + "UF: " + ufMarcada.getIdUnidadFormativa());
						AlumnosCol1.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("NombreCompleto"));*/
						//if (!openned){
						/*Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaListaAlumnos.fxml"));
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.show();*/
						//   openned = true;}

					}
				});
			//}
		//});


	}

	public void cargarFranjaHoraria() {

	}
	@FXML
	public void configuracion(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaConfiguracion.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void volver(){
		try {
			Stage Actual = (Stage) BtnVolverConfig.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("../Vistas/VistaInicial.fxml"));
			AnchorPane ventanaDos = (AnchorPane) loader.load();
			Scene sceneDos = new Scene(ventanaDos);
			Actual.setScene(sceneDos);
			Actual.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al abrir la configuracion");
			alert.setContentText("El programa se cerrara.");
			alert.showAndWait();
			Stage Actual2 = (Stage) BtnVolverConfig.getScene().getWindow();
			Actual2.close();
			e.printStackTrace();
		}
	}

	/*@FXML
	public void verUFAsignaturaSelected(String asignatura){
		List <String> ufs = pr.UFSimpartidas(asignatura, profesorActivo.getDniProfesor());
		//List <Unidadformativa> ufs = pr.misUFs(profesorActivo, idAsignatura)
		ObservableList<String>ufsimpartidas = FXCollections.observableArrayList(ufs);
		ListaUfs.setItems(ufsimpartidas);

		ListaUfs.getSelectionModel().getSelectedItem();
		ListaUfs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(
				ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Stage Actual = (Stage) ListaUfs.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("../Vistas/VistaListaAlumnos.fxml"));
				AnchorPane ventanaDos;
				try {
					ventanaDos = (AnchorPane) loader.load();
					Scene sceneDos = new Scene(ventanaDos);
					Actual.setScene(sceneDos);
					Actual.show();
				} catch (IOException e) {
					// TODO Auto-generat ed catch block
					e.printStackTrace();
				}

			}
		});
	}*/

	private void setCheckBox(){
		//Seleccionar hora actual para generar las faltas
		hora =calendario.get(Calendar.HOUR_OF_DAY);
		minutos = calendario.get(Calendar.MINUTE);
		String minuts ="";
		if (minutos<10)
		TextHoraAsistencia.setText(hora+":0"+minuts);
		else TextHoraAsistencia.setText(hora+":"+minutos);
		//-------------------------------------------------
		//Seleccionar el dia actual para generar las faltas
		
		DiaAsistenciaSelect.setValue(today);
		//-------------------------------------------------


		alumnosLista = FXCollections.observableArrayList(pr.misAlumnosByAsignatura(profesorActivo, UFMarcada));
		tablaAlumnos.setItems(alumnosLista);
		ColAlumnos.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("NombreCompleto"));
		List<Integer> faltas = FXCollections.observableArrayList();
		for (Alumnos alumnos : alumnosLista) {
			List<Asistencia> faltasAlumno = ast.verAllAsistenciasAlumnoUF(alumnos, UFMarcada);
			alumnos.setTotal((100*faltasAlumno.size())/UFMarcada.getHoras());
			ColAsistencia.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("FaltasUF"));
		}


		Checkers.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumnos, Boolean>, ObservableValue<Boolean>>() {
	        @Override
	        public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Alumnos, Boolean> features) {
	            return new SimpleBooleanProperty(features.getValue() != null);
	            }
	        });

		Checkers.setCellFactory(new ColumnCheckBox<Alumnos, Boolean>(){

			@Override
			public boolean checkValue(Alumnos element) {
				return false;
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
	@FXML
	public void generarFaltas(){
		Horas horaFalta = h.getHorasByRango(TextHoraAsistencia.getText());
		Asignatura asignaturaFalta = as.verAsignaturaById(UFMarcada.getAsignatura().getIdAsignatura());
		String fecha = DiaAsistenciaSelect.getValue().toString();
		String dia = DiaAsistenciaSelect.getValue().getDayOfWeek().name();
		Franjas franjaFalta = fr.verFranjaFalta(horaFalta, profesorActivo, dia, asignaturaFalta);
		for (Alumnos alumnos : listaNoAsistencia) {
			Asistencia falta = new Asistencia();
			falta.setAlumnos(alumnos);
			falta.setUnidadformativa(UFMarcada);
			falta.setFranjas(franjaFalta);
			AsistenciaId a = new AsistenciaId(alumnos.getDni(), UFMarcada.getIdUnidadFormativa(), franjaFalta.getIdFranja(), fecha);
			falta.setId(a);
			falta.setFecha(DiaAsistenciaSelect.getValue().toString());
			ast.addAsistencia(falta);
		}

	}
	@FXML
	public void addCurso() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../Vistas/AddCursoVista.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	public void cargarUltimasFaltas(Alumnos alumno, Asignatura asignatura){
		int faltasCargar = 10;
		List<Franjas> franjasAsig = fr.verFranjaAsignatura(profesorActivo, asignatura);
		List<String> dias = new ArrayList<String>();
		for (Franjas franjas : franjasAsig) {
			dias.add(franjas.getDia());
		}
		
	}
}




