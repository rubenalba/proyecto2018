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
		//crearDatosdepruebaAulaYAlumno(); //PRUEBAS PARA AULA Y ALUMNO, OK + ó - FUNCIONAR FUNCIONA
		eliminarDatosDePruebaAulaYAlumno();
	}
	
	public static void crearDatosdepruebaAulaYAlumno() {
		//EL AULA SE CREA CORRECTAMENTE Y EL ALUMNO EN LA BD, PERO SALE MENSAJE DE EN EL TERMINAL
		Aula aula = new Aula (2);
		try {
			aulas.addAula(aula);
			System.out.println("Se ha creado el aula");
		} catch (Exception e) {
			System.out.println("No se ha creado nada");
		}
		try {
			Alumnos a1 = new Alumnos ("47665701H", aula, "Ruben", "Alba Revilla", "rubenalbarevilla@gmail.com");
			alumno.addAlumno(a1);
			System.out.println("alumno creado");
		} catch (Exception e) {
			System.out.println("No se ha creado nada");
		}
	}
	
	
	private static void eliminarDatosDePruebaAulaYAlumno() {
		//EL ALUMNO SE ELIMINA PERO TAMBIÉN HAY MENSAJES DE ERROR EN EL TERMINAL, PERO LO ELIMINA DE BD
	/*	String dni = "47665701H";
		try {
			alumno.eliminarAlumno(dni);
			System.out.println("Alumno eliminado");
		} catch (Exception e) {
			System.out.println("No se ha eliminado nada");
		}*/
	}
}



