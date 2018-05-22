package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Ciclo;

public class ImpCiclos implements CicloInterface {
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	@Override
	public List<Ciclo> verAllCiclos() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Ciclo> listaciclos = null;
		try {
			tx = session.beginTransaction();
			listaciclos = session.createQuery("FROM Ciclo").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaciclos;
	}

}
