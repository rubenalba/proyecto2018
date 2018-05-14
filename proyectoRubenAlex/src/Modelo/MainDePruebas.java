package Modelo;

import java.util.Iterator;
import java.util.List;

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
		//eliminarDatosDePruebaAulaYAlumno();
		//modificiarAlumnoYAula();
		verTodosAlumnos();
	}
	
	

	private static void verTodosAlumnos() {
		
		List listaAlumnos = alumno.verTodosAlumnos();
		System.out.println("Lista de alumnos:\n");
		for (Iterator itAlumno = listaAlumnos.iterator();itAlumno.hasNext();) {
			Alumnos alu = (Alumnos)itAlumno.next();
			System.out.println("Nombre: " + alu.getNombre()+ " " + alu.getApellidos() + ", DNI: " + alu.getDni());
			
		}
		/*List usuariosListados = user.verAllUsuers();
				System.out.println("Lista de usuarios :\n");
				for (Iterator itUser = usuariosListados.iterator();itUser.hasNext(); ) {
					Usuaris usuar = (Usuaris)itUser.next();
					System.out.println("ID usuario: " +usuar.getIdUsuari() +"Nombre:"+ usuar.getNom()+ " " + usuar.getCognoms() 
					+"\n" );
				}
				}*/
		
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
		int numAula = 2;
		try {
			aulas.eliminarAula(numAula);
			System.out.println("Eliminada");
		} catch (Exception e) {
			System.out.println("No se ha eliminado nada");
		}
	}
	
	private static void modificiarAlumnoYAula() {
		Aula  aula = new Aula(1);
		Alumnos al = new Alumnos("01234567A", aula, "Alex", "Rienda", "alexrien95@gmail.com");
		alumno.addAlumno(al);
	}
	
}



