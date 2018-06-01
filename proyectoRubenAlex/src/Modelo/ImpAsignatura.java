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
/**
 *
 * @author cfgs
 * @version 1.0
 */
public class ImpAsignatura implements AsignaturaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	/**
	 * Metodo para añadir una asignatura a la base de datos.
	 * @param Asignatura assignatura, Asignatura que se añadira
	 */
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
	/**
	 * Metodo para eliminar Asignaturas a partir de su id
	 * @param int idAsignatura id que uilizara como condicion
	 */
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

	/**
	 * metodo para modificar una asignatura
	 * @param Asignatura asignaturaModificada, Asignatura sobre la que se hara el update
	 */
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
	@Override
	public Asignatura verAsignaturaById(int idAsignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asignatura Asignatura = (Asignatura)session.get(Asignatura.class, idAsignatura);
		session.close();
		return Asignatura;
	}
	/**
	 * Metodo que retorna todas las asignaturas
	 */
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
	/**
	 * Metodo que retornara todas las asignaturas que pertenecen a un ciclo concreto
	 * @param String curso Nombre de curso que usaremos como condicion
	 */
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
	/**
	 * Metodo que retorna la asignatura con un nombre y un id de cliclo concreto
	 * @param String asignatura es la asignatura que usaremos como condicion, int ciclo es la id de cliclo que usaremos como condicion
	 * @return asignaturas
	 */
	@Override
	public Asignatura verAsignaturaByName(String asignatura, int ciclo ) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asignatura asignaturas = null;
		Integer num = null;
		String sql = "select a.ID_Asignatura "
				+ " from asignatura a "
				+ " where a.ID_Ciclo = " + "'"+ciclo+"'"
				+ " and a.Nombre_Asignatura = " + "'"+asignatura+"'";
		num = (Integer) session.createNativeQuery(sql).uniqueResult();

		asignaturas = as.verAsignaturaById(num);
		return asignaturas;
	}


}
