package Modelo;
/**
 *
 * @author rubenalba
 * @version 1.0
 */

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asistencia;



public class ImpAlumnos implements AlumnosInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

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

	@Override
	public List<Asistencia> verAsistencia(String dni) {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Asistencia> listaAsistencia = null;
		try {
			tx = session.beginTransaction();
			listaAsistencia = session.createQuery("FROM Asistencia").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAsistencia;
	}

	@Override
	public Asistencia verAsistenciasByID(String dni) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asistencia asistencia = null;
		try {
			tx = session.beginTransaction();
			asistencia = (Asistencia)session.get(Asistencia.class, dni);
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return asistencia;
	}

	/*	public List verClientebyName(String clienteID) {
		Session session = factory.openSession();
		Transaction tx = null;
		Clients cliente  = null;
		String hql = "FROM Clients C WHERE C.nom LIKE " + "'"+ "%"+clienteID + "%" + "' OR C.cognoms LIKE " +"'"+ "%" + clienteID + "%"+ "'" ;
		Query query = session.createQuery(hql);
		List results = query.list();
		return results;
	}*/

	@Override
	//Esto deberiamos hacerlo por DNI ya que es el primary key, si lo hacemos por nombre nos devolvera todos los que se llamen igual
	//Para hacer consultas "especiales" es mejor crear una consulta que nos devuelva todos y a partir de la lista que nos de recoger
	//Los que necesitemos.
	public Alumnos verAlumnobyDNI(String DNI) {
		Session session = factory.openSession();
		Transaction tx = null;
		Alumnos alumno = (Alumnos)session.get(Alumnos.class, DNI);
		session.close();
		return alumno;
	}

	public Alumnos verAlumnobyName(String nombre) {
		Session session = factory.openSession();
		Transaction tx = null;
		Alumnos alumno = (Alumnos)session.get(Alumnos.class, nombre);
		session.close();
		return alumno;
	}
}
