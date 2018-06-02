package pojos;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Encoder;
import Modelo.ProfesorInterface;
import Vistas.VistaIniciController;
import dao.DAO;



public class LoginProfesor {
	ProfesorInterface p = DAO.getProfesorInterface();
	/**
	 * Comprueba que los datos introducidos para hacer login sean correctos
	 * @param id Usuario que logueara
	 * @param pwd Password del usuario
	 * @return retornara true o false dependiendo de si es correcto o no
	 * @throws SQLException Lanzara esta Excepcion en caso de que el login falle
	 */
	public boolean login (String id, String pwd) throws SQLException{
		Profesor profeLogin = p.verProfesorByUser(id);
		SecretKey skey = passWordKeyGeneration(profeLogin.getDniProfesor());
		pwd = encryptedData(skey, pwd);
		VistaIniciController a = new VistaIniciController();
		if (profeLogin == null) return false;
		if (profeLogin.getPassword().equals(pwd) || profeLogin.getPasswordTemp().equals(pwd)) {
			a.setPasswordUsada(pwd);
			return true;
		}

		else return false;
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

		}

		return dats;

	}

}