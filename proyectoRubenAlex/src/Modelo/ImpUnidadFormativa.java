package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Profesor;
import pojos.Unidadformativa;

public class ImpUnidadFormativa implements UnidadFormativaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	//FUNCIONA NO ELIMINAR!!
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
	//FUNCIONA NO ELIMINAR!!
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
	
	//FUNCIONA NO ELIMINAR!!
	@Override
	public Unidadformativa verUnidadformativaByID(String idUnidadFormativa) {
		Session session = factory.openSession();
		Transaction tx = null;
		Unidadformativa unidad = (Unidadformativa)session.get(Unidadformativa.class, idUnidadFormativa);
		session.close();
		return unidad;
	}
	//FUNCIONA; NO TOCAR
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

}
