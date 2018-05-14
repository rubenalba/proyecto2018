package Modelo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.MatriculaId;
import pojos.Unidadformativa;

public class ImpMatricula  implements MatriculaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	
	@Override
	public void matricularAlumno(MatriculaId id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(id);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		
	}

	@Override
	public void addNota(MatriculaId id, Alumnos alumnos, Unidadformativa unidadformativa, Double nota) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarNota(MatriculaId id, Double nota) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean validarNota(Double nota) {
		// TODO Auto-generated method stub
		return null;
	}

}
