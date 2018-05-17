package pojos;

import java.sql.SQLException;

import Modelo.ProfesorInterface;
import dao.DAO;



public class LoginProfesor {
	ProfesorInterface p = DAO.getProfesorInterface();
	
	
	public boolean login (String id, String pwd) throws SQLException{
		///Encriptar con Hash
		Profesor profeLogin = p.verProfesorByUser(id);
		if (profeLogin == null) return false;
		return profeLogin.getPassword().equals(pwd);

	}
	
}