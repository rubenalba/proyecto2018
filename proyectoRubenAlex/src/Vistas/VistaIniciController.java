package Vistas;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import pojos.Franjas;

public class VistaIniciController {

	@FXML
	private Button BtnInfo;

	@FXML
	private TableColumn<String, String> ColHorario;

	@FXML
	private TableColumn<Franjas, String> ColFranja;

	@FXML
	private ListView<?> ListaCursos;

	@FXML
	private Button BtnCerrarSession;

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

	@FXML
	public void initialize() {
		setVisibleFranja(false);

		if (this.ColHorario != null) {
			//this.ColHorario.setMinWidth(80);
			//this.ColHorario.setMaxWidth(80);

		}

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
}

