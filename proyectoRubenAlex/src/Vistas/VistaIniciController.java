package Vistas;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import Modelo.AlumnosInterface;
import Modelo.AsignaturaInterface;
import Modelo.AsistenciaInterface;
import Modelo.CicloInterface;
import Modelo.FranjaInterface;
import Modelo.HorasInterface;
import Modelo.MatriculaInterface;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.AsistenciaId;
import pojos.Ciclo;
import pojos.Franjas;
import pojos.Horas;
import pojos.Matricula;
import pojos.MatriculaId;
import pojos.Profesor;
import pojos.Unidadformativa;
import dao.DAO;
/**
 * Controlador de la vista inicial del programa
 * @author cfgs
 *
 */
public class VistaIniciController {
	public static final int PORCENTAJE = 25;
	static ProfesorInterface pr = DAO.getProfesorInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static HorasInterface h = DAO.getHorasInterface();
	static FranjaInterface fr = DAO.getFranjaInterface();
	static AsistenciaInterface ast = DAO.getAsistenciaInterface();
	static CicloInterface c = DAO.getCicloInterface();
	static AlumnosInterface a = DAO.getAlumnosInterface();
	static MatriculaInterface m = DAO.getMatriculaInterface();

	@FXML
	private MenuItem cerrarSession;
	@FXML
	private MenuItem Salir;
	@FXML
	private MenuItem Ajustes;
	@FXML
	private Menu Opciones, SalirMENU;
	@FXML
	private MenuBar Menu;

    @FXML
    private Pane PanePantallaEntera;
	@FXML
	private MenuItem BtnInfo;
	@FXML
	private Label fechaLabel, CursoLabel,EliminaLabel, Allabel, curLb ;



	@FXML
	private TableColumn<String, String> ColHorario;

	@FXML
	private TableColumn<Franjas, String> ColFranja;

	@FXML
	private ListView<String> ListaCursos, ListaUfs;

	@FXML
	private Button BtnCerrarSession, BtnVolverConfig, volverFranja;

	private ObservableList<String> alumnosList;

	@FXML
	private Button addFranja;
	@FXML
	private Button btnAddFranja;
	@FXML
	private Button addCursoBTN;

	@FXML
	private Button ConfirmFranjaCB;
	@FXML
	private Button btneliminarFaltaAsistencia;
	@FXML
	private ChoiceBox<Horas> CBHoraFranja;
	@FXML
	private ChoiceBox<?> idFranjaCB;

	@FXML
	private ChoiceBox<Unidadformativa> UFranjaCB, CBUfBorrar;
	@FXML
	private ChoiceBox<String> diasSemana;
	@FXML
	private TableView<Asignatura> tablaCursos;

	@FXML
	private TableColumn<Asignatura, String> ColCursos;

	@FXML
	private TableView<Unidadformativa> TablaUFs;

	@FXML
	private TableColumn<Unidadformativa, String> ColUF;
	@FXML
	private ChoiceBox<Asignatura> AsigFranja, CBAsignaturaBorrar;
	@FXML
	private AnchorPane PaneAddUF;
	@FXML
	private AnchorPane paneAddFranja;

	@FXML
	private Button AlumnosBTN, btnEliminarUFProf, btnEliminarFranja;

	@FXML
	private ChoiceBox<Ciclo> cursos;
	@FXML
	private TextField horasTF;

	private ObservableList<Ciclo> listaCiclos;

	private ObservableList<Unidadformativa>ufs;

	@FXML
	private ChoiceBox<Unidadformativa> ufCB;

	@FXML
	private ChoiceBox<Asignatura> asignaturaCB;

	private ObservableList<Asignatura>asig;
	@FXML
	private Button addUF;
	private Ciclo cursoActivo;
	private Asignatura AsignaturaActiva;

	public Asignatura asignaturaActiva;
	public Ciclo cicloActivo;

	@FXML
	private TableView<Alumnos> tablaPruebas;

	@FXML
	private TableColumn<Alumnos, String> ColNom;
	@FXML
	private TextField alumnoBuscar;

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
	private Button BtnGenerarAsistencia, search;
	Calendar calendario = new GregorianCalendar();
	int hora, minutos, segundos;
	@FXML
	private TableColumn<Alumnos, String> ColAsistencia;
	@FXML
	private TableView<Alumnos> tablaBusqueda;
	@FXML
	private TableColumn<Alumnos, String> ColNombreBusqueda;
	@FXML
	private TableColumn<Alumnos, String> colDNIBusqueda;

	//-----------------------------------------------
	@FXML
	private AnchorPane VentanaPrincipal;
	@FXML
	private AnchorPane VentanaAlumnos;

	@FXML
	private Button volverBTN;
	LocalDate today;

	@FXML
	private Button volverBTN1;

	@FXML
	private AnchorPane PaneAddAlumno;

