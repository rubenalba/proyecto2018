package Modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

import dao.DAO;
import dao.SessionFactoryUtil;

import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Franjas;
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
	static FranjaInterface f = DAO.getFranjaInterface();

	public static void main(String[] args) throws ParseException {
		//crearDatosdepruebaAulaYAlumno(); // OK
		//eliminarDatosDePruebaAulaYAlumno(); OK
		//modificiarAlumnoYAula(); OK
		//verTodosAlumnos(); //OK
		//verTodasAulas(); OK
		//verAlumnoByName(); OK
		//verAlumnoDNI(); OK
		//addProfe(); 
		//eliminarProfe(); OK
		//verAllProfes(); OK
		//verProfeById(); OK
		//addAsigna();
		//eliminarAsig(); OK
		//verAsigna(); OK
		//verAllAsignaturas(); OK
		//addUF(); OK
		//verUF(); OK
		//eliminarUF(); OK
		//verAllUF(); OK
		//matricular(); //OK
		//verMatriculas(); OK
		//eliminarMatricula(); //OK
		//addNota(); SIN HACER!!!!
		//addFranja(); OK
		//verFranjaById(); OK
		//eliminarFranja(); OK
		//verAllFranjas(); //OK
		//verProfesorByUser(); OK
		//consultas();
		misAlumnos();
	}


	private static void misAlumnos() {
	Profesor p = pro.verProfesorByDni("SUSTITUTO");
	List<Asignatura> asignaturas = pro.misAsignaturas(p);
	List<Unidadformativa> u = new ArrayList<>();
	System.out.println("Asignaturas de " + p.getNombre() + ":");
	for (Asignatura integer : asignaturas) {
		System.out.println(integer.getNombreAsignatura());
		u = pro.misUFs(p, integer);
		for (Unidadformativa unidadformativa : u) {
			System.out.println("\t" + unidadformativa.getNombreUf());
			
		}
		System.out.println("\n");
	}
		
		
	}


	private static void consultas() {
		Profesor p = pro.verProfesorByDni("0CRISTINA");
		List<String> asi = pro.asignaturasImpartidas("0CRISTINA");
		System.out.println("Las asignaturas que imparte "+ p.getNombre()+ " son: ");
		for (String string : asi) {
			System.out.println(string);
		}
	
		
	}


	private static void verProfesorByUser() {
	Profesor p = new Profesor();
	p = pro.verProfesorByUser("eloyAl");
	System.out.println(p.getDniProfesor() + " " + p.getNombre());

	}



	private static void verAllFranjas() {
		List listaFtanja = f.verAlFranjas();
		System.out.println("Lista de Franjas:\n");
		for (Iterator itF = listaFtanja.iterator(); itF.hasNext();) {
			Franjas f = (Franjas)itF.next();
			System.out.println("Nombre: " + f.getIdFranja() + ", Hora Inicio: " + f.getHoraInicio() + ", Hora Fin: " + f.getHoraFin());

		}


	}


	private static void eliminarFranja() {
		String id = "F1";
		f.eliminarFranja(id);

	}


	private static void verFranjaById() {
		String id = "F1";
		Franjas fra = new Franjas();
		fra = f.verFranjaByID(id);
		System.out.println("Franja: " + fra.getIdFranja() + ", Hora inicio: " + fra.getHoraInicio() + ", Hora fin: " + fra.getHoraFin());

	}


	private static void addFranja() throws ParseException {
		String idFranja ="F6";
		Profesor p = new Profesor();
		p = pro.verProfesorByDni("47665702H");
		String inicio = "20:20";
		String hfinal = "21:15";
		Asignatura a = new Asignatura();
		as.verAsignaturaById(1);
		DateFormat dateF = new SimpleDateFormat("HH:mm");
		Date horaInicio = dateF.parse(inicio);
		Date horaFin = dateF.parse(hfinal);
		String dia = "Lunes";
		Franjas franja = new Franjas(idFranja,a, p, horaInicio, horaFin, dia);
		try {
			f.addFranja(franja);
			System.out.println("si");
		} catch (Exception e) {
			System.out.println("no");
		}


	}


	private static void addNota() {
		String id = "47665701H";
		int ufs = 1;
		Double nota = 7.3;
		MatriculaId mat = new MatriculaId(id,ufs);
		try {
			mt.addNota(mat,  nota);
			System.out.println("si");
		} catch (Exception e) {
			System.out.println("no");
		}


	}


	private static void eliminarMatricula() {
		String id = "47665701H";
		int uf = 1;
		MatriculaId mat = new MatriculaId(id,uf);
		Matricula m = new Matricula();
		m = mt.verMatricula(mat);
		try {
			mt.eliminarMatricula(mat);
			System.out.println("si");
		} catch (Exception e) {
			System.out.println("No");
		}

	}


	private static void verMatriculas() {
		String id = "47665701H";
		int uf = 1;
		MatriculaId mat = new MatriculaId(id,uf);
		Matricula m = new Matricula();
		m = mt.verMatricula(mat);
		System.out.println("DNI: " + m.getAlumnos().getDni() +", Nombre: "+m.getAlumnos().getNombre() + " " + m.getAlumnos().getApellidos() +  ", Asignatura: "+ m.getUnidadformativa().getAsignatura().getNombreAsignatura());
		//Hibernate.initialize(m.getAlumnos());
		//Hibernate.initialize(m.getUnidadformativa());
		//Hibernate.initialize(m.getAlumnos().getMatriculas());
		//Hibernate.initialize(m.getUnidadformativa().getMatriculas());

	}



	private static void matricular() {
		MatriculaId m = new MatriculaId("47665701H", 11);
		Alumnos a = new Alumnos();
		a = alumno.verAlumnobyDNI("47665701H");
		Unidadformativa u = new Unidadformativa();
		u = uf.verUnidadformativaByID(3);

		Matricula matricula = new Matricula(m,a,u);
		try {
			mt.matricularAlumno(matricula);
			System.out.println("si");
		} catch (Exception e) {
			System.out.println("no");
		}

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
		
		Unidadformativa u = new Unidadformativa();
		u = uf.verUnidadformativaByID(5);
		System.out.println("Nombre UF: " + u.getIdUnidadFormativa() + ", Duración:" + u.getHoras() + ", Asignatura:" +u.getAsignatura().getNombreAsignatura()+ ", Profesor: " + u.getProfesor().getNombre() );

	}



	private static void addUF() {
		Asignatura a = as.verAsignaturaById(1);
		Profesor p = pro.verProfesorByDni("47665702H");
		Unidadformativa ufo = new Unidadformativa(1,a,p,"UF2",33);
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
		Asignatura asignatura = new Asignatura(3,"M3");
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
		Profesor prof = new Profesor("0013547H", "Prueba", "hola","1111");
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



