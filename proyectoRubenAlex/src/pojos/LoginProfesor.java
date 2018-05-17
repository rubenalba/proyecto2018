package pojos;

import java.sql.SQLException;

import Modelo.ProfesorInterface;
import dao.DAO;



public class LoginProfesor {
	ProfesorInterface p = DAO.getProfesorInterface();
	
	
	public boolean login (String id, String pwd) throws SQLException{
		Profesor profeLogin = new Profesor();
		Profesor profeTemporal = new Profesor();
		profeLogin = p.verProfesorByUser(id);
		if (profeLogin == null)return false;
		else {
		profeTemporal.setPassword(pwd);
		
		if (profeLogin.getPassword().equals(profeTemporal.getPassword())) {
			return true;
		}
		return false;
	}
	}
	
}