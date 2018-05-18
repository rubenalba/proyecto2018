package Vistas;

import java.io.IOException;
import java.sql.SQLException;

import Modelo.ProfesorInterface;
import application.Main;
import dao.DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pojos.LoginProfesor;
import pojos.Profesor;

public class VistaLoginController {
	static ProfesorInterface p = DAO.getProfesorInterface();

	@FXML
	private TextField IdUsuari;

	@FXML
	private Button btnLogin;

	@FXML
	private TextField IdPassword;

	public static String usuarioActivo;

	public String getUsuarioActivo() {
		return usuarioActivo;
	}

	public void setUsuarioActivo(String usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
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
	@FXML
	public void login(ActionEvent event) throws IOException, SQLException {

		String id = IdUsuari.getText();
		String contraseña = IdPassword.getText();



		System.out.println("Llega hasta aquí" + id + contraseña);

		LoginProfesor logg = new LoginProfesor ();
		boolean verificar = logg.login(id, contraseña);
		if (verificar) {

			closeWindow(event);
			usuarioActivo = id;
			System.out.println(usuarioActivo);
			Profesor profesorEntrada = new Profesor();
			profesorEntrada = p.verProfesorByUser(id);
			Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaInicial.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

		}else {

			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("Credenciales incorrectos");
			alert.setHeaderText("El password o el usuario son incorrectos");
			alert.showAndWait();

		}

	}
}






