package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
	@Override
	public Ciclo verCicloByName(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		Ciclo c = null;
		String sql = "FROM Ciclo C WHERE C.nombreCiclo = " + "'" + name + "'";
		Query query = session.createQuery(sql);
		c = (Ciclo)query.uniqueResult();
		return c;
		/*@Override
		public Serveis buscarServicioByNombre(String nombre) {
			Session session = factory.openSession();
			Transaction tx = null;
			Serveis servei = null;
			String sql = "FROM Serveis S WHERE S.descripcio = " + "'" + nombre + "'";
			Query query = session.createQuery(sql);
			servei = (Serveis) query.uniqueResult();
			System.out.println(servei.getDescripcio() + "hola jesssi");
			return servei;
			
		}*/
	}


}

