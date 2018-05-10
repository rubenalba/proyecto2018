package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Aula;

/**
 * 
 * @author rubenalba
 * @version 1.0
 */
public class ImpAula implements AulaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

	@Override
	public Integer addAula(int numAula) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer aulaID = null;
		try {
			tx = session.beginTransaction();
			session.save(numAula);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return aulaID;
	}

	@Override
	public void eliminarAula(int numAula) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarAula(int numAula) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Aula verAulaByID(int numAula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> verAllAulas(int numAula) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
