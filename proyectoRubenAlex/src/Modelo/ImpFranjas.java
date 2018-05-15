package Modelo;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Aula;
import pojos.Franjas;
import pojos.Profesor;

public class ImpFranjas implements FranjaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	
	//FUNCIONA NO BORRAR!!
	@Override
	public void addFranja(Franjas franja) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(franja);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	//FUNCIONA NO BORRAR!
	@Override
	public void eliminarFranja(String idFranja) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Franjas franja = (Franjas)session.get(Franjas.class, idFranja);
			session.delete(franja);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@Override
	public void modificarFranja(Franjas franjaModificada) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(franjaModificada);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	//FUNCIONA NO TOCAR!!
	@Override
	public List<Franjas> verAlFranjas() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Franjas> listaFranjas = null;
		try {
			tx = session.beginTransaction();
			listaFranjas = session.createQuery("FROM Franjas").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaFranjas;
	}
	//FUNCIONA NO BORRAR
	@Override
	public Franjas verFranjaByID(String idFranja) {
		Session session = factory.openSession();
		Transaction tx = null;
		Franjas franja = (Franjas)session.get(Franjas.class, idFranja);
		session.close();
		return franja;
	}

}
