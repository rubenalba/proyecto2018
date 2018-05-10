package Vistas;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	public void initialize() {
		if (this.ColHorario != null) {
			//this.ColHorario.setMinWidth(80);
			//this.ColHorario.setMaxWidth(80);

			this.ColHorario.setCellValueFactory(
                    new Callback<TableColumn.CellDataFeatures<String, String>,
                    ObservableValue<String>>() {

						@Override
						public ObservableValue<String> call(CellDataFeatures<String, String> param) {
							// TODO Auto-generated method stub
							return null;
						}



            });
			this.ColHorario.setCellFactory(new ColumnButton<String, String>("asd"){
				@Override
				public void buttonAction(String element) {
					// TODO Auto-generated method stub

				}
			});
		}

	}
}

