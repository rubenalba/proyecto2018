package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Ciclo;
import pojos.Franjas;

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
	@Override
	public Ciclo verCicloByName(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		Ciclo c = null;
		String sql = "FROM Ciclo C WHERE C.nombreCiclo = " + "'" + name + "'";
		Query query = session.createQuery(sql);
		c = (Ciclo)query.uniqueResult();
		return c;
	}
	@Override
	public Ciclo verCicloByID(int idciclo) {
			Session session = factory.openSession();
			Transaction tx = null;
			Ciclo ciclo = (Ciclo)session.get(Ciclo.class, idciclo);
			session.close();
			return ciclo;
	}
}

