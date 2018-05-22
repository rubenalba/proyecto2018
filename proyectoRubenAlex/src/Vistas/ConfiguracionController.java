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
	private PasswordField contraseñaTF;

	@FXML
	private PasswordField ConfirmarPWDTF;
	private Profesor profesorActivo;


	public Profesor profesorActivo() {
		VistaIniciController v = new VistaIniciController();
		profesorActivo = v.getProfesorActivo();
		return profesorActivo;
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
	public void actualizarPassword (ActionEvent event) {
		profesorActivo();
		System.out.println("HA entrado en el metodo");
		if (contraseñaTF.getText().equals(ConfirmarPWDTF.getText()) && contraseñaTF.getLength() > 6) {
			System.out.println("YA las ha comparado");
			Profesor mod = p.verProfesorByDni(profesorActivo.getDniProfesor());
			SecretKey skey = passWordKeyGeneration(profesorActivo.getDniProfesor());
			String pwd = encryptedData(skey,ConfirmarPWDTF.getText());
			mod.setPassword(pwd);
			try {
				p.modificarProfesor(mod);
				contraseñaTF.setText("");
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
			System.out.println("Error generando la clave");
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
			System.out.println("Error cifrando");
		}

		return dats;

	}

}