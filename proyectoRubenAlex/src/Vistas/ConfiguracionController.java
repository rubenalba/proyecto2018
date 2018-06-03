package Vistas;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Encoder;
import Modelo.ProfesorInterface;
import dao.DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pojos.Profesor;

public class ConfiguracionController {
	static ProfesorInterface p = DAO.getProfesorInterface();

	@FXML
	private Button confirmarBTN;

	@FXML
	private Button pwdTempBTN;

	@FXML
	private TextField pwdTemporalTF;

	@FXML
	private Button BtnVolverConfig;

	@FXML
	private PasswordField contrasenyaTF;

	@FXML
	private PasswordField ConfirmarPWDTF;
	private Profesor profesorActivo;

	/**
	 * Accede a la informacion del profesor que ha logueado
	 * @return retorna el profesor logueado
	 */
	public Profesor profesorActivo() {
		VistaIniciController v = new VistaIniciController();
		profesorActivo = v.getProfesorActivo();
		return profesorActivo;
	}

	@FXML
	private void closeWindow(ActionEvent event) {
		cerrarVentana(event);
	}
	/**
	 * Cierra la ventana actual
	 * @param event parametro que recibe al haber accion en el botton que llama a este metodo
	 */
	private void cerrarVentana(ActionEvent event) {
		Node source = (Node)event.getSource();
		Stage stage= (Stage)source.getScene().getWindow();
		stage.close();
	}
	/**
	 * Genera una password nueva a partir de lo indicado en los textLine de la ventana configuracion
	 * @param event
	 */
	@FXML
	public void actualizarPassword (ActionEvent event) {
		profesorActivo();
		System.out.println("HA entrado en el metodo");
		if (contrasenyaTF.getText().equals(ConfirmarPWDTF.getText()) && contrasenyaTF.getLength() > 6) {

			Profesor mod = p.verProfesorByDni(profesorActivo.getDniProfesor());
			SecretKey skey = passWordKeyGeneration(profesorActivo.getDniProfesor());
			String pwd = encryptedData(skey,ConfirmarPWDTF.getText());
			mod.setPassword(pwd);
			try {
				p.modificarProfesor(mod);
				contrasenyaTF.setText("");
				ConfirmarPWDTF.setText("");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Contraseña cambiada");
				alert.showAndWait();
			} catch (Exception e) {
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Las contraseñas no coinciden o no cumple con el mínimo de 6 carácteres");
			alert.showAndWait();
		}
	}

	public SecretKey passWordKeyGeneration(String pwd) {
		SecretKey skey = null;
		int keysize = 128;
		try {
			byte[] data = pwd.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(data);
			byte [] key = Arrays.copyOf(hash, keysize/8);
			skey = new SecretKeySpec (key, "AES");
		}catch (Exception e) {

		}
		return skey;
	}

	public static String encryptedData(SecretKey skey, String pwd) {
		byte [] datos = null;
		String dats=null;
		try{
			byte [] pwdEncriptado = pwd.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			datos = cipher.doFinal(pwdEncriptado);
			BASE64Encoder b = new BASE64Encoder();
			dats = b.encode(datos);

		}catch (Exception ex) {

		}
		return dats;
	}
	/**
	 * Genera una password temporal al profesorActivo
	 */
	public void passwordTemporal(){
		profesorActivo();
		Profesor mod = p.verProfesorByDni(profesorActivo.getDniProfesor());
		SecretKey skey = passWordKeyGeneration(profesorActivo.getDniProfesor());
		String pwd = encryptedData(skey,pwdTemporalTF.getText());
		mod.setPasswordTemp(pwd);
		try {
			p.modificarProfesor(mod);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Password Temp Generada");
			alert.showAndWait();
		} catch (Exception e) {

	}
	}

}