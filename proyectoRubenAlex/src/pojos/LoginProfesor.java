package pojos;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Encoder;
import Modelo.ProfesorInterface;
import dao.DAO;



public class LoginProfesor {
	ProfesorInterface p = DAO.getProfesorInterface();


	public boolean login (String id, String pwd) throws SQLException{
		Profesor profeLogin = p.verProfesorByUser(id);
		SecretKey skey = passWordKeyGeneration(profeLogin.getDniProfesor());
		pwd = encryptedData(skey, pwd);
		if (profeLogin == null) return false;
		System.out.println(profeLogin.getPassword());
		System.out.println(pwd);
		return profeLogin.getPassword().equals(pwd);

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