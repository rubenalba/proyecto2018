package Vistas;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import Modelo.AsignaturaInterface;
import Modelo.ProfesorInterface;
import dao.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import pojos.Asignatura;
import pojos.Profesor;


public class HomeController implements Initializable{
static AsignaturaInterface a = DAO.getAsignaturaInterface();
static ProfesorInterface p = DAO.getProfesorInterface();
    @FXML
    private ListView<String> lista;
    static String usuarioActual;
    static Profesor profesorActual;

    public void currentUser() {
    	VistaLoginController v = new VistaLoginController();
    	usuarioActual = v.getUsuarioActivo();
     profesorActual = p.verProfesorByUser(usuarioActual);
     }

    private void cargaLista() {
    	List<String>asig = p.asignaturasImpartidas(profesorActual.getDniProfesor());
    	ObservableList<String> cursosImpartidos = FXCollections.observableArrayList(asig);
    	lista.setItems(cursosImpartidos);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		currentUser();
		cargaLista();

	}
}
