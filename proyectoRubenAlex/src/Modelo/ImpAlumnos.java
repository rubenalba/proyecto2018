package Modelo;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asistencia;
import pojos.Matricula;
import pojos.Unidadformativa;


/**
*
* @author rubenalba
* @version 1.0
*/
public class ImpAlumnos implements AlumnosInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	//Funciona, no tocar!!
	/**
	 *  Añade alumnos a la base de datos pasándole el alumno
	 *   @pararm Alumno alumno
	 */
	@Override
	public void addAlumno(Alumnos alumno) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(alumno);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	//Funciona, no tocar!!
	/**
	 *  Elimina alumnos a la base de datos pasándole el dni
	 *   @pararm String dni
	 */
	@Override
	public void eliminarAlumno(String dni) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Alumnos alumno = (Alumnos)session.get(Alumnos.class, dni);
			session.delete(alumno);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	/**
	 *  modifica alumnos a la base de datos pasándole el alumno
	 *   @pararm Alumno alumno
	 */
	@Override
	public void modificarAlumno(Alumnos alumnoModificado) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(alumnoModificado);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	/**
	 *  Obtiene la lista de asistencias del alumno que se le pasa por medio del DNI
	 *   @pararm String dni
	 */
	@Override
	public List<Asistencia> verAsistencia(String dni) {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Asistencia> listaAsistencia = null;
		try {
			tx = session.beginTransaction();
			listaAsistencia = session.createQuery("FROM Asistencia").list(); //AÑADIR UF
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAsistencia;
	}

	//Esto deberia estar aqui?
	/**
	 * Obtiene una falta de asistencia en concreto a partir de la id
	 * @param String dni
	 */
	@Override
	public Asistencia verAsistenciasByID(String id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asistencia asistencia = null;
		try {
			tx = session.beginTransaction();
			asistencia = (Asistencia)session.get(Asistencia.class, id);
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return asistencia;
	}

	/**
	 * Obtiene un alumno a partir de su DNI
	 * @param String DNI
	 */
	@Override
	//FUNCIONA, NO TOCAR!!
	public Alumnos verAlumnobyDNI(String DNI) {
		Session session = factory.openSession();
		Transaction tx = null;
		Alumnos alumno = (Alumnos)session.get(Alumnos.class, DNI);
		session.close();
		return alumno;
	}

	//FUNCIONA, NO TOCAR!!
	/**
	 * Lista todos los alumnos.
	 */
	@Override
	public List<Alumnos> verTodosAlumnos() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Alumnos> listaAlumnos = null;
		try {
			tx = session.beginTransaction();
			listaAlumnos = session.createQuery("FROM Alumnos").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAlumnos;
	}
	//FUNCIONA, NO TOCAR!!
	//No deberiamos pasar el apellido tambien?
	/**
	 * Lista los alumnos a partir de su nombre.
	 */
	@Override
	public List<Alumnos> verAlumnobyName(String nombre) {
		Session session = factory.openSession();
		Transaction tx = null;
		Alumnos alum = null;
		String hql = "FROM Alumnos a WHERE a.nombre LIKE " + "'" + "%"+nombre + "%" + "' OR a.apellidos LIKE " + "'" + "%" + nombre + "%" + "'";
		Query query = session.createQuery(hql);
		List resultado = query.list();
		try {
			tx = session.beginTransaction();
			alum = (Alumnos)session.get(Alumnos.class, nombre);
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return resultado;
	}
}
