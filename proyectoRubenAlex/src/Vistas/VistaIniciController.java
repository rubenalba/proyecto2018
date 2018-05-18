package Vistas;

import java.util.ArrayList;
import java.util.List;

import Modelo.ProfesorInterface;
import application.Main;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojos.Franjas;
import pojos.Profesor;
import dao.DAO;

public class VistaIniciController {
	static ProfesorInterface pr = DAO.getProfesorInterface();
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
    private Button ConfirmFranjaCB;

    @FXML
    private ChoiceBox<?> idFranjaCB;

    @FXML
    private ChoiceBox<?> UFranjaCB;

    boolean franjaVisible = true;

    private static Profesor profesorActivo;

	@FXML
	public void initialize() {
		profesorActivo=getProfesorActivo();
		//setVisibleFranja(false);
		//AnchorPane vistaInicial;
		//if (loader.is)
		cargarCursos();
	}
	private ObservableList<String> cursosList;


	public Profesor getProfesorActivo(){
		return profesorActivo;
	}

	public void setProfesorActivo(String usuarioActivo){
		profesorActivo = pr.verProfesorByUser(usuarioActivo);
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

	public void cargarCursos(){
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
	}
	@FXML
	public void configuracion(){
		try {
			Stage Actual = (Stage) BtnAjustes.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("../Vistas/VistaConfiguracion.fxml"));
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
			Stage Actual2 = (Stage) BtnAjustes.getScene().getWindow();
			Actual2.close();
			e.printStackTrace();
	    }
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

	@FXML
	public void verUFAsignaturaSelected(String asignatura){
		List <String> ufs = pr.UFSimpartidas(asignatura, profesorActivo.getDniProfesor());
		ObservableList<String>ufsimpartidas = FXCollections.observableArrayList(ufs);
		ListaUfs.setItems(ufsimpartidas);
	}


}