	@FXML
	private ChoiceBox<Ciclo> cursosAlumno;

	@FXML
	private ChoiceBox<Unidadformativa> ufCBAlumno;

	@FXML
	private ChoiceBox<Asignatura> asignaturaCB1Alumno;

	@FXML
	private Button matricularBTN;

	@FXML
	private Button addAlumno;

	@FXML
	private Button volverBTNAlumno;

	ObservableList<Alumnos> listaAlumnos;

	private ResourceBundle bundle;
	private Locale locale;

	public static String vlc;
	static VistaLoginController v = new VistaLoginController();

	public String getIdioma() {
		System.out.println("Etoy mirando el idioma " + vlc);
		return vlc;
	}

	boolean franjaVisible = true;
	private ObservableList<String> cursosList;
	private static Profesor profesorActivo;
	private static String passwordUsada;

	public static String getPasswordUsada() {
		return passwordUsada;
	}

	private static String obtenerlang() {
		vlc = v.getLangActivo();
		return vlc;
	}
	private void cargarIdioma (String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("resources.lang", locale);
		Opciones.setText(bundle.getString("Opciones"));
		Ajustes.setText(bundle.getString("Ajustes"));
		Salir.setText(bundle.getString("Salir"));
		ColCursos.setText(bundle.getString("ColCursos"));
		addCursoBTN.setText(bundle.getString("addCursoBTN"));
		AlumnosBTN.setText(bundle.getString("AlumnosBTN"));
		ColAlumnos.setText(bundle.getString("ColAlumnos"));
		Checkers.setText(bundle.getString("Checkers"));
		fechaLabel.setText(bundle.getString("fechaLabel"));
		volverBTN.setText(bundle.getString("volverBTN"));
		btneliminarFaltaAsistencia.setText(bundle.getString("btneliminarFaltaAsistencia"));
		CursoLabel.setText(bundle.getString("CursoLabel"));
		addUF.setText(bundle.getString("addUF"));
		volverBTN1.setText(bundle.getString("volverBTN1"));
		btnEliminarUFProf.setText(bundle.getString("btnEliminarUFProf"));
		curLb.setText(bundle.getString("curLb"));
		search.setText(bundle.getString("search"));
		BtnInfo.setText(bundle.getString("BtnInfo"));
		ColNombreBusqueda.setText(bundle.getString("ColNombreBusqueda"));
		volverBTNAlumno.setText(bundle.getString("volverBTNAlumno"));
		volverFranja.setText(bundle.getString("volverFranja"));
		if (lang =="cat") {
			alumnoBuscar.setPromptText("Nom, Cognom o DNI");
		}else {
			alumnoBuscar.setPromptText("Nombre, apellido o DNI");
		}
		SalirMENU.setText(bundle.getString("SalirMENU"));
		cerrarSession.setText(bundle.getString("cerrarSession"));

	}

	public static void setPasswordUsada(String passwordUsada) {
		VistaIniciController.passwordUsada = passwordUsada;
	}
	private static Asignatura asignaturaMarcada;
	private static Alumnos alumnoMarcado;

	public static Alumnos getAlumnoMarcado() {
		return alumnoMarcado;
	}

	public static void setAlumnoMarcado(Alumnos alumnoMarcado) {
		VistaIniciController.alumnoMarcado = alumnoMarcado;
	}
	private static Unidadformativa UFMarcada;

	public static Unidadformativa getUFMarcada() {
		return UFMarcada;
	}

