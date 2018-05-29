package Modelo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.DAO;
import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Ciclo;

public class ImpAsignatura implements AsignaturaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
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

	public List<Asignatura> verAsignaturaByCurso(String curso){
		Session session = factory.openSession();
		Transaction tx = null;
		List <Integer> listaAsignatura = null;
		List<Asignatura> asign = new ArrayList<Asignatura>();
		String sql = "select a.ID_Asignatura "
				+ " from asignatura a, ciclo c "
				+ " where a.ID_Ciclo = c.ID_Ciclo "
				+ " and c.Nombre_Ciclo like "+"'"+curso+"'";
		try {
			tx = session.beginTransaction();
			listaAsignatura = session.createNativeQuery(sql).list();
			for (Integer asignatura : listaAsignatura) {
				Asignatura a = as.verAsignaturaById(asignatura);
				asign.add(a);
			}
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return asign;
	}
	@Override
	public Asignatura verAsignaturaByName(String ciclo, String asignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asignatura asignaturas = null;
		Integer num = null;
		String sql = "select a.ID_Asignatura "
				+ " from asignatura a, ciclo c "
				+ " where c.Nombre_Ciclo = " + "'"+asignatura+"'"
				+ " and a.Nombre_Asignatura = " + "'"+ciclo+"'";
		num = (Integer) session.createNativeQuery(sql).uniqueResult();

		asignaturas = as.verAsignaturaById(num);
		return asignaturas;
	}


}
