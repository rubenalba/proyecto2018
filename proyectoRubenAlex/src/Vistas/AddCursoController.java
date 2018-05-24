package Vistas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Modelo.AsignaturaInterface;
import Modelo.CicloInterface;
import Modelo.UnidadFormativaInterface;
import dao.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import pojos.Asignatura;
import pojos.Ciclo;
import pojos.Unidadformativa;

public class AddCursoController implements Initializable {
	static CicloInterface c = DAO.getCicloInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
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
    
    public Asignatura asignaturaActiva;
    public Ciclo cicloActivo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarCiclo();
		
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
	public void 	addUF () {
	Ciclo cic = c.verCicloByName(cursoActivo);
	Asignatura asi = as.verAsignaturaByName(AsignaturaActiva, cursoActivo);
	asi.getNombreAsignatura();
	
				
		
	}
}
