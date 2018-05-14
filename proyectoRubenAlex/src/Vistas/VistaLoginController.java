package Vistas;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VistaLoginController {
    @FXML
    private TextField IdUsuari;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField IdPassword;

    @FXML
    public void login() {

	    Stage Actual = (Stage) btnLogin.getScene().getWindow();
		Actual.close();
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("../Vistas/VistaInicial.fxml"));
			Pane ventanaDos = (Pane) loader.load();
			Stage ventana = new Stage();

			Scene sceneDos = new Scene(ventanaDos);
			sceneDos.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        ventana.setScene(sceneDos);
	        ventana.show();

	    } catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error desconocido");
			alert.setContentText("Error desconocido impide el correcto funcionamiento, cerrando");
			alert.showAndWait();
			Stage Actual2 = (Stage) btnLogin.getScene().getWindow();
			Actual2.close();
			e.printStackTrace();
	    }
    }
}



