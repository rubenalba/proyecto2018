package Vistas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Modelo.AsignaturaInterface;
import Modelo.CicloInterface;
import Modelo.ProfesorInterface;
import Modelo.UnidadFormativaInterface;
import application.Main;
import dao.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pojos.Asignatura;
import pojos.Ciclo;
import pojos.Profesor;
import pojos.Unidadformativa;

public class AddCursoController implements Initializable {
	static CicloInterface c = DAO.getCicloInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static ProfesorInterface p = DAO.getProfesorInterface();
    @FXML
    private ChoiceBox<String> cursos;
    @FXML
    private TextField horasTF;

    private ObservableList<String> listaCiclos;

    private ObservableList<String>ufs;

    @FXML
    private ChoiceBox<String> ufCB;

    @FXML
    private ChoiceBox<String> asignaturaCB;

    private ObservableList<String>asig;
    @FXML
    private Button addUF;
    private String cursoActivo;
    private String AsignaturaActiva;

    @FXML
    private Button volverBTN;

    public Asignatura asignaturaActiva;
    public Ciclo cicloActivo;
    private Profesor profesorActivo;


	public Profesor profesorActivo() {
		VistaIniciController v = new VistaIniciController();
		profesorActivo = v.getProfesorActivo();
		return profesorActivo;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarCiclo();
		profesorActivo();


		asignaturaCB.setVisible(false);
		ufCB.setVisible(false);

		cursos.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (cursos != null) {
					asignaturaCB.setVisible(true);
					cursoActivo = cursos.getValue();
					cargarAsignatura(cursoActivo);
					asignaturaCB.valueProperty().addListener(new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
							if (asignaturaCB != null) {
								ufCB.setVisible(true);
								AsignaturaActiva = asignaturaCB.getValue();
								System.out.println("asignatura ->" +  AsignaturaActiva);
								cargarUF(AsignaturaActiva);
							}

						}
					});
				}

			}
		});


	}
	public void cargarCiclo () {
		listaCiclos = FXCollections.observableArrayList();
		List <Ciclo> ciclo = c.verAllCiclos();
		for (Ciclo cicle : ciclo) {
			listaCiclos.add(cicle.getNombreCiclo());
		}
		cursos.setItems(listaCiclos);
	}

	public void cargarUF(String asignatura) {
		ufs = FXCollections.observableArrayList();
		List <Unidadformativa> uf = u.ufByCiclo(asignatura);
		for (Unidadformativa unidadformativa : uf) {
			ufs.add(unidadformativa.getNombreUf());
		}
		ufCB.setItems(ufs);
	}

	public void cargarAsignatura(String curso) {
		asig = FXCollections.observableArrayList();
		List <Asignatura> a = as.verAsignaturaByCurso(curso);
		for (Asignatura asignatura : a) {
			asig.add(asignatura.getNombreAsignatura());
		}
		asignaturaCB.setItems(asig);
	}
	@FXML
	public void addUF () {
	Ciclo cic = c.verCicloByName(cursoActivo);
	Asignatura asi = as.verAsignaturaByName(AsignaturaActiva, cursoActivo);
	asi.getIdAsignatura();
	System.out.println(cic.getNombreCiclo() + "id del ciclo -> "+ cic.getIdCiclo());
	System.out.println(asi.getIdAsignatura()  +" <---id de la asignatura"+ asi.getNombreAsignatura()+"<--nombre");
	String uf = ufCB.getValue();
	System.out.println(uf);

	Unidadformativa unid = u.verUFByName(cic.getIdCiclo(), asi.getIdAsignatura(), uf);
	System.out.println(unid.getIdUnidadFormativa() + " " + unid.getNombreUf());
	unid.setProfesor(profesorActivo);
	try {
		u.modificarUnidadFormativa(unid);
		VistaIniciController vc = new VistaIniciController();
		vc.cargarCursos();
		System.out.println("sí");
	} catch (Exception e) {
		System.out.println("no");
	}
	/*String se = "DAM1";
	Ciclo ci = c.verCicloByName(se);
		System.out.println(ci.getNombreCiclo());
		String name = "M4 - Llenguatge de Marques";
		Asignatura asi = as.verAsignaturaByName(name, se);
		System.out.println(asi.getNombreAsignatura() + "<----");
		*/



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
}
