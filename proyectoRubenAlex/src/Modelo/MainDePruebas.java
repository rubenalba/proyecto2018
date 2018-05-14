package Modelo;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.DAO;
import dao.SessionFactoryUtil;

import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Matricula;
import pojos.MatriculaId;
import pojos.Profesor;
import pojos.Unidadformativa;

public class MainDePruebas {
	SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static AlumnosInterface alumno = DAO.getAlumnosInterface();
	static AulaInterface aulas = DAO.getAulaInterface();
	static ProfesorInterface pro = DAO.getProfesorInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static UnidadFormativaInterface uf = DAO.getUnidadFormativaInterface();
	static MatriculaInterface mt = DAO.getMatriculaInterface();

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
		//verProfeById();
		//addAsigna();
		//eliminarAsig();
		//verAsigna();
		//verAllAsignaturas();
		//addUF();
		//verUF();
		//eliminarUF();
		//verAllUF();
		matricular();
		
		
	}
	
	

	private static void matricular() {
		MatriculaId m = new MatriculaId("47665701H", "M1");
		Alumnos a = new Alumnos();
		a = alumno.verAlumnobyDNI("47665701H");
		Unidadformativa u = new Unidadformativa();
		u = uf.verUnidadformativaByID("M1");
		
		MainDePruebas.mt.matricularAlumno(m, a, u);
		
	}



	private static void verAllUF() {
		List listaUF = uf.verAllUnidadFormativas();
		System.out.println("lista de UF:\n");
		for (Iterator itPro = listaUF.iterator(); itPro.hasNext(); ) {
			Unidadformativa p = (Unidadformativa)itPro.next();
			System.out.println("Nombre: " + p.getIdUnidadFormativa());
		}
		
	}



	private static void eliminarUF() {
	uf.eliminarUnidadFormativa("M1");
		
	}



	private static void verUF() {
		String nom = "M1";
		Unidadformativa u = new Unidadformativa();
		u = uf.verUnidadformativaByID(nom);
		System.out.println("Nombre UF: " + u.getIdUnidadFormativa() + ", Duración:" + u.getHoras() + ", Asignatura:" +u.getAsignatura().getNombreAsignatura()+ ", Profesor: " + u.getProfesor().getNombre() );
		
	}



	private static void addUF() {
		Asignatura a = as.verAsignaturaById(1);
		Profesor p = pro.verProfesorByDni("47665702H");
	Unidadformativa ufo = new Unidadformativa("M3",a,p,33);
	uf.addUnidadFormativa(ufo);
		
	}



	private static void verAllAsignaturas() {
		List listaAsig = as.verAllAsignaturas();
		System.out.println("lista de profesores:\n");
		for (Iterator itPro = listaAsig.iterator(); itPro.hasNext(); ) {
			Asignatura p = (Asignatura)itPro.next();
			System.out.println("Nombre: " + p.getNombreAsignatura());
		}
		
	}



	private static void verAsigna() {
		int id = 1;
		Asignatura a = new Asignatura();
		a = as.verAsignaturaById(id);
		System.out.println("Nombre: " + a.getNombreAsignatura());
		
	}



	private static void eliminarAsig() {
		int asi = 1;
		as.eliminarAsignatura(asi);
		
	}



	private static void addAsigna() {
		Asignatura asignatura = new Asignatura(2,"Acceso a Datos");
		as.addAsignatura(asignatura);
		
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



