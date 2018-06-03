package Modelo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.DAO;
import dao.SessionFactoryUtil;
import pojos.Asignatura;
import pojos.Horas;

public class ImpHoras implements HorasInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	/**
	 * Retorna un objeto hora al que pertenezca la hora introducida
	 * @return objeto hora
	 * @param hora introducida
	 */
	@Override
	public Horas getHorasByRango(String hora) {
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT * FROM Horas h WHERE '"+hora+"' >= h.Hora_Inicio and '"+hora+"'< h.Hora_Fin";
		Horas r = null;
		List<Horas> h = new ArrayList<Horas>();
		try {
			tx = session.beginTransaction();
			h = session.createNativeQuery(sql, Horas.class).list();
			tx.commit();
			for (Horas horas : h) {
				r = horas;
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return r;
	}
	/**
	 * Lista todas las horas de la bbdd
	 * @return retorna una lista
	 */
	@Override
	public List<Horas> getAllHoras() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Horas> listaHoras = null;
			try {
				tx = session.beginTransaction();
				listaHoras = session.createQuery("FROM Horas").list();
				tx.commit();
			}catch  (HibernateException e) {
				if (tx!=null) tx.rollback();
				e.printStackTrace();
			}finally {
				session.close();
			}
			return listaHoras;
		}

}
