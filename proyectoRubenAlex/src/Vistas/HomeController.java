package Vistas;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import Modelo.AsignaturaInterface;
import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import pojos.Asignatura;


public class HomeController {//implements Initializable{
static AsignaturaInterface a = DAO.getAsignaturaInterface();
    @FXML
    private ListView<Asignatura> lista;
   
   /* @Override
	public void initialize(URL location, ResourceBundle resources) {
		cargaLista();
		
	}*/
    private void cargaLista() {
    	ObservableList<Asignatura> asig;
    	asig = FXCollections.observableArrayList();
    	List <Asignatura> listaAsignaturas = a.verAllAsignaturas();
    	for (Iterator listaAsig = listaAsignaturas.iterator();listaAsig.hasNext();) {
    		Asignatura asignat = (Asignatura)listaAsig.next();
    		asig.add(asignat);
    	}
    	lista.setItems(asig);
    }


	
}
