package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Aula;

/**
 *
 * @author rubenalba
 * @version 1.0
 */
public class ImpAula implements AulaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

	/**
	 * Añade un aula a la bbdd
	 * @param aula, objeto a añadir a la bbdd
	 */
	@Override
	public void addAula(Aula aula) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(aula);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	/**
	 * elimina un aula de la bbdd
	 * @param numAula numero del aula a eliminar
	 */
	@Override
	public void eliminarAula(int numAula) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Aula aula = (Aula)session.get(Aula.class, numAula);
			session.delete(aula);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	/**
	 * Modifica un aula de la bbdd
	 * @param aula, objeto a modificar
	 */
	@Override
	public void modificarAula(Aula aula) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(aula);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}
	/**
	 * muestra un aula a partir de su numero
	 * @return retorna un aula
	 * @param numAula, numero a partir del que buscara el aula
	 */
	@Override
	public Aula verAulaByID(int numAula) {
		Session session = factory.openSession();
		Transaction tx = null;
		Aula aula = (Aula)session.get(Aula.class, numAula);
		session.close();
		return aula;
	}
/**
 * Retorna todas las aulas
 * @param retorna una lista
 */
	@Override
	public List<Aula> verAllAulas() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Aula> listaAula = null;
		try {
			tx = session.beginTransaction();
			listaAula = session.createQuery("FROM Aula").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAula;
	}

}
