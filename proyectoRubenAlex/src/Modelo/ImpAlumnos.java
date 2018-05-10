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
	public String addAlumno(Alumnos alumno) {
		Session session = factory.openSession();
		Transaction tx = null;
		String alumnoID = null;
		try {
			tx = session.beginTransaction();
			session.save(alumnoID);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return alumnoID;
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
	public List verAlumnobyName(String nombre) {
		Session session = factory.openSession();
		Transaction tx = null;
		Alumnos alumno = null;
		String query = "FROM Alumnos a WHERE a.nombre LIKE "+"'"+"%"+nombre+"%"+"' OR a.apellidos LIKE " + "'"+"%"+ nombre+"%"+"'";
		Query consulta = session.createQuery(query);
		List resultados = consulta.list();
		return resultados;
	}

	
}
