package Vistas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import Modelo.ProfesorInterface;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.LoginProfesor;
import pojos.Profesor;
/**
 *
 * @author cfgs
 *
 */
public class VistaLoginController {
	static ProfesorInterface p = DAO.getProfesorInterface();

	@FXML
	private TextField IdUsuari;

	@FXML
	private Button btnLogin;

	@FXML
	private TextField IdPassword;

	public static String usuarioActivo;


	@FXML
	private Button espBTN;

	@FXML
	private Button catBTN;

	@FXML
	private Label usu;

	@FXML
	private Label cont;
	
	private ResourceBundle bundle;
	private Locale locale;
	private static  String langActivo;
	
	

	public static String getLangActivo() {
		return langActivo;
	}
	public static void setLangActivo(String langActivo) {
		VistaLoginController.langActivo = langActivo;
	}
	public void loadLang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("resources.lang", locale);
		usu.setText(bundle.getString("usu"));
		cont.setText(bundle.getString("cont"));
		
	}
	@FXML 
	private void spanish(ActionEvent event) {
		loadLang("es");
		langActivo = "es";
	}
	
	@FXML 
	private void catalanish(ActionEvent event) {
		loadLang("cat");
		langActivo = "cat";
	}

	public String getUsuarioActivo() {
		return usuarioActivo;
	}

	public void setUsuarioActivo(String usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
	}

	/**
	 * Metodo que recibe un event y lo envia al metodo cerrar ventana.
	 * @param event
	 */
	@FXML
	private void closeWindow(ActionEvent event) {
		cerrarVentana(event);
	}
	/**
	 * Metodo para cerrar ventana del stage actual.
	 * @param event
	 */
	private void cerrarVentana(ActionEvent event) {
		Node source = (Node)event.getSource();
		Stage stage= (Stage)source.getScene().getWindow();
		stage.close();
	}
	/**
	 *
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	public void login(ActionEvent event) throws IOException, SQLException {

		String id = IdUsuari.getText();
		String contraseña = IdPassword.getText();

		LoginProfesor logg = new LoginProfesor ();
		boolean verificar = false;
		try{
			verificar = logg.login(id, contraseña);
		} catch (Exception e){
			verificar = false;
		}
		if (verificar) {

			closeWindow(event);
			usuarioActivo = id;
			Profesor profesorEntrada = new Profesor();
			profesorEntrada = p.verProfesorByUser(id);
			VistaIniciController a = new VistaIniciController();

			a.setProfesorActivo(usuarioActivo);
			Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaInicial.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

		}else {

			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("Credenciales  incorrectos");
			alert.setHeaderText("El password o el usuario son incorrectos");
			alert.showAndWait();

		}

	}
}






