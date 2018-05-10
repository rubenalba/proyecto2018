package Modelo;

import org.hibernate.SessionFactory;

import dao.DAO;
import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Aula;

public class MainDePruebas {
	SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static AlumnosInterface alumno = DAO.getAlumnosInterface();
	static AulaInterface aulas = DAO.getAulaInterface();
	
	public static void main(String[] args) {
		Aula aula = new Aula (1);
		aulas.addAula(1);
		/*Alumnos a1 = new Alumnos ("47665701H", aula, "Ruben", "Alba Revilla", "rubenalbarevilla@gmail.com");
		alumno.addAlumno(a1);*/
	}

}
