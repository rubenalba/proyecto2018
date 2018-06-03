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
import pojos.Profesor;
import pojos.Unidadformativa;

public class ImpUnidadFormativa implements UnidadFormativaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static UnidadFormativaInterface uf = DAO.getUnidadFormativaInterface();

	/**
	 * Añade un uf a la bbdd
	 * @param uf, objeto a añadir a la bbdd
	 */
	@Override
	public void addUnidadFormativa(Unidadformativa unidad) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(unidad);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}
	/**
	 * elimina un uf de la bbdd
	 * @param numAula numero del uf a eliminar
	 */
	@Override
	public void eliminarUnidadFormativa(String idUnidadFormativa) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Unidadformativa uf = (Unidadformativa)session.get(Unidadformativa.class, idUnidadFormativa);
			session.delete(uf);
			tx.commit();

		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}
	/**
	 * Modifica un uf de la bbdd
	 * @param unidadFormativaModificada, objeto a modificar
	 */
	@Override
	public void modificarUnidadFormativa(Unidadformativa unidadFormativaModificada) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(unidadFormativaModificada);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	/**
	 * retorna una unidad formativa a partir de su id
	 * @return objeto unidad formativa
	 */
	@Override
	public Unidadformativa verUnidadformativaByID(int idUnidadFormativa) {
		Session session = factory.openSession();
		Transaction tx = null;
		Unidadformativa unidad = (Unidadformativa)session.get(Unidadformativa.class, idUnidadFormativa);
		session.close();
		return unidad;
	}
	/**
	 * retorna todas las ufs
	 * @return retorna una lista
	 */
	@Override
	public List<Unidadformativa> verAllUnidadFormativas() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Unidadformativa> listaUnidad = null;
		try {
			tx = session.beginTransaction();
			listaUnidad = session.createQuery("FROM Unidadformativa").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaUnidad;
	}
	/**
	 * Retorna todas las ufs de una asignatura
	 * @param nombre de la asignatura
	 * @return retorna una lista
	 */
	@Override
	public List<Unidadformativa> ufByCiclo(String asig) {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Integer> listaUnidad = null;
		List<Unidadformativa> u = new ArrayList<Unidadformativa>();
		String sql ="SELECT u.ID_UnidadFormativa "
				+ " from unidadformativa u, asignatura a "
				+ " where a.ID_Asignatura = u.ID_Asignatura "
				+ " and a.Nombre_Asignatura LIKE " + "'" + asig + "'";
		try {
			tx = session.beginTransaction();
			listaUnidad = session.createNativeQuery(sql).list();
			for (Integer unidadformativa : listaUnidad) {
				Unidadformativa ufor = uf.verUnidadformativaByID(unidadformativa);
				u.add(ufor);
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return u;
	}
	/**
	 * Retorna una uf a partir de su ciclom asignatura y nombre
	 * @param id del ciclo
	 * @param id de la asignatura
	 * @param nombre de la uf
	 * @return retorna un objeto unidad formativa
	 */
	@Override
	public Unidadformativa verUFByName(int ciclo, int asignatura, String uf) {
		Session session = factory.openSession();
		Transaction tx = null;
		Unidadformativa u = null;
		Integer num = null;
		String sql = "select u.ID_UnidadFormativa "
				+ " from unidadformativa u, ciclo c, asignatura a "
				+ " where c.ID_Ciclo = " + "'" + ciclo + "'"
				+ " and a.ID_Asignatura = " + "'" + asignatura + "'"
				+ " and u.NombreUF = " + "'" + uf + "'";
		num = (Integer)session.createNativeQuery(sql).uniqueResult();
		u = ImpUnidadFormativa.uf.verUnidadformativaByID(num);

		return u;
	}
	/**
	 * Quita una uf de un profesor
	 * @param id de la unidad formativa a eliminar
	 */
	@Override
	public void quitarUFprofesir(int idUnidadFormativa) {
		Session session = factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		String sql = "UPDATE Unidadformativa SET DNI_Profesor = NULL WHERE ID_UnidadFormativa = "+idUnidadFormativa;

		Query query = session.createNativeQuery(sql);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

}
