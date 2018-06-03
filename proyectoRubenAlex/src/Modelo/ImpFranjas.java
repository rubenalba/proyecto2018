package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Franjas;
import pojos.Horas;
import pojos.Profesor;

public class ImpFranjas implements FranjaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

	//FUNCIONA NO BORRAR!!
	@Override
	public void addFranja(Franjas franja) throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(franja);
			tx.commit();
		}catch  (Exception e) {
			if (tx!=null) tx.rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	//FUNCIONA NO BORRAR!
	@Override
	public void eliminarFranja(int idFranja) {
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
	public Franjas verFranjaByID(int idFranja) {
		Session session = factory.openSession();
		Transaction tx = null;
		Franjas franja = (Franjas)session.get(Franjas.class, idFranja);
		session.close();
		return franja;
	}
	@Override
	public Franjas verFranjaFalta(Horas Hora, Profesor profesor, String dia, Asignatura asignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT * FROM Franjas h WHERE h.id_horas = "+Hora.getIdHoras()+" AND h.DNI_Profesor LIKE '"+profesor.getDniProfesor()+"' AND h.Dia LIKE '"+dia+"' AND h.Asignatura = "+asignatura.getIdAsignatura();
		Franjas r = null;
		List<Franjas> franjaList = new ArrayList<Franjas>();
		try {
			tx = session.beginTransaction();
			franjaList = session.createNativeQuery(sql, Franjas.class).list();
			tx.commit();

			for (Franjas franj : franjaList) {
				r = franj;
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return r;
	}
	@Override
	public List<Franjas> verFranjaAsignatura(Profesor profesor, Asignatura asignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT * FROM Franjas h WHERE h.DNI_Profesor LIKE '"+profesor.getDniProfesor()+"' AND h.Asignatura = "+asignatura.getIdAsignatura();
		//Franjas r = null;
		List<Franjas> franjaList = new ArrayList<Franjas>();
		try {
			tx = session.beginTransaction();
			franjaList = session.createNativeQuery(sql, Franjas.class).list();
			tx.commit();

		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return franjaList;
	}
	public List<Franjas>completarHorario(String dni, String dia){
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT f.* from franjas f where f.DNI_Profesor = '" + dni+"' and f.Dia = '"+ dia +"'";
		Franjas r = null;
		List<Franjas>franjaList = new ArrayList<Franjas>();
		try {
			tx = session.beginTransaction();
			franjaList = session.createNativeQuery(sql, Franjas.class).list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return franjaList;
	}

}