	/*********************************************************
	 *	COMIENZAN LOS MÉTODOS
	 * @throws IOException
	 ********************************************************/
	@FXML
	public void initialize()  {
		profesorActivo=getProfesorActivo();
		comprobacionTemporal();
		cargarCursos();
		vlc = obtenerlang();
		cargarIdioma(vlc);


		VentanaAlumnos.setVisible(false);
		VentanaPrincipal.setVisible(true);
		PaneAddUF.setVisible(false);
		PaneAddAlumno.setVisible(false);
		paneAddFranja.setVisible(false);

		tablaAlumnos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				if (!passwordUsada.equals(profesorActivo.getPasswordTemp())){
					setAlumnoMarcado(newSelection);
					abrirAlumno(alumnoMarcado);
				}
			}
		});

		asignaturaCB.setVisible(true);
		ufCB.setVisible(true);


		listeners();
		listeners2();

	}
	//creo que no hace nada
	private void cargarAlumnos() {
		listaAlumnos = FXCollections.observableArrayList();
		List <Alumnos> alumne = a.verTodosAlumnos();
		for (Alumnos alumnos : alumne) {
			listaAlumnos.add(alumnos);
		}
		tablaBusqueda.setItems(listaAlumnos);
	}
	/**
	 * Declara los listeners de las tablas iniciales.
	 */
	private void listeners() {
		cursos.valueProperty().addListener(new ChangeListener<Ciclo>() {

			@Override
			public void changed(ObservableValue<? extends Ciclo> observable, Ciclo oldValue, Ciclo newValue) {
				if (cursos != null) {
					asignaturaCB.setVisible(true);
					cursoActivo = cursos.getValue();
					try{
						cargarAsignatura(cursoActivo.getNombreCiclo());

					} catch(Exception e){

					}
				}
			}
		});
		asignaturaCB.valueProperty().addListener(new ChangeListener<Asignatura>() {

			@Override
			public void changed(ObservableValue<? extends Asignatura> observable, Asignatura oldValue,Asignatura newValue) {
				if (asignaturaCB != null) {
					ufCB.setVisible(true);
					AsignaturaActiva = asignaturaCB.getSelectionModel().getSelectedItem();
					try{
						cargarUF(AsignaturaActiva.getNombreAsignatura());
					} catch(Exception e){

					}
				}

			}
		});
	}

	/**
	 * Declara los listeners de todos los choiceBox y tablas de las diferentes acciones.
	 */
	private void listeners2() {
		cursosAlumno.valueProperty().addListener(new ChangeListener<Ciclo>() {

			@Override
			public void changed(ObservableValue<? extends Ciclo> observable, Ciclo oldValue, Ciclo newValue) {
				if (cursosAlumno != null) {
					asignaturaCB1Alumno.setVisible(true);
					cursoActivo = cursosAlumno.getSelectionModel().getSelectedItem();
					try {
						cargarAsignatura2(cursoActivo.getNombreCiclo());
					} catch (Exception e) {

					}
				}
			}
		});
		asignaturaCB1Alumno.valueProperty().addListener(new ChangeListener<Asignatura>() {

			@Override
			public void changed(ObservableValue<? extends Asignatura> observable, Asignatura oldValue, Asignatura newValue) {
				if (asignaturaCB1Alumno != null) {
					ufCBAlumno.setVisible(true);
					AsignaturaActiva = asignaturaCB1Alumno.getValue();
					try {
						cargarUF2(AsignaturaActiva.getNombreAsignatura());
					} catch (Exception e){

					}
				}
			}
		});

		CBAsignaturaBorrar.valueProperty().addListener(new ChangeListener<Asignatura>(){
			@Override
			public void changed(ObservableValue<? extends Asignatura> observable, Asignatura oldValue, Asignatura newValue) {
				if (CBAsignaturaBorrar != null) {
					Asignatura asigborrar = CBAsignaturaBorrar.getSelectionModel().getSelectedItem();
					if (asigborrar != null){
						List<Unidadformativa> ufsborrar = pr.misUFs(profesorActivo, asigborrar);
						CBUfBorrar.setItems(FXCollections.observableArrayList(ufsborrar));}
				}
			}
		});
	}

	public Profesor getProfesorActivo(){
		return profesorActivo;
	}

	public void setProfesorActivo(String usuarioActivo){
		profesorActivo = pr.verProfesorByUser(usuarioActivo);
	}

	public Unidadformativa getUnidadFormativa() {
		return UFMarcada;
	}

	/**
	 * Metodo para cerrar session, regresa a la ventana de login.
	 */
	@FXML
	public void cerrarSesion(){
		Stage Actual = (Stage) tablaCursos.getScene().getWindow();
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
			if (vlc=="es") {
				alert.setHeaderText("Error al cerrar sessión");
				alert.setContentText("No se ha podido desconectar correctamente, el programa se cerrara.");
				alert.showAndWait();
				Stage Actual2 = (Stage) BtnCerrarSession.getScene().getWindow();
				Actual2.close();
			}else {
				alert.setHeaderText("Error al tancar sessió");
				alert.setContentText("No s'ha pogut desconectar correctament, el programa es tancarà.");
				alert.showAndWait();
				Stage Actual2 = (Stage) BtnCerrarSession.getScene().getWindow();
				Actual2.close();
			}
		}
	}
	/**
	 * Oculta los items no usados en ese momento
	 */
	@FXML
	public void back() {
		VentanaAlumnos.setVisible(false);
		VentanaPrincipal.setVisible(true);
		PaneAddUF.setVisible(false);
		PaneAddAlumno.setVisible(false);
		paneAddFranja.setVisible(false);
	}
	/**
	 * Carga los ciclos a la hora de asignarlos a un profesor
	 */
	public void cargarCiclo() {
		listaCiclos = FXCollections.observableArrayList();
		List <Ciclo> ciclo = c.verAllCiclos();
		for (Ciclo cicle : ciclo) {
			listaCiclos.add(cicle);
		}
		cursos.setItems(listaCiclos);
	}

	/**
	 * Carga los ciclos a la hora de asignarlos a un alumno
	 */
	public void cargarCiclo2 () {
		listaCiclos = FXCollections.observableArrayList();
		List <Ciclo> ciclo = c.verAllCiclos();
		for (Ciclo cicle : ciclo) {
			listaCiclos.add(cicle);
		}
		cursosAlumno.setItems(listaCiclos);
	}

	/**
	 * Carga las de una asignatura UFS a la hora de asignarlos a un profesor
	 * @param asignatura, asignatura de la que se cargaran las ufs
	 */
	public void cargarUF(String asignatura) {
		ufs = FXCollections.observableArrayList();
		List <Unidadformativa> uf = u.ufByCiclo(asignatura);
		for (Unidadformativa unidadformativa : uf) {
			ufs.add(unidadformativa);
		}
		ufCB.setItems(ufs);
	}
	/**
	 * Carga las de una asignatura UFS a la hora de asignarlos a un alumno
	 * @param asignatura, asignatura de la que se cargaran las ufs
	 */
	public void cargarUF2(String asignatura) {
		ufs = FXCollections.observableArrayList();
		List <Unidadformativa> uf = u.ufByCiclo(asignatura);
		for (Unidadformativa unidadformativa : uf) {
			ufs.add(unidadformativa);
		}
		ufCBAlumno.setItems(ufs);
	}
	/**
	 * Carga las asignaturas pertenecientes a un ciclo para a�adirlos a un profesor
	 * @param curso, nombre del cilo del que cargara las asignaturas
	 */
	public void cargarAsignatura(String curso) {
		asig = FXCollections.observableArrayList();
		List <Asignatura> a = as.verAsignaturaByCurso(curso);
		for (Asignatura asignatura : a) {
			asig.add(asignatura);
		}
		asignaturaCB.setItems(asig);
	}

	/**
	 * Carga las asignaturas pertenecientes a un ciclo para a�adirlos a un alumno
	 * @param curso, nombre del cilo del que cargara las asignaturas
	 */
	public void cargarAsignatura2(String curso) {
		asig = FXCollections.observableArrayList();
		List <Asignatura> a = as.verAsignaturaByCurso(curso);
		for (Asignatura asignatura : a) {
			asig.add(asignatura);
		}
		asignaturaCB1Alumno.setItems(asig);
	}
	/**
	 * A�adira una matricula para un alumno en una uf determinada
	 */
	public void matricular() {
		MatriculaId mId = new MatriculaId(tablaBusqueda.getSelectionModel().getSelectedItem().getDni(),ufCBAlumno.getSelectionModel().getSelectedItem().getIdUnidadFormativa());
		Matricula mat = new Matricula(mId,tablaBusqueda.getSelectionModel().getSelectedItem(),ufCBAlumno.getSelectionModel().getSelectedItem());

		try {
			m.matricularAlumno(mat);
			Alert alert = new Alert(AlertType.INFORMATION);
			if(vlc=="es") {
				alert.setHeaderText(tablaBusqueda.getSelectionModel().getSelectedItem().getNombreCompleto() + " ha sido matriculado.");
			}else {
				alert.setHeaderText(tablaBusqueda.getSelectionModel().getSelectedItem().getNombreCompleto() + " s'ha matriculat correctament");
			}
			alert.showAndWait();
		}catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			if (vlc =="es") {
				alert.setHeaderText("No se ha podido matricular, inténtelo de nuevo.");
			}else {
				alert.setHeaderText("No s'ha pogut matricular, provi un altre cop.");
			}
			alert.showAndWait();
		}
	}

	@FXML
	/**
	 * Añade una uf determinada al profesor logueado
	 */
	public void addUF2DB () {
		Ciclo cic = c.verCicloByName(cursoActivo.getNombreCiclo());
		Asignatura asi = as.verAsignaturaByName(AsignaturaActiva.getNombreAsignatura(), cursoActivo.getIdCiclo());
		asi.getIdAsignatura();
		Unidadformativa uforma = ufCB.getSelectionModel().getSelectedItem();

		Unidadformativa unid = u.verUFByName(cic.getIdCiclo(), asi.getIdAsignatura(), uforma.getNombreUf());

		unid.setProfesor(profesorActivo);
		if (vlc == "es") {
			try {
				u.modificarUnidadFormativa(unid);
				Alert alert = new Alert(AlertType.INFORMATION);
				if(vlc=="es") {
				alert.setHeaderText("Has añadido la " + ufCB.getValue() + " a tus cursos." );
				}else {
					alert.setHeaderText("Has afegit la " + ufCB.getValue() + " als teus cursos." );
				}
				alert.showAndWait();
				cargarCursos();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				if(vlc=="es") {
				alert.setHeaderText("Error al añadir la "+ ufCB.getValue() + " a tus cursos." );
				}else {
					alert.setHeaderText("Error al afegir la "+ ufCB.getValue() + " al tus cursos." );
				}
				alert.showAndWait();
			}
		}

	}

	/**
	 * Carga los cursos impartidos por el profesor que ha hecho loggin y las UFs de estos cursos.
	 */
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


			}
		});

	}
	/**
	 * Carga en un choiceBox las asignaturas del profesor
	 */
	public void cargarAsigborrar(){
		CBAsignaturaBorrar.getItems().clear();
		List<Asignatura> asignaturasborrar = pr.misAsignaturas(profesorActivo);
		if (!asignaturasborrar.isEmpty())
			CBAsignaturaBorrar.setItems(FXCollections.observableArrayList(asignaturasborrar));
	}



	@FXML
	/**
	 * Inicia la ventana de configuracion
	 * @param event, evento que se genera al pulsar el boton
	 * @throws IOException, Se lanzara en caso de error al iniciar la ventana
	 */
	public void configuracion(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaConfiguracion.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	public void informacion(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../Vistas/informacion.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Configuración de la tabla donde se muestran todos los alumnos a la hora de pasar lista.
	 */
	private void setCheckBox(){
		//Seleccionar hora actual para generar las faltas;
		hora =calendario.get(Calendar.HOUR_OF_DAY);
		minutos = calendario.get(Calendar.MINUTE);
		listaNoAsistencia = new ArrayList<Alumnos>();
		String minuts ="";
		if (minutos<10)
			this.TextHoraAsistencia.setText(hora+":0"+minuts);
		else this.TextHoraAsistencia.setText(hora+":"+minutos);
		//-------------------------------------------------
		//Seleccionar el dia actual para generar las faltas
		today = LocalDate.now( ZoneId.of( "Europe/Paris" ) );

		DiaAsistenciaSelect.setValue(today);
		//-------------------------------------------------
		alumnosLista = FXCollections.observableArrayList(pr.misAlumnosByAsignatura(profesorActivo, UFMarcada));
		tablaAlumnos.setItems(alumnosLista);
		ColAlumnos.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("NombreCompleto"));
		List<Integer> faltas = FXCollections.observableArrayList();
		for (Alumnos alumnos : alumnosLista) {
			List<Asistencia> faltasAlumno = ast.verAllAsistenciasAlumnoUF(alumnos, UFMarcada);
			int porcentaje= (100*faltasAlumno.size())/UFMarcada.getHoras();
			if (porcentaje >= 10) ColAsistencia.setStyle("-fx-text-fill=red");
			alumnos.setTotal(porcentaje);
			ColAsistencia.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("FaltasUF"));
		}

		ColAsistencia.setCellFactory( column -> {
			return new TableCell<Alumnos, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item,  empty);
					if (item == null || empty) {

					}else {
						String itemsplit[] = item.split("%");
						int num = Integer.valueOf(itemsplit[0]);
						setText(item);
						if (num >= 25) {
							setTextFill(Color.RED);
						}
						else if(num >=15 && num <25) {
							setTextFill(Color.ORANGE);
						}
						else {
							setTextFill(Color.GREEN);
						}
					}
				}
			};
		});

		Checkers.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumnos, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Alumnos, Boolean> features) {
				return new SimpleBooleanProperty(features.getValue() != null);
			}
		});

		Checkers.setCellFactory(new ColumnCheckBox<Alumnos, Boolean>(){

			@Override
			public boolean checkValue(Alumnos element) {
				List<Asistencia> faltas = ast.verAllAsistenciasAlumnoUF(element, UFMarcada);
				boolean existe = false;
				for (Asistencia asistencia : faltas) {
					if (asistencia.getId().getFecha().equals(DiaAsistenciaSelect.getValue().toString())){
						existe = true;
					}
				}
				return existe;
			}

			@Override
			public boolean checkEditable(Alumnos element) {
				return true;
			}

			@Override
			public void checkAction(int index, Alumnos element, boolean value) {
				if (value){
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
	/**
	 * Muestra la ventana de A�adir unidad formativa
	 * @throws IOException, lanzara esta excepcion en caso de error al modificar la visibilidad de esta.
	 */
	@FXML
	public void addUF() throws IOException {
		cargarAsigborrar();
		cargarCiclo();
		VentanaAlumnos.setVisible(false);
		VentanaPrincipal.setVisible(false);
		PaneAddUF.setVisible(true);
		PaneAddAlumno.setVisible(false);
		paneAddFranja.setVisible(false);
	}
	/**
	 * Muestra la ventana de Añadir alumno
	 * @throws IOException, lanzara esta excepcion en caso de error al modificar la visibilidad de esta.
	 */
	@FXML
	public void addAlumno() throws IOException {
		cargarCiclo2();
		VentanaAlumnos.setVisible(false);
		VentanaPrincipal.setVisible(false);
		PaneAddUF.setVisible(false);
		PaneAddAlumno.setVisible(true);
		paneAddFranja.setVisible(false);

	}
	/**
	 * Muestra la ventana de Añadir franja
	 * @throws IOException, lanzara esta excepcion en caso de error al modificar la visibilidad de esta.
	 */
	@FXML
	public void addFranja() throws IOException {
		cargarHoras();
		VentanaAlumnos.setVisible(false);
		VentanaPrincipal.setVisible(false);
		PaneAddUF.setVisible(false);
		PaneAddAlumno.setVisible(false);
		paneAddFranja.setVisible(true);
	}

	/**
	 * Genera falta de asistencia a partir de la fecha y hora indicadas
	 */
	public void generarFaltas(){
		Horas horaFalta = h.getHorasByRango(TextHoraAsistencia.getText());

		if (horaFalta == null) {
			Alert alert = new Alert (AlertType.INFORMATION);
			if(vlc == "es") {
				alert.setHeaderText("Introduzca la hora de la falta");
				alert.showAndWait();
			}else {
				alert.setHeaderText("Introdueixi l'hora de la falta");
				alert.showAndWait();
			}
		}else {
			Asignatura asignaturaFalta = as.verAsignaturaById(UFMarcada.getAsignatura().getIdAsignatura());
			String fecha = DiaAsistenciaSelect.getValue().toString();
			String dia = DiaAsistenciaSelect.getValue().getDayOfWeek().name();
			Franjas franjaFalta = null;
			try {
				franjaFalta	 = fr.verFranjaFalta(horaFalta, profesorActivo, dia, asignaturaFalta);
			} catch(Exception e){
				Alert alert = new Alert (AlertType.INFORMATION);
				if(vlc =="es") {
					alert.setHeaderText("Dia incorrecto para esta UF");
					alert.showAndWait();
				}else {
					alert.setHeaderText("Dia incorrecte per aquesta UF");
					alert.showAndWait();
				}
			}
			if (listaNoAsistencia.isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
				if (vlc=="es") {
					alert.setHeaderText("Indica las faltas a generar");
					alert.showAndWait();
				}else {
					alert.setHeaderText("Indica les faltes a generar");
					alert.showAndWait();
				}
			} else {
				Asistencia falta = null;
				boolean mostrado = false;
				for (Alumnos alumnos : listaNoAsistencia) {
					boolean error = false;
					try {
						falta = new Asistencia();
						AsistenciaId a = new AsistenciaId(alumnos.getDni(), UFMarcada.getIdUnidadFormativa(), franjaFalta.getIdFranja(), fecha);
						falta.setId(a);
					} catch(Exception e){
						if (!mostrado){
							Alert alert = new Alert (AlertType.INFORMATION);
							if (vlc == "es") {
								alert.setHeaderText("Dia incorrecto para esta UF");
								alert.showAndWait();
							}else {
								alert.setHeaderText("Dia incorrecte per aquesta UF");
								alert.showAndWait();
							}
							error = true;
							mostrado = true;
						}
						listaNoAsistencia = new ArrayList<Alumnos>();
					}
					if (!error){
						try {
							ast.addAsistencia(falta);
							Alert alert2 = new Alert(AlertType.INFORMATION);
							if (vlc=="es") {
								alert2.setHeaderText("Falta añadida correctamente");
								alert2.showAndWait();
							}else {
								alert2.setHeaderText("Falta afegida correctament");
								alert2.showAndWait();
							}
						} catch(Exception e){
							if (!mostrado){
								Alert alert = new Alert(AlertType.ERROR);
								if (vlc=="es") {
									alert.setHeaderText("Falta de asistencia duplicada");
									alert.showAndWait();
								}else {
									alert.setHeaderText("Falta d' asistència duplicada");
									alert.showAndWait();
								}
								mostrado = true;
								error = true;
							}
							e.printStackTrace();
							listaNoAsistencia = new ArrayList<Alumnos>();
						}
					}
				}
				setCheckBox();
			}
		}
	}
	/**
	 * Metodo para abrir la vista de un alumno con la informacion de este.
	 * @param newSelection Alumno del que se cargaran los datos
	 */
	public void abrirAlumno(Alumnos newSelection){
		Parent root;
		alumnoMarcado = newSelection;
		try {

			root = FXMLLoader.load(getClass().getResource("../Vistas/VistaAlumno.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(tablaAlumnos.getScene().getWindow());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			if(vlc=="es") {
				alert.setTitle("Error");
				alert.setHeaderText("Error al cargar la ventana de alumno, vuelva a intentarlo");
				alert.showAndWait();
			}else {
				alert.setTitle("Error");
				alert.setHeaderText("Error al carregar la finestra de l'alumne, torni a provar-ho");
				alert.showAndWait();
			}
		}
	}
	/**
	 * Cerrara el programa completamente
	 */
	@FXML
	public void salir() {
		Stage Actual = (Stage) tablaCursos.getScene().getWindow();
		Actual.close();
		System.exit(0);
	}
	/**
	 * Buscar alumno concreto a partir de un nombre o dni
	 */
	@FXML
	public void buscar(){
		String alumno = alumnoBuscar.getText();
		List<Alumnos> alumnos = a.verAlumnobyName(alumno);
		tablaBusqueda.setItems(FXCollections.observableArrayList(alumnos));
		for (Alumnos alumnos2 : alumnos) {
			ColNombreBusqueda.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombreCompleto"));
			colDNIBusqueda.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("Dni"));
		}
	}
	/**
	 * Cargara la informacion de la ventana de a�adir franja
	 */
	public void cargarHoras(){
		List<Horas> horas = h.getAllHoras();
		List<Asignatura> asignaturasStr = pr.misAsignaturas(profesorActivo);
		ObservableList observablehoras = FXCollections.observableArrayList(horas);
		CBHoraFranja.setItems(observablehoras);
		List<String> dias  = new ArrayList<String>();
		dias.add("LUNES");
		dias.add("MARTES");
		dias.add("MIÉRCOLES");
		dias.add("JUEVES");
		dias.add("VIERNES");
		ObservableList value = FXCollections.observableArrayList(dias);
		diasSemana.setItems(value);
		ObservableList asignaturas = FXCollections.observableArrayList(asignaturasStr);
		AsigFranja.setItems(asignaturas);
	}
	/**
	 * A�ade la franja seleccionada al profesor
	 */
	public void guardarFranja(){
		String dia ="";
		if (diasSemana.getValue().equals("LUNES")){
			dia ="MONDAY";
		}else if (diasSemana.getValue().equals("MARTES")){
			dia ="TUESDAY";
		}else if (diasSemana.getValue().equals("MIÉRCOLES")){
			dia ="WEDNESDAY";
		}else if (diasSemana.getValue().equals("JUEVES")){
			dia ="THURSDAY";
		}else if (diasSemana.getValue().equals("VIERNES")){
			dia ="FRIDAY";
		}
		Franjas franja = new Franjas(CBHoraFranja.getSelectionModel().getSelectedItem(), AsigFranja.getSelectionModel().getSelectedItem(), profesorActivo, dia);
		try{
			fr.addFranja(franja);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Franja creada");
			alert.showAndWait();
		} catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Franja duplicada");
			alert.showAndWait();
		}
	}
	/**
	 * Elimina la UF seleccionada del profesor
	 */
	public void eliminarUFProfesor(){
		Unidadformativa uforma = CBUfBorrar.getSelectionModel().getSelectedItem();
		uforma.setProfesor(null);
		try {
			u.quitarUFprofesir(uforma.getIdUnidadFormativa());
			Alert alert = new Alert(AlertType.INFORMATION);
			if (vlc == "es") {
				alert.setHeaderText("Has eliminado la " + CBUfBorrar.getValue() + " de tus cursos." );
				alert.showAndWait();
			}else {
				alert.setHeaderText("Has eliminat l'  " + CBUfBorrar.getValue() + " dels teus cursos." );
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			if (vlc == "es") {
				alert.setHeaderText("Error al eliminar la "+ CBUfBorrar.getValue() + " de tus cursos." );
				alert.showAndWait();
			}else {
				alert.setHeaderText("Error al eliminar l'  "+ CBUfBorrar.getValue() + " dels teus cursos." );
				alert.showAndWait();
			}
		}
		CBUfBorrar.getItems().clear();
		cargarAsigborrar();
	}
	/**
	 * Comprueba si el loggin se ha realizado con una password temporal o no y a partir del resultado mostrara unas opciones u otras
	 */
	public void comprobacionTemporal(){
		if (passwordUsada.equals(profesorActivo.getPassword())){
			if (profesorActivo.getPasswordTemp()!= null){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				if (vlc=="es") {
					alert.setHeaderText("Password temporal activa, desea desactivarla?");
				}else {
					alert.setHeaderText("Password temporal activa, desitja desactivar-la?");
				}
				Optional<ButtonType> result = alert.showAndWait();

				if(result.isPresent()&& result.get() == ButtonType.OK){
					profesorActivo.setPasswordTemp(null);
					pr.modificarProfesor(profesorActivo);
				}
			}
		}
		if(passwordUsada.equals(profesorActivo.getPasswordTemp())){
			addCursoBTN.setVisible(false);
			AlumnosBTN.setVisible(false);
			btnAddFranja.setVisible(false);
			Opciones.setVisible(false);
		}
	}
	/**
	 * Elimina una falta de asistencia de los alumnos marcados
	 */
	public void eliminarFaltaAsistencia(){
		Horas horaFalta = h.getHorasByRango(TextHoraAsistencia.getText());
		if (horaFalta == null) {
			Alert alert = new Alert (AlertType.INFORMATION);
			if(vlc=="es") {
				alert.setHeaderText("Introduzca la hora de la falta");
				alert.showAndWait();
			}else {
				alert.setHeaderText("Introdueixi l' hora de la falta");
				alert.showAndWait();
			}
		}else {
			Asignatura asignaturaFalta = as.verAsignaturaById(UFMarcada.getAsignatura().getIdAsignatura());
			String fecha = DiaAsistenciaSelect.getValue().toString();
			String dia = DiaAsistenciaSelect.getValue().getDayOfWeek().name();
			Franjas franjaFalta = null;
			try {
				franjaFalta	 = fr.verFranjaFalta(horaFalta, profesorActivo, dia, asignaturaFalta);
			} catch(Exception e){
				Alert alert = new Alert (AlertType.INFORMATION);
				if(vlc=="es") {
					alert.setHeaderText("Dia incorrecto para esta UF");
				}else {
					alert.setHeaderText("Dia incorrecte per aquesta UF");
				}
				alert.showAndWait();
			}
			if (listaNoAsistencia.isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
				if (vlc == "es") {
					alert.setHeaderText("Indica faltas a generar");
				}else {
					alert.setHeaderText("Indiqui les faltas a generar");
				}
				alert.showAndWait();
			} else {
				Asistencia falta = null;
				boolean mostrado = false;
				for (Alumnos alumnos : listaNoAsistencia) {
					boolean error = false;
					try {
						falta = new Asistencia();
						AsistenciaId a = new AsistenciaId(alumnos.getDni(), UFMarcada.getIdUnidadFormativa(), franjaFalta.getIdFranja(), fecha);
						falta.setId(a);
					} catch(Exception e){
						if (!mostrado){
							Alert alert = new Alert (AlertType.INFORMATION);
							if (vlc=="es") {
								alert.setHeaderText("Dia incorrecto para esta UF");
							}else {
								alert.setHeaderText("Dia incorrecte per aquesta UF");
							}
							alert.showAndWait();
							error = true;
							mostrado = true;
						}
						listaNoAsistencia = new ArrayList<Alumnos>();
					}
					if (!error){
						try {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							if (vlc== "es") {
								alert.setHeaderText("Desea eliminar las faltas de asistencia indicadas?");
							}else {
								alert.setHeaderText("Vol eliminar les faltes d'asistència indicades?");
							}
							Optional<ButtonType> result = alert.showAndWait();
							if(result.isPresent()&& result.get() == ButtonType.OK){
								ast.eliminarAsistencia(falta.getId());
								Alert alert2 = new Alert(AlertType.INFORMATION);
								if(vlc=="es") {
									alert2.setHeaderText("Falta eliminada correctamente");
								}else {
									alert2.setHeaderText("Falta eliminada correctament");
								}
								alert2.showAndWait();
							}

						} catch(Exception e){
							if (!mostrado){
								Alert alert = new Alert(AlertType.ERROR);
								if(vlc=="es") {
									alert.setHeaderText("Falta de asistencia duplicada");
								}else {
									alert.setHeaderText("Falta de asistència duplicada");
								}
								alert.showAndWait();
								mostrado = true;
								error = true;
							}
							e.printStackTrace();
							listaNoAsistencia = new ArrayList<Alumnos>();
						}
					}
				}
				setCheckBox();
			}
		}
	}
	/**
	 * Elimina la franja seleccionada del profesor
	 */
	public void eliminarFranjaProfesor(){
		Franjas franja;

		try{

			Horas hora = CBHoraFranja.getSelectionModel().getSelectedItem();
			Asignatura asig =  AsigFranja.getSelectionModel().getSelectedItem();
			String dia = diasSemana.getSelectionModel().getSelectedItem();
			if (diasSemana.getValue().equals("LUNES")){
				dia ="MONDAY";
			}else if (diasSemana.getValue().equals("MARTES")){
				dia ="TUESDAY";
			}else if (diasSemana.getValue().equals("MIÉRCOLES")){
				dia ="WEDNESDAY";
			}else if (diasSemana.getValue().equals("JUEVES")){
				dia ="THURSDAY";
			}else if (diasSemana.getValue().equals("VIERNES")){
				dia ="FRIDAY";
			}
			franja = fr.verFranjaFalta(hora, profesorActivo, dia, asig) ;
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			if (vlc=="es") {
				alert2.setHeaderText("¿Está seguro que desea eliminar esta franja?");
			}else {
				alert2.setHeaderText("De debó vol eliminar aquesta franja?");
			}
			Optional<ButtonType> result = alert2.showAndWait();
			if(result.isPresent()&& result.get() == ButtonType.OK){
				fr.eliminarFranja(franja.getIdFranja());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Franja Eliminada");
				alert.showAndWait();
			}else {

			}
		} catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			if (vlc=="es") {
				alert.setHeaderText("No se ha podido eliminar la franja");
			}else {
				alert.setHeaderText("No s'ha pogut eliminar la franja");
			}
			alert.showAndWait();
		}
	}
}




