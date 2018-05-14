package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;

public class ImpAsignatura implements AsignaturaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	//FUNCIONA; NO TOCAR!!
	@Override
	public void addAsignatura(Asignatura assignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(assignatura);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}
	//FUNCIONA; NO TOCAR!!
	@Override
	public void eliminarAsignatura(int idAsignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Asignatura asignatura = (Asignatura)session.get(Asignatura.class, idAsignatura);
			session.delete(asignatura);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}


	@Override
	public void modificarAsignatura(Asignatura asignaturaModificada) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(asignaturaModificada);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}
	//FUNCIONA NO BORRAR!
	@Override
	public Asignatura verAsignaturaById(int idAsignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asignatura Asignatura = (Asignatura)session.get(Asignatura.class, idAsignatura);
		session.close();
		return Asignatura;
	}
	//FUNCIONA NO BORRAR!
	@Override
	public List<Asignatura> verAllAsignaturas() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Asignatura> listaAsignatura = null;
		try {
			tx = session.beginTransaction();
			listaAsignatura = session.createQuery("FROM Asignatura").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAsignatura;
	}

}
