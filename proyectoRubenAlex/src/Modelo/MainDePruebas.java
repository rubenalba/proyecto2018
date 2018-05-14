package Modelo;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.DAO;
import dao.SessionFactoryUtil;

import pojos.Alumnos;
import pojos.Aula;
import pojos.Profesor;

public class MainDePruebas {
	SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static AlumnosInterface alumno = DAO.getAlumnosInterface();
	static AulaInterface aulas = DAO.getAulaInterface();
	static ProfesorInterface pro = DAO.getProfesorInterface();

	public static void main(String[] args) {
		//crearDatosdepruebaAulaYAlumno(); //PRUEBAS PARA AULA Y ALUMNO, OK + ó - FUNCIONAR FUNCIONA
		//eliminarDatosDePruebaAulaYAlumno();
		//modificiarAlumnoYAula();
		//verTodosAlumnos();
		//verTodasAulas();
		//verAlumnoByName();
		//verAlumnoDNI();
		//addProfe();
		//eliminarProfe();
		//verAllProfes();
		verProfeById();
		
	}
	
	

	private static void verProfeById() {
		String dni = "47665702H";
		Profesor profes = new Profesor();
		profes = pro.verProfesorByDni(dni);
		System.out.println("Profesor por id: " + profes.getNombre());
		
	}



	private static void verAllProfes() {
		List listaProfes = pro.verProfesores();
		System.out.println("lista de profesores:\n");
		for (Iterator itPro = listaProfes.iterator(); itPro.hasNext(); ) {
			Profesor p = (Profesor)itPro.next();
			System.out.println("Nombre: " + p.getNombre());
		}
		
	}



	private static void eliminarProfe() {
		String dni = "47665702H";
		try {
			pro.eliminarProfesor(dni);
			System.out.println("eliminado");
		} catch (Exception e) {
			System.out.println("no");
		}
		
	}



	private static void addProfe() {
		Profesor prof = new Profesor("47665702H", "Eloy Albiach", "eloyAl","a1");
		try {
			pro.addProfesor(prof);
			System.out.println("añadido");
		} catch (Exception e) {
			System.out.println("no añadido");
		}
		
		
	}



	private static void verAlumnoDNI() {
		String DNI = "47665701H";
		Alumnos dni = alumno.verAlumnobyDNI(DNI);
		System.out.println("Enconrado: " + dni.getNombre() + " " + dni.getApellidos());
		
	}

	private static void verAlumnoByName() {
		System.out.println("introduce el nombre:\n");
		String alumnoBuscar = leerString();
		List<Alumnos> alum = alumno.verAlumnobyName(alumnoBuscar);
		for (Iterator itAlumno = alum.iterator();itAlumno.hasNext();) {
			Alumnos a = (Alumnos)itAlumno.next();
			System.out.println("Alumnos Encontrados: " + a.getNombre() + " " + a.getApellidos());
		}
	
		
	}

	private static void verTodasAulas() {
		List listaAulas = aulas.verAllAulas();
		System.out.println("Lista de alumnos:\n");
		for (Iterator itAula = listaAulas.iterator();itAula.hasNext();) {
			Aula aula = (Aula) itAula.next();
			System.out.println("Aula nº: " + aula.getNumAula());
		}
		
	}

	private static void verTodosAlumnos() {
		
		List listaAlumnos = alumno.verTodosAlumnos();
		System.out.println("Lista de alumnos:\n");
		for (Iterator itAlumno = listaAlumnos.iterator();itAlumno.hasNext();) {
			Alumnos alu = (Alumnos)itAlumno.next();
			System.out.println("Nombre: " + alu.getNombre()+ " " + alu.getApellidos() + ", DNI: " + alu.getDni());	
		}
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
	public static String leerString() {
		Scanner nombreNuevo = new Scanner (System.in);
		String nombreNew = nombreNuevo.nextLine();
		return nombreNew;
	}
	private static int leerInteger() {
		Scanner search = new Scanner (System.in);
		String busqueda = search.nextLine();
		int buscar = Integer.parseInt(busqueda);
		return buscar;
	}
	private static Float leerFloat() {
		Scanner search = new Scanner (System.in);
		String busqueda = search.nextLine();
		Float buscar = Float.parseFloat(busqueda);
		return buscar;
	}	
}



